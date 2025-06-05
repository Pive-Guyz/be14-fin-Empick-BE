package com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.mapper;

import com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.dto.InterviewSheetItemCommandDTO;
import com.piveguyz.empickbackend.employment.interviewSheetItem.command.domain.aggregate.InterviewSheetItemEntity;
import org.springframework.stereotype.Component;

@Component
public class InterviewSheetItemCommandMapper {
    public InterviewSheetItemCommandDTO toDTO(InterviewSheetItemEntity entity){
        InterviewSheetItemCommandDTO dto = new InterviewSheetItemCommandDTO();
        dto.setId(entity.getId());
        dto.setSheetId(entity.getSheetId());
        dto.setCriteriaId(entity.getCriteriaId());
        dto.setWeight(entity.getWeight());
        dto.setMemberId(entity.getMemberId());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public InterviewSheetItemEntity toEntity(InterviewSheetItemCommandDTO dto){
        InterviewSheetItemEntity entity = new InterviewSheetItemEntity();
        entity.setId(dto.getId());
        entity.setSheetId(dto.getSheetId());
        entity.setCriteriaId(dto.getCriteriaId());
        entity.setWeight(dto.getWeight());
        entity.setMemberId(dto.getMemberId());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}
