package com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.mapper;

import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.CreateApplicationJobtestCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.UpdateApplicationJobtestCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.ApplicationJobtestEntity;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.enums.JobtestStatus;

public class ApplicationJobtestMapper {

    public static ApplicationJobtestEntity toEntity(CreateApplicationJobtestCommandDTO dto, String entryCode) {
        return ApplicationJobtestEntity.builder()
                .gradingTotalScore(0.0)
                .evaluationScore(0.0)
                .evaluationStatus(JobtestStatus.WAITING)
                .gradingStatus(JobtestStatus.WAITING)
                .entryCode(entryCode)
                .applicationId(dto.getApplicationId())
                .jobTestId(dto.getJobtestId())
                .gradingMemberId(dto.getGradingMemberId())
                .evaluationMemberId(dto.getEvaluationMemberId())
                .build();
    }

    public static CreateApplicationJobtestCommandDTO toCreateDto(ApplicationJobtestEntity entity) {
        return CreateApplicationJobtestCommandDTO.builder()
                .applicationId(entity.getApplicationId())
                .jobtestId(entity.getJobTestId())
                .gradingMemberId(entity.getGradingMemberId())
                .evaluationMemberId(entity.getEvaluationMemberId())
                .build();
    }

    public static UpdateApplicationJobtestCommandDTO toUpdateDto(ApplicationJobtestEntity entity) {
        return UpdateApplicationJobtestCommandDTO.builder()
                .evaluatorComment(entity.getEvaluatorComment())
                .submittedAt(entity.getSubmittedAt())
                .gradingTotalScore(entity.getGradingTotalScore())
                .evaluationScore(entity.getEvaluationScore())
                .gradingStatus(entity.getGradingStatus())
                .evaluationStatus(entity.getEvaluationStatus())
                .gradingMemberId(entity.getGradingMemberId())
                .evaluationMemberId(entity.getEvaluationMemberId())
                .build();
    }
}
