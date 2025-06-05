package com.piveguyz.empickbackend.employment.interviewSheetItem.query.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewSheet.query.dto.InterviewSheetQueryDTO;
import com.piveguyz.empickbackend.employment.interviewSheetItem.query.dto.InterviewSheetItemQueryDTO;
import com.piveguyz.empickbackend.employment.interviewSheetItem.query.mapper.InterviewSheetItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewSheetItemQueryServiceImpl implements InterviewSheetItemQueryService {
    private final InterviewSheetItemMapper interviewSheetItemMapper;

    @Override
    public List<InterviewSheetItemQueryDTO> findAll() {
        List<InterviewSheetItemQueryDTO> dtoList = interviewSheetItemMapper.findAll();
        return dtoList;
    }

    @Override
    public InterviewSheetItemQueryDTO findById(Integer id) {
        InterviewSheetItemQueryDTO dto = interviewSheetItemMapper.findById(id);
        if(dto == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_SHEET_ITEM_NOT_FOUND);
        }
        return dto;
    }

    @Override
    public List<InterviewSheetItemQueryDTO> findBySheetId(Integer sheetId) {
        List<InterviewSheetItemQueryDTO> dtoList = interviewSheetItemMapper.findBySheetId(sheetId);
        return dtoList;
    }

    @Override
    public List<InterviewSheetItemQueryDTO> findByCriteriaId(Integer criteriaId) {
        List<InterviewSheetItemQueryDTO> dtoList = interviewSheetItemMapper.findByCriteriaId(criteriaId);
        return dtoList;
    }
}
