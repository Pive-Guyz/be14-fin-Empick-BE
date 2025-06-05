package com.piveguyz.empickbackend.employment.interviewCriteria.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewCriteria.command.application.dto.InterviewCriteriaCommandDTO;
import com.piveguyz.empickbackend.employment.interviewCriteria.command.application.mapper.InterviewCriteriaCommandMapper;
import com.piveguyz.empickbackend.employment.interviewCriteria.command.domain.aggregate.InterviewCriteriaEntity;
import com.piveguyz.empickbackend.employment.interviewCriteria.command.domain.repository.InterviewCriteriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewCriteriaCommandServiceImpl implements InterviewCriteriaCommandService {
    private final InterviewCriteriaCommandMapper mapper;
    private final InterviewCriteriaRepository repository;

    @Override
    public InterviewCriteriaCommandDTO createCriteria(InterviewCriteriaCommandDTO dto) {
        String content = dto.getContent();
        String detailContent = dto.getDetailContent();
        if(content == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NO_CONTENT);
        }
        if(detailContent == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NO_DETAIL_CONTENT);
        }
        if (repository.existsByContentAndIsDeleted(content, "N")) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_DUPLICATE_CONTENT);
        }
        InterviewCriteriaEntity entity = new InterviewCriteriaEntity();
        entity.setContent(content);
        entity.setDetailContent(detailContent);
        entity.setIsDeleted("N");
        entity.setMemberId(1);      // 나중에 로그인 JWT토큰으로부터 받아올 예정
        entity.setUpdatedAt(LocalDateTime.now());

        entity = repository.save(entity);
        InterviewCriteriaCommandDTO savedDTO = mapper.toDTO(entity);
        return savedDTO;
    }

    @Override
    public InterviewCriteriaCommandDTO updateCriteria(Integer id, InterviewCriteriaCommandDTO dto) {
        String content = dto.getContent();
        String detailContent = dto.getDetailContent();
        if(content == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NO_CONTENT);
        }
        if(detailContent == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NO_DETAIL_CONTENT);
        }
        if (repository.existsByContentAndIdNotAndIsDeleted(content, id, "N")) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_DUPLICATE_CONTENT);
        }
        InterviewCriteriaEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NOT_FOUND));
        entity.setContent(content);
        entity.setDetailContent(detailContent);
        entity.setIsDeleted("N");
        entity.setMemberId(1);     // 나중에 로그인 JWT토큰으로부터 받아올 예정
        entity.setUpdatedAt(LocalDateTime.now());
        entity = repository.save(entity);
        InterviewCriteriaCommandDTO savedDTO = mapper.toDTO(entity);
        return savedDTO;
    }

    @Override
    public InterviewCriteriaCommandDTO deleteCriteria(Integer id) {
        if(!repository.existsByIdAndIsDeleted(id, "N")){
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NOT_FOUND);
        }
        InterviewCriteriaEntity entity = repository.findByIdAndIsDeleted(id, "N");

        entity.setIsDeleted("Y");
        entity.setMemberId(1);
        entity.setUpdatedAt(LocalDateTime.now());
        entity = repository.save(entity);
        InterviewCriteriaCommandDTO savedDTO = mapper.toDTO(entity);
        return savedDTO;
    }
}
