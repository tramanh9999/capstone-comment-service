package com.storyart.commentservice.dto.reaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactionCommentDTO {
    private int userId;
    private int commentId;
    private String type;
}
