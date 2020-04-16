package com.storyart.commentservice.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticResponse {
    private int total;
    private List<NumberOfCommentByDate> numberOfCommentByDates;
}
