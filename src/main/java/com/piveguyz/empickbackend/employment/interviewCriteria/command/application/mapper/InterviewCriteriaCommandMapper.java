package com.piveguyz.empickbackend.employment.interviewCriteria.command.application.mapper;

import com.piveguyz.empickbackend.employment.interviewCriteria.command.application.dto.InterviewCriteriaCommandDTO;
import com.piveguyz.empickbackend.employment.interviewCriteria.command.domain.aggregate.InterviewCriteriaEntity;
import org.springframework.stereotype.Component;

@Component
public class InterviewCriteriaCommandMapper {
    public InterviewCriteriaCommandDTO toDTO(InterviewCriteriaEntity entity) {
        InterviewCriteriaCommandDTO dto = new InterviewCriteriaCommandDTO();

        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setIsDeleted(entity.getIsDeleted());
        dto.setMemberId(entity.getMemberId());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public InterviewCriteriaEntity toEntity(InterviewCriteriaCommandDTO dto) {
        InterviewCriteriaEntity entity = new InterviewCriteriaEntity();

        entity.setId(dto.getId());
        entity.setContent(dto.getContent());
        entity.setIsDeleted(dto.getIsDeleted());
        entity.setMemberId(dto.getMemberId());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}
