package com.piveguyz.empickbackend.employment.interviewCriteria.command.application.service;


import com.piveguyz.empickbackend.employment.interviewCriteria.command.application.dto.InterviewCriteriaCommandDTO;

public interface InterviewCriteriaCommandService {
    InterviewCriteriaCommandDTO createCriteria(InterviewCriteriaCommandDTO dto);

    InterviewCriteriaCommandDTO updateCriteria(Integer id, InterviewCriteriaCommandDTO dto);

    InterviewCriteriaCommandDTO deleteCriteria(Integer id);
}
