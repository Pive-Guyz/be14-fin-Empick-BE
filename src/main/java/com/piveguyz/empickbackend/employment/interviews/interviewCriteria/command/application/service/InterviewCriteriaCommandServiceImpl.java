package com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.application.dto.InterviewCriteriaCommandDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.application.mapper.InterviewCriteriaCommandMapper;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.domain.aggregate.InterviewCriteriaEntity;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.domain.repository.InterviewCriteriaRepository;
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
        String title = dto.getTitle();
        String content = dto.getContent();
        if(title == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NO_CONTENT);
        }
        if(content == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NO_DETAIL_CONTENT);
        }
        if (repository.existsByContentAndIsDeleted(content, "N")) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_DUPLICATE_CONTENT);
        }
        Double weight = dto.getWeight();
        if(weight > 1 || weight <= 0) {
            throw new BusinessException(ResponseCode.BAD_REQUEST);
        }
        InterviewCriteriaEntity entity = new InterviewCriteriaEntity();
        entity.setSheetId(dto.getSheetId());
        entity.setTitle(title);
        entity.setContent(content);
        entity.setWeight(weight);
        entity.setIsDeleted("N");
        entity.setMemberId(dto.getMemberId());      // 나중에 로그인 JWT토큰으로부터 받아올 예정
        entity.setUpdatedAt(LocalDateTime.now());

        entity = repository.save(entity);
        InterviewCriteriaCommandDTO savedDTO = mapper.toDTO(entity);
        return savedDTO;
    }

    @Override
    public InterviewCriteriaCommandDTO updateCriteria(Integer id, InterviewCriteriaCommandDTO dto) {
        String title = dto.getTitle();
        String content = dto.getContent();
        if(title == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NO_CONTENT);
        }
        if(content == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NO_DETAIL_CONTENT);
        }
        if (repository.existsByContentAndIdNotAndIsDeleted(content, id, "N")) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_DUPLICATE_CONTENT);
        }
        InterviewCriteriaEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NOT_FOUND));
        entity.setTitle(title);
        entity.setContent(content);
        entity.setWeight(dto.getWeight());
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
