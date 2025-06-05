package com.piveguyz.empickbackend.employment.interviewCriteria.query.service;

import com.piveguyz.empickbackend.employment.interviewCriteria.query.dto.InterviewCriteriaQueryDTO;

import java.util.List;

public interface InterviewCriteriaQueryService {
    List<InterviewCriteriaQueryDTO> findAll();

    InterviewCriteriaQueryDTO findById(Integer id);

    List<InterviewCriteriaQueryDTO> searchByContent(String content);
}
