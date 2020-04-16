package com.storyart.commentservice.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//type = "none","comment","user","story"
//action = "activate", "deactivate","none"
public class HandleReportRequestDTO {
    private String type;
    private int id;
    private String action;
    private List<Integer> reportIds;
}
