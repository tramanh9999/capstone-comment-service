package com.storyart.commentservice.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoryReportResponse {
    private List<Integer> reportIds;
    private int numberOfReports;

    private int userId;
    private String authorName;
    private String authorEmail;
    private boolean userIsDisableByAdmin;

    private int storyId;
    private String storyName;
    private boolean storyIsDisableByAdmin;

    private boolean isHandled;
}
