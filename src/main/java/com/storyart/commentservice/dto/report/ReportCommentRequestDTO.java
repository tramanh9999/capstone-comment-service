package com.storyart.commentservice.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCommentRequestDTO {
    private int userId;
    private int commentId;
    private String content;
}
