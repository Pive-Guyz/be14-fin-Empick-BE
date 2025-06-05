package com.piveguyz.empickbackend.employment.jobtests.question.command.application.mapper;

import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.CreateQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.DeleteQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.UpdateQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.QuestionEntity;

import java.time.LocalDateTime;

public class QuestionMapper {

    // 문제 생성 DTO → Entity
    public static QuestionEntity toEntity(CreateQuestionCommandDTO dto) {
        return QuestionEntity.builder()
                .content(dto.getContent())
                .detailContent(dto.getDetailContent())
                .type(dto.getType())
                .difficulty(dto.getDifficulty())
                .answer(dto.getAnswer())
                .createdAt(LocalDateTime.now())
                .createdMemberId(dto.getCreatedMemberId())
                .build();
    }

    // Entity → 문제 생성 DTO
    public static CreateQuestionCommandDTO toCreateDto(QuestionEntity entity) {
        return CreateQuestionCommandDTO.builder()
                .content(entity.getContent())
                .detailContent(entity.getDetailContent())
                .type(entity.getType())
                .difficulty(entity.getDifficulty())
                .answer(entity.getAnswer())
                .createdMemberId(entity.getCreatedMemberId())
                .build();
    }

    // Entity → 문제 수정 DTO
    public static UpdateQuestionCommandDTO toUpdateDto(QuestionEntity entity) {
        return UpdateQuestionCommandDTO.builder()
                .content(entity.getContent())
                .detailContent(entity.getDetailContent())
                .type(entity.getType())
                .difficulty(entity.getDifficulty())
                .answer(entity.getAnswer())
                .updatedMemberId(entity.getUpdatedMemberId())
                .build();
    }

    // Entity → 문제 삭제 DTO
    public static DeleteQuestionCommandDTO toDeleteDto(QuestionEntity entity) {
        return DeleteQuestionCommandDTO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .detailContent(entity.getDetailContent())
                .type(entity.getType())
                .difficulty(entity.getDifficulty())
                .answer(entity.getAnswer())
                .createdMemberId(entity.getCreatedMemberId())
                .build();
    }
}
