package com.piveguyz.empickbackend.employment.interviewSheet.query.mapper;

import com.piveguyz.empickbackend.employment.interviewSheet.query.dto.InterviewSheetQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InterviewSheetMapper {
    List<InterviewSheetQueryDTO> findAll();

    InterviewSheetQueryDTO findById(Integer id);

    List<InterviewSheetQueryDTO> searchByName(String name);
}
