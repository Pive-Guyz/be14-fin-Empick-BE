package com.piveguyz.empickbackend.employment.interviewSheetItem.query.mapper;

import com.piveguyz.empickbackend.employment.interviewSheetItem.query.dto.InterviewSheetItemQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InterviewSheetItemMapper {
    List<InterviewSheetItemQueryDTO> findAll();

    InterviewSheetItemQueryDTO findById(Integer id);

    List<InterviewSheetItemQueryDTO> findBySheetId(Integer sheetId);

    List<InterviewSheetItemQueryDTO> findByCriteriaId(Integer criteriaId);
}
