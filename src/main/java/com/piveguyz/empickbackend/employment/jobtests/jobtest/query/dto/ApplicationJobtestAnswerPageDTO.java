package com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationJobtestAnswerPageDTO {
    private int applicationJobTestId;
    private String applicantName;
    private String recruitmentTitle;
    private Integer applicantId;
    private Integer applicationId;
    private String jobtestTitle;
    private LocalDateTime submittedAt;
}
