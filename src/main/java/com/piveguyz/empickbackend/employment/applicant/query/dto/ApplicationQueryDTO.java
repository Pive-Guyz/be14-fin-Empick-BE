package com.piveguyz.empickbackend.employment.applicant.query.dto;

import com.piveguyz.empickbackend.employment.applicant.command.domain.aggregate.ApplicationStatus;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.enums.JobtestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationQueryDTO {

    private int id;
    private int recruitmentId;
    private LocalDateTime createdAt;
    private ApplicationStatus status;
    private int applicantId;
    private int introduceRatingResultId; // nullable
    private int interviewId; // nullable
    private LocalDateTime updatedAt; // DATETIME 타입은 LocalDateTime이 적절
    private int updatedBy; // 수정자 member_id

    // 실무테스트 정보 조회
    private Integer applicationJobtestId;
    private Integer jobtestGradingScore;
    private JobtestStatus jobtestGradingStatus;
}
