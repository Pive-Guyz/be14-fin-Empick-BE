package com.piveguyz.empickbackend.employment.interviewSheet.query.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewSheet.query.dto.InterviewSheetQueryDTO;
import com.piveguyz.empickbackend.employment.interviewSheet.query.mapper.InterviewSheetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewSheetQueryServiceImpl implements InterviewSheetQueryService {
    private final InterviewSheetMapper mapper;
    @Override
    public List<InterviewSheetQueryDTO> findAll() {
        List<InterviewSheetQueryDTO> dtoList = mapper.findAll();
        return dtoList;
    }

    @Override
    public InterviewSheetQueryDTO findById(Integer id) {
        InterviewSheetQueryDTO dto = mapper.findById(id);
        if(dto == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_SHEET_NOT_FOUND);
        }
        return dto;
    }

    @Override
    public List<InterviewSheetQueryDTO> searchByName(String name) {
        List<InterviewSheetQueryDTO> dtoList = mapper.searchByName(name);
        return dtoList;
    }
}
