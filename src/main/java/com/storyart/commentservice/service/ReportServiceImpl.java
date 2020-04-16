package com.storyart.commentservice.service;

import com.storyart.commentservice.dto.comment.ResponseListCommentDTO;
import com.storyart.commentservice.dto.report.*;
import com.storyart.commentservice.model.*;
import com.storyart.commentservice.repository.CommentRepository;
import com.storyart.commentservice.repository.ReportRepository;
import com.storyart.commentservice.repository.StoryRepository;
import com.storyart.commentservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    StoryRepository storyRepository;

    @Override
    public void reportComment(ReportCommentRequestDTO reportCommentRequestDTO) {
        if (reportCommentRequestDTO.getContent().trim().length() < 1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vui lòng không để trống nội dung báo cáo.");
        }
        Optional<Report> rp = reportRepository.findReportByUserIdAndCommentId(reportCommentRequestDTO.getUserId(), reportCommentRequestDTO.getCommentId());

        if (rp.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bạn đã báo cáo bình luận này, vui lòng đợi quản trị viên xử lý.");
        }
        //find user
        Optional<User> u = userRepository.findById(reportCommentRequestDTO.getUserId());
        if (!u.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không tồn tại.");
        }

        //find cmt
        Optional<Comment> cmt = commentRepository.findById(reportCommentRequestDTO.getCommentId());
        if (!cmt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bình luận không tồn tại.");
        }

        Report report = new Report();
        report.setCommentId(reportCommentRequestDTO.getCommentId());
        report.setUserId(reportCommentRequestDTO.getUserId()); //user nay la nguoi di report dung ko, dung r, user bi, report nam trong cmt
        //chinh xac =)))
        //lua` nhau a` :v
        //tuong ko biet cai nay chu =)) :))
        report.setContent(reportCommentRequestDTO.getContent());
        report.setHandled(false);//handle cho nay de lam gi v anh //them vao` de admin moi lan giai quyet
        //kieu nhu isDisableByAdmin ha
        //dung, nhung ma` se co case admin k disable,
        //để mình biết cái report nào admin đã xử lí rồi, tránh load hết.
        //a ok anh, v la co them 1 bien isDisableByAdmin trong comment dung ko , đúng, 1 cái ishandled trong report
        //ok, gud gud

        reportRepository.save(report);
    }

    @Override
    public void reportStory(ReportStoryRequest request) {
        if (request.getContent().trim().length() < 1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vui lòng nhập nội dung báo cáo.");
        }

        Optional<Story> story = storyRepository.findById(request.getStoryId());
        if (!story.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Truyện không tồn tại.");
        }

        Optional<User> user = userRepository.findById(request.getUserId());
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không tồn tại.");
        }

        Optional<Report> rp = reportRepository.findReportByUserIdAndStoryId(request.getUserId(), request.getStoryId());

        if (rp.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bạn đã báo cáo bình luận này, vui lòng đợi quản trị viên xử lý.");
        }

        Report report = new Report();
        report.setUserId(request.getUserId());
        report.setStoryId(request.getStoryId());
        report.setContent(request.getContent());
        report.setHandled(false);

        reportRepository.save(report);
    }

    @Override
    public Page<ReportCommentResponseDTO> getListReportComment(String searchString, boolean isHandled, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Integer> commentIds = new ArrayList<>();


        Page<Comment> commentPage;
        if (searchString.trim().length() < 1) {
            commentIds = reportRepository.getCommentIds(isHandled);
            commentPage = commentRepository.getCommentReports(commentIds, isHandled, pageable);
        } else {
            try {
                int commentId = Integer.parseInt(searchString);
                commentIds.add(commentId);
            } catch (Exception e) {
                List<Integer> userIds = userRepository.findUserIdsBySearchString(searchString);
                commentIds = commentRepository.findAllCommentIdByUserIds(userIds);

            }
            commentPage = commentRepository.getCommentReports(commentIds, isHandled, pageable);
        }


        Page<ReportCommentResponseDTO> responsePage = commentPage.map(new Function<Comment, ReportCommentResponseDTO>() {
            @Override
            public ReportCommentResponseDTO apply(Comment comment) {
                ModelMapper mm = new ModelMapper();
                ReportCommentResponseDTO reportCommentResponseDTO = mm.map(comment, ReportCommentResponseDTO.class);
                return reportCommentResponseDTO;
            }
        });
        List<ReportCommentResponseDTO> responseList = responsePage.getContent();
        List<Comment> commentList = commentPage.getContent();

        int index = 0;
        for (ReportCommentResponseDTO response : responseList) {
            response.setCommentId(commentList.get(index).getId());
            response.setCommentOwnerId(commentList.get(index).getUserId());
            response.setCommentIsDisableByAdmin(commentList.get(index).isDisableByAdmin());
            response.setCommentContent(commentList.get(index).getContent());
            index++;
        }


        //Page<ReportCommentResponseDTO> responsePage = commentPage.map(new Function<Comment, ReportCommentResponseDTO>() {
        //    @Override
        //    public ReportCommentResponseDTO apply(Comment comment) {
        //        ModelMapper mm = new ModelMapper();
        //        ReportCommentResponseDTO reportCommentResponseDTO = mm.map(comment, ReportCommentResponseDTO.class);
        //        return reportCommentResponseDTO;
        //    }
        //});

        //List<Report> reportList = reportPage.getContent();
        //List<Integer> commentIds = new ArrayList<>();

        //for (Report report : reportList) {
        //    commentIds.add(report.getCommentId());
        //}

        //List<Comment> comments = commentRepository.findAllById(commentIds);

        List<Integer> userIds = new ArrayList<>();
        for (Comment comment : commentList) {
            userIds.add(comment.getUserId());
        }
        List<User> users = userRepository.findAllById(userIds);
        List<Report> fullInfoReport = reportRepository.findFullInformationReport(commentIds, isHandled);

        //List<Integer> numberOfReports = reportRepository.getNumberOfReports(commentIds, isHandled);
        //List<ReportCommentResponseDTO> responseList = responsePage.getContent();
        //int index = 0;
        for (ReportCommentResponseDTO response : responseList) {
            List<Integer> reportIds = new ArrayList<>();
            boolean handled = false;
            for (Report report : fullInfoReport) {
                if (report.getCommentId().equals(response.getCommentId())) {
                    reportIds.add(report.getId());
                }
            }
            response.setNumberOfReports(reportIds.size());
            for (User user : users) {
                if (user.getId() == response.getCommentOwnerId()) {
                    response.setCommentOwner(user.getUsername());
                    response.setCommentOwnerEmail(user.getEmail());
                    response.setUserIsDisableByAdmin(user.isDeactiveByAdmin());
                }
            }
            response.setReportIds(reportIds);
            response.setHandled(isHandled);
            //index++;
        }
        //response.setCommentOwner(reportList.get(index).getComment().getUser().getUsername());


        return responsePage;
    }

    @Override
    public Page<ReportByCommentIdResponse> getReportsByCommentId(int commentId, boolean isHandled, int pageNo, int pageSize) {

        Optional<Comment> comment = commentRepository.findById(commentId);

        if (!comment.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bình luận không tồn tại.");
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Report> reportPage = reportRepository.getReportsByCommentId(commentId, isHandled, pageable);
        Page<ReportByCommentIdResponse> responsePage = reportPage.map(new Function<Report, ReportByCommentIdResponse>() {
            @Override
            public ReportByCommentIdResponse apply(Report report) {
                ModelMapper mm = new ModelMapper();
                ReportByCommentIdResponse reportByCommentIdResponse = mm.map(report, ReportByCommentIdResponse.class);

                return reportByCommentIdResponse;
            }
        });

        List<Report> reports = reportPage.getContent();
        List<Integer> userIds = new ArrayList<>();
        for (Report report : reports) {
            userIds.add(report.getUserId());
        }
        List<User> users = userRepository.findAllById(userIds);

        List<ReportByCommentIdResponse> responseList = responsePage.getContent();
        for (ReportByCommentIdResponse response : responseList) {
            for (User user : users) {
                if (response.getUserId() == user.getId()) {
                    response.setUsername(user.getUsername());
                }
            }
        }
        return responsePage;
    }

    @Override
    public Page<StoryReportResponse> getListReportStory(String searchString, boolean isHandled, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Integer> storyIds = new ArrayList<>();

        Page<Story> reportPage;
        if (searchString.trim().length() < 1) {
            storyIds = reportRepository.getStoryIds(isHandled);
            reportPage = storyRepository.getStoryReports(storyIds, isHandled, pageable);
        } else {
            //List<Integer> storyIds = new ArrayList<>();
            try {
                int storyId = Integer.parseInt(searchString);
                storyIds.add(storyId);
            } catch (Exception e) {
                List<Integer> userIds = userRepository.findUserIdsBySearchString(searchString);
                storyIds = storyRepository.findAllStoryIdByUserIds(userIds);
            }
            reportPage = storyRepository.getStoryReports(storyIds, isHandled, pageable);
        }


        Page<StoryReportResponse> responsePage = reportPage.map(new Function<Story, StoryReportResponse>() {
            @Override
            public StoryReportResponse apply(Story story) {
                ModelMapper mm = new ModelMapper();
                StoryReportResponse storyReportResponse = mm.map(story, StoryReportResponse.class);
                return storyReportResponse;
            }
        });

        List<StoryReportResponse> responseList = responsePage.getContent();
        List<Story> stories = reportPage.getContent();
        int index = 0;
        for (StoryReportResponse response : responseList) {
            response.setUserId(stories.get(index).getUserId());
            response.setStoryIsDisableByAdmin(stories.get(index).isDeactiveByAdmin());
            response.setStoryName(stories.get(index).getTitle());
            response.setStoryId(stories.get(index).getId());
            index++;
        }

        //List<Report> reportList = reportPage.getContent();
        //List<Integer> storyIds = new ArrayList<>();

        //for (Report report : reportList) {
        //storyIds.add(report.getStoryId());
        //}

        //List<Story> stories = storyRepository.findAllById(storyIds);

        List<Integer> userIds = new ArrayList<>();
        for (Story story : stories) {
            userIds.add(story.getUserId());
        }

        List<User> users = userRepository.findAllById(userIds);
        List<Report> fullInfoReport = reportRepository.findFullInformationReportStory(storyIds, isHandled);

        //List<Integer> numberOfReports = reportRepository.getNumberOfReports(commentIds, isHandled);
        //List<StoryReportResponse> responseList = responsePage.getContent();
        //int index = 0;
        for (StoryReportResponse response : responseList) {
            List<Integer> reportIds = new ArrayList<>();
            for (Report report : fullInfoReport) {
                if (report.getStoryId().equals(response.getStoryId())) {
                    reportIds.add(report.getId());
                }
            }
            response.setNumberOfReports(reportIds.size());
            for (User user : users) {
                if (user.getId() == response.getUserId()) {
                    response.setAuthorName(user.getUsername());
                    response.setAuthorEmail(user.getEmail());
                    response.setUserIsDisableByAdmin(user.isDeactiveByAdmin());
                }
            }
            response.setReportIds(reportIds);
            response.setHandled(isHandled);
        }


        return responsePage;
    }

    @Override
    public Page<ReportByStoryIdResponse> getReportsByStoryId(int storyId, boolean isHandled, int pageNo, int pageSize) {
        Optional<Story> story = storyRepository.findById(storyId);

        if (!story.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Truyện không tồn tại.");
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Report> reportPage = reportRepository.getReportsByStoryId(storyId, isHandled, pageable);
        Page<ReportByStoryIdResponse> responsePage = reportPage.map(new Function<Report, ReportByStoryIdResponse>() {
            @Override
            public ReportByStoryIdResponse apply(Report report) {
                ModelMapper mm = new ModelMapper();
                ReportByStoryIdResponse reportByStoryIdResponse = mm.map(report, ReportByStoryIdResponse.class);

                return reportByStoryIdResponse;
            }
        });

        List<Report> reports = reportPage.getContent();

        List<Integer> userIds = new ArrayList<>();
        for (Report report : reports) {
            userIds.add(report.getUserId());
        }
        List<User> users = userRepository.findAllById(userIds);

        List<ReportByStoryIdResponse> responseList = responsePage.getContent();
        for (ReportByStoryIdResponse response : responseList) {
            for (User user : users) {
                if (response.getUserId() == user.getId()) {
                    response.setUsername(user.getUsername());
                }
            }
        }
        return responsePage;
    }

    @Override
    public void handleReport(HandleReportRequestDTO request) {
        if (request.getType().equals("user")) {
            Optional<User> user = userRepository.findById(request.getId());
            if (!user.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không tồn tại.");
            }
            User u = user.get();
            if (request.getAction().equals("deactivate")) {
                u.setDeactiveByAdmin(true);
                userRepository.save(u);
            }
            if (request.getAction().equals("activate")) {
                u.setDeactiveByAdmin(false);
                userRepository.save(u);
            }
        }
        if (request.getType().equals("story")) {
            Optional<Story> story = storyRepository.findById(request.getId());
            if (!story.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Truyện không tồn tại.");
            }
            Story s = story.get();
            if (request.getAction().equals("deactivate")) {
                //storyRepository.updateDisableByAdminStatus(request.getId(),true);
                s.setDeactiveByAdmin(true);
                storyRepository.save(s);
            }
            if (request.getAction().equals("activate")) {
                //storyRepository.updateDisableByAdminStatus(request.getId(),false);
                s.setDeactiveByAdmin(false);
                storyRepository.save(s);
            }
        }
        if (request.getType().equals("comment")) {
            Optional<Comment> comment = commentRepository.findById(request.getId());
            if (!comment.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bình luận không tồn tại.");
            }
            Comment c = comment.get();
            if (request.getAction().equals("deactivate")) {
                c.setDisableByAdmin(true);
                commentRepository.save(c);
            }
            if (request.getAction().equals("activate")) {
                c.setDisableByAdmin(false);
                commentRepository.save(c);
            }
        }
        if (request.getReportIds().size() > 0) {
            changeStatusOfReport(request.getReportIds());
        }


    }

    public void changeStatusOfReport(List<Integer> reportIds) {
        List<Report> reports = reportRepository.findAllById(reportIds);
        if (reports.size() > 0) {
            for (Report report : reports) {
                report.setHandled(true);
            }
            reportRepository.saveAll(reports);
        }

    }

    //@Override
    //public void handleReport(List<Integer> reportIds) {
    //    List<Report> reports = reportRepository.findAllById(reportIds);
//
    //    for (Report report: reports) {
    //        report.setHandled(true);
    //    }
//
    //    reportRepository.saveAll(reports);
    //}
}
