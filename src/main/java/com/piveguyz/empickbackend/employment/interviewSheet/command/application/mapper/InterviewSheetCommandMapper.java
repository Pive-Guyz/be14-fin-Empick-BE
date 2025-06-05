package com.piveguyz.empickbackend.employment.interviewSheet.command.application.mapper;

import com.piveguyz.empickbackend.employment.interviewSheet.command.application.dto.InterviewSheetCommandDTO;
import com.piveguyz.empickbackend.employment.interviewSheet.command.domain.aggregate.InterviewSheetEntity;
import org.springframework.stereotype.Component;

@Component
public class InterviewSheetCommandMapper {
    public InterviewSheetCommandDTO toDTO(InterviewSheetEntity entity){
        InterviewSheetCommandDTO dto = new InterviewSheetCommandDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIsDeleted(entity.getIsDeleted());
        dto.setMemberId(entity.getMemberId());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public InterviewSheetEntity toEntity(InterviewSheetCommandDTO dto){
        InterviewSheetEntity entity = new InterviewSheetEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setIsDeleted(dto.getIsDeleted());
        entity.setMemberId(dto.getMemberId());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}
