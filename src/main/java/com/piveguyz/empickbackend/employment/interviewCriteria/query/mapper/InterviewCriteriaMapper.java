package com.piveguyz.empickbackend.employment.interviewCriteria.query.mapper;

import com.piveguyz.empickbackend.employment.interviewCriteria.query.dto.InterviewCriteriaQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InterviewCriteriaMapper {
    List<InterviewCriteriaQueryDTO> findAll();

    InterviewCriteriaQueryDTO findById(Integer id);

    List<InterviewCriteriaQueryDTO> searchByContent(String content);
}
