package com.storyart.commentservice.dto.report;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportStoryRequest {
    private int userId;
    private int storyId;
    private String content;
}
