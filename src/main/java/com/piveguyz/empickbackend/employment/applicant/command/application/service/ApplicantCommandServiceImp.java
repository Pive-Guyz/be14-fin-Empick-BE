package com.piveguyz.empickbackend.employment.applicant.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicant.command.application.dto.ApplicantCommandDTO;
import com.piveguyz.empickbackend.employment.applicant.command.application.mapper.ApplicantCommandMapper;
import com.piveguyz.empickbackend.employment.applicant.command.domain.aggregate.ApplicantEntity;
import com.piveguyz.empickbackend.employment.applicant.command.domain.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantCommandServiceImp implements ApplicantCommandService {

    private final ApplicantRepository applicantRepository;

    @Override
    public ApplicantCommandDTO createApplicant(ApplicantCommandDTO dto) {

        ApplicantEntity entity = ApplicantCommandMapper.toEntity(dto);
        ApplicantEntity saved = applicantRepository.save(entity);
        return ApplicantCommandMapper.toDto(saved);
    }
}
