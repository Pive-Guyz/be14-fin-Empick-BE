package com.piveguyz.empickbackend.employment.applicant.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicant.command.application.dto.ApplicationCommandDTO;
import com.piveguyz.empickbackend.employment.applicant.command.application.mapper.ApplicationCommandMapper;
import com.piveguyz.empickbackend.employment.applicant.command.domain.aggregate.ApplicationEntity;
import com.piveguyz.empickbackend.employment.applicant.command.domain.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationCommandServiceImp implements ApplicationCommandService {

    private final ApplicationRepository applicationRepository;

    @Override
    public ApplicationCommandDTO register(ApplicationCommandDTO dto) {
        if (applicationRepository.existsByRecruitmentIdAndApplicantId(
                dto.getRecruitmentId(), dto.getApplicantId())) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_APPLICATION_DUPLICATE_APPLICATION);
        }

        dto.setStatus(0);
//        dto.setIntroduceRatingResultId(); // 초기값 처리
        ApplicationEntity entity = ApplicationCommandMapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());

        ApplicationEntity saved = applicationRepository.save(entity);
        dto.setId(saved.getId()); // 응답용 id 설정
        return dto;
    }

    @Override
    public ApplicationCommandDTO updated(ApplicationCommandDTO dto) {
        // 1. 해당 ID의 지원서가 존재하는지 확인
        ApplicationEntity entity = applicationRepository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_APPLICATION_NOT_FOUND));

        // 2. 필드 업데이트 (status와 introduceRatingResultId)
        entity.setStatus(dto.getStatus());

        // introduceRatingResultId가 null이 아닌 경우에만 업데이트
        if (dto.getIntroduceRatingResultId() != null) {
            entity.setIntroduceRatingResultId(dto.getIntroduceRatingResultId());
        }

        // updatedAt과 updatedBy도 설정
        entity.setUpdatedAt(LocalDateTime.now());
        // entity.setUpdatedBy(현재_사용자_ID); // 필요시 추가

        // 3. 저장
        ApplicationEntity updated = applicationRepository.save(entity);

        // 4. entity → dto 변환 후 반환
        return ApplicationCommandMapper.toDTO(updated);
    }

    @Override
    public Integer deleted(int id) {
        ApplicationEntity entity = applicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_APPLICATION_NOT_FOUND));

        applicationRepository.delete(entity);
        return id;
    }



}

