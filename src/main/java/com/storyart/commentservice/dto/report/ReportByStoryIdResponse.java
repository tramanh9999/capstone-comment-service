package com.storyart.commentservice.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportByStoryIdResponse {
    private Integer id;

    private int userId;
    private String username;

    private int storyId;
    private String content;
    private boolean isHandled;
}
