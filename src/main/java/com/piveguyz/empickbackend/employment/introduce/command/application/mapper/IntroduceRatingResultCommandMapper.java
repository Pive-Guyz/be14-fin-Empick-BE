package com.piveguyz.empickbackend.employment.introduce.command.application.mapper;

import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceRatingResultCreateCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceRatingResultUpdateCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceRatingResultEntity;


public class IntroduceRatingResultCommandMapper {

    // 등록용 DTO → Entity
    public static IntroduceRatingResultEntity toEntity(IntroduceRatingResultCreateCommandDTO dto) {
        if (dto == null) return null;

        return IntroduceRatingResultEntity.builder()
                .content(dto.getContent())
                .ratingScore(dto.getRatingScore())
                .memberId(dto.getMemberId())
                .introduceStandardId(dto.getIntroduceStandardId())
                .introduceId(dto.getIntroduceId())
                .build();
    }

    // Entity → 등록 결과 DTO
    public static IntroduceRatingResultCreateCommandDTO toCreateDto(IntroduceRatingResultEntity entity) {
        if (entity == null) return null;

        return IntroduceRatingResultCreateCommandDTO.builder()
                .content(entity.getContent())
                .ratingScore(entity.getRatingScore())
                .memberId(entity.getMemberId())
                .introduceStandardId(entity.getIntroduceStandardId())
                .introduceId(entity.getIntroduceId())
                .build();
    }

    // Entity → 수정 결과 DTO
    public static IntroduceRatingResultUpdateCommandDTO toUpdateDto(IntroduceRatingResultEntity entity) {
        if (entity == null) return null;

        return IntroduceRatingResultUpdateCommandDTO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .ratingScore(entity.getRatingScore())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}