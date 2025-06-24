package com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto;

import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.enums.JobtestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationJobtestResponseDTO {
    private int id;
    private int applicationId;
    private int jobtestId;
    private String jobtestTitle;
    private Double score;
    private JobtestStatus status;
}
