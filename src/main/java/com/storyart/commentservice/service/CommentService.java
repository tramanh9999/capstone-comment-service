package com.storyart.commentservice.service;

import com.storyart.commentservice.dto.comment.*;
import com.storyart.commentservice.model.Comment;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CommentService {
    ResponseListCommentDTO create(CreateCommentDTO cmt);
    Comment update(UpdateCommentDTO cmt);
    Comment delete(DeleteCommentDTO cmt);
    Page<ResponseListCommentDTO> findAllByStoryId(int storyId, int pageNo, int pageSize, String sortBy);
    Comment findById(Integer id);
    void disableAndEnableComment(int commentId);
    Page<CommentHistoryResponseDTO> getCommentHistory(int userId, int pageNo, int pageSize);
    StatisticResponse getStatistic(int storyId, int userId, String startDate, String endDate);
}
