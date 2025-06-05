package com.piveguyz.empickbackend.employment.interviewCriteria.query.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewCriteria.query.dto.InterviewCriteriaQueryDTO;
import com.piveguyz.empickbackend.employment.interviewCriteria.query.mapper.InterviewCriteriaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewCriteriaQueryServiceImpl implements InterviewCriteriaQueryService {
    private final InterviewCriteriaMapper interviewCriteriaMapper;

    @Override
    public List<InterviewCriteriaQueryDTO> findAll() {
        List<InterviewCriteriaQueryDTO> interviewCriteriaQueryDTOList = interviewCriteriaMapper.findAll();
        return interviewCriteriaQueryDTOList;
    }

    @Override
    public InterviewCriteriaQueryDTO findById(Integer id) {
        InterviewCriteriaQueryDTO interviewCriteriaQueryDTO = interviewCriteriaMapper.findById(id);
        if(interviewCriteriaQueryDTO == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_CRITERIA_NOT_FOUND);
        }
        return interviewCriteriaQueryDTO;
    }

    @Override
    public List<InterviewCriteriaQueryDTO> searchByContent(String content) {
        List<InterviewCriteriaQueryDTO> interviewCriteriaQueryDTOList = interviewCriteriaMapper.searchByContent(content);
        return interviewCriteriaQueryDTOList;
    }
}
