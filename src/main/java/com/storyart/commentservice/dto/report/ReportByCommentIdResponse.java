package com.storyart.commentservice.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportByCommentIdResponse {
    private Integer id;

    private int userId;
    private String username;

    private int storyId;
    private int commentId;
    private String content;
    private boolean isHandled;
}
