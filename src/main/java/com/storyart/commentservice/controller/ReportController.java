package com.storyart.commentservice.controller;

import com.storyart.commentservice.dto.report.*;
import com.storyart.commentservice.model.Reaction;
import com.storyart.commentservice.model.Report;
import com.storyart.commentservice.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
@CrossOrigin(origins = "*")
public class ReportController {
    @Autowired
    ReportService reportService;

    @PostMapping("/reportComment")
    public ResponseEntity<Boolean> reportComment(@RequestBody @Valid ReportCommentRequestDTO reportCommentRequestDTO) {
        reportService.reportComment(reportCommentRequestDTO);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @PostMapping("/reportStory")
    public ResponseEntity<Boolean> reportStory(@RequestBody @Valid ReportStoryRequest request) {
        reportService.reportStory(request);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/getCommentReports")
    public Page<ReportCommentResponseDTO> getCommentReports(
            @RequestParam(defaultValue = "") String searchString,
            @RequestParam(defaultValue = "false") boolean isHandled,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        pageNo = pageNo - 1;
        if (pageNo < 0) {
            pageNo = 0;
        }
        return reportService.getListReportComment(searchString,isHandled,pageNo, pageSize);
    }

    @GetMapping("/getStoryReports")
    public Page<StoryReportResponse> getStoryReports(
            @RequestParam(defaultValue = "") String searchString,
            @RequestParam(defaultValue = "false") boolean isHandled,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        pageNo = pageNo - 1;
        if (pageNo < 0) {
            pageNo = 0;
        }
        return reportService.getListReportStory(searchString,isHandled,pageNo, pageSize);
    }

    @GetMapping("/getReportsByCommentId")
    public Page<ReportByCommentIdResponse> getRoportsByCommentId(
            @RequestParam(defaultValue = "false") boolean isHandled,
            @RequestParam(defaultValue = "0") Integer commentId,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        pageNo = pageNo - 1;
        if (pageNo < 0) {
            pageNo = 0;
        }
        return reportService.getReportsByCommentId(commentId,isHandled, pageNo, pageSize);
    }

    @GetMapping("/getReportsByStoryId")
    public Page<ReportByStoryIdResponse> getRoportsByStoryId(
            @RequestParam(defaultValue = "false") boolean isHandled,
            @RequestParam(defaultValue = "0") Integer storyId,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        pageNo = pageNo - 1;
        if (pageNo < 0) {
            pageNo = 0;
        }
        return reportService.getReportsByStoryId(storyId,isHandled, pageNo, pageSize);
    }

    @PostMapping("/handleReport")
    public ResponseEntity<Boolean> handleReport(
            @RequestBody @Valid HandleReportRequestDTO request){
        reportService.handleReport(request);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
