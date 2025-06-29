package com.piveguyz.empickbackend.employment.applicant.command.application.mapper;

import com.piveguyz.empickbackend.employment.applicant.command.application.dto.ApplicationCommandDTO;
import com.piveguyz.empickbackend.employment.applicant.command.domain.aggregate.ApplicationEntity;

import java.time.LocalDateTime;

public class ApplicationCommandMapper {


    // DTO → Entity
    public static ApplicationEntity toEntity(ApplicationCommandDTO dto) {
        ApplicationEntity entity = new ApplicationEntity();
        entity.setRecruitmentId(dto.getRecruitmentId());
        entity.setApplicantId(dto.getApplicantId());
        entity.setStatus(dto.getStatus());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setIntroduceRatingResultId(dto.getIntroduceRatingResultId());
        // updatedAt, updatedBy는 등록 시에는 null
        return entity;
    }

    // Entity → DTO
    public static ApplicationCommandDTO toDTO(ApplicationEntity entity) {
        ApplicationCommandDTO dto = new ApplicationCommandDTO();
        dto.setId(entity.getId());
        dto.setRecruitmentId(entity.getRecruitmentId());
        dto.setApplicantId(entity.getApplicantId());
        dto.setStatus(entity.getStatus());
        dto.setIntroduceRatingResultId(entity.getIntroduceRatingResultId());
        return dto;
    }

    // 기존 updated 메서드의 오류 수정 (introduceRatingResultId를 updatedBy에 설정하는 오류)
    public static void updated(ApplicationEntity entity, int status, int updatedBy) {
        entity.setStatus(status);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(updatedBy);  // 기존에 잘못 설정된 부분 수정
    }

    // 새로운 메서드 추가: DTO 전체 필드 업데이트용
    public static void updateFromDTO(ApplicationEntity entity, ApplicationCommandDTO dto, Integer updatedBy) {
        if (dto.getStatus() != 0) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getIntroduceRatingResultId() != null) {
            entity.setIntroduceRatingResultId(dto.getIntroduceRatingResultId());
        }
        entity.setUpdatedAt(LocalDateTime.now());
        if (updatedBy != null) {
            entity.setUpdatedBy(updatedBy);
        }
    }
}