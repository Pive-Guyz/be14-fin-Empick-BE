package com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.dto.InterviewSheetItemCommandDTO;
import com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.mapper.InterviewSheetItemCommandMapper;
import com.piveguyz.empickbackend.employment.interviewSheetItem.command.domain.aggregate.InterviewSheetItemEntity;
import com.piveguyz.empickbackend.employment.interviewSheetItem.command.domain.repository.InterviewSheetItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewSheetItemCommandServiceImpl implements InterviewSheetItemCommandService {
    private final InterviewSheetItemRepository repository;
    private final InterviewSheetItemCommandMapper mapper;
    @Override
    public InterviewSheetItemCommandDTO create(InterviewSheetItemCommandDTO dto) {
        Integer sheetId = dto.getSheetId();
        Integer criteriaId = dto.getCriteriaId();
        if(repository.existsBySheetIdAndCriteriaId(sheetId, criteriaId)){
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_SHEET_ITEM_DUPLICATE);
        }
        InterviewSheetItemEntity entity = new InterviewSheetItemEntity();
        entity.setSheetId(sheetId);
        entity.setCriteriaId(criteriaId);
        entity.setWeight(dto.getWeight());
        entity.setMemberId(1);
        entity.setUpdatedAt(LocalDateTime.now());
        InterviewSheetItemEntity createdEntity = repository.save(entity);
        InterviewSheetItemCommandDTO createdDto = mapper.toDTO(createdEntity);
        return createdDto;
    }

    @Override
    public InterviewSheetItemCommandDTO delete(Integer id) {
        InterviewSheetItemEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_SHEET_ITEM_NOT_FOUND));
        repository.delete(entity);
        InterviewSheetItemCommandDTO deletedDTO = mapper.toDTO(entity);
        return deletedDTO;
    }
}
