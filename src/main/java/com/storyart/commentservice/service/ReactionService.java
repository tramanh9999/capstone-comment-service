package com.storyart.commentservice.service;

import com.storyart.commentservice.dto.reaction.ReactionCommentDTO;
import com.storyart.commentservice.dto.reaction.ReactionHistoryResponseDTO;
import com.storyart.commentservice.model.Reaction;
import org.springframework.data.domain.Page;

public interface ReactionService {
    void react(ReactionCommentDTO reaction);
    //void like(ReactionCommentDTO reaction);
    //void dislike(ReactionCommentDTO reaction);
    void removeReaction(ReactionCommentDTO reaction);
    Page<ReactionHistoryResponseDTO> getReactionHistory(int userId, int pageNo, int pageSize);
}
