package com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class JobtestExamQueryDTO {
    private Integer jobtestId;
    private int applicationJobTestId;
    private String title;
    private LocalDateTime startedAt;
    private Integer testTime;
    private List<JobtestExamQuestionSummaryDTO> questions;
}