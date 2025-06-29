package com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.application.dto.InterviewSheetCommandDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.application.mapper.InterviewSheetCommandMapper;
import com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.domain.aggregate.InterviewSheetEntity;
import com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.domain.repository.InterviewSheetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewSheetCommandServiceImpl implements InterviewSheetCommandService {
    private final InterviewSheetRepository repository;
    private final InterviewSheetCommandMapper mapper;
    @Override
    public InterviewSheetCommandDTO createSheet(InterviewSheetCommandDTO dto) {
        String name = dto.getName();
        if(name == null || name.isEmpty()){
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_SHEET_NO_NAME);
        }
        if(repository.existsByName(name)){
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_SHEET_DUPLICATE_NAME);
        }
        InterviewSheetEntity entity = new InterviewSheetEntity();
        entity.setName(name);
        entity.setIsDeleted("N");
        entity.setMemberId(dto.getMemberId());
        entity.setUpdatedAt(LocalDateTime.now());
        InterviewSheetEntity createdEntity = repository.save(entity);
        InterviewSheetCommandDTO createdDTO = mapper.toDTO(createdEntity);
        return createdDTO;
    }

    @Override
    public InterviewSheetCommandDTO updateSheet(Integer id, InterviewSheetCommandDTO dto) {
        String name = dto.getName();
        if(name == null || name.isEmpty()){
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_SHEET_NO_NAME);
        }
        if(repository.existsByNameAndIdNotAndIsDeleted(name, id, "N")){
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_SHEET_DUPLICATE_NAME);
        }
        InterviewSheetEntity entity = repository.findById(id)
                        .orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_SHEET_NOT_FOUND));
        entity.setName(name);
        entity.setIsDeleted("N");
        entity.setMemberId(dto.getMemberId());
        entity.setUpdatedAt(LocalDateTime.now());
        InterviewSheetEntity updatedEntity = repository.save(entity);
        InterviewSheetCommandDTO updatedDTO = mapper.toDTO(updatedEntity);
        return updatedDTO;
    }

    @Override
    public InterviewSheetCommandDTO deleteSheet(Integer id) {
        InterviewSheetEntity entity = repository.findByIdAndIsDeleted(id, "N");
        if(entity == null){
            throw new BusinessException(ResponseCode.EMPLOYMENT_INTERVIEW_SHEET_NOT_FOUND);
        }
        entity.setIsDeleted("Y");
        InterviewSheetEntity deletedEntity = repository.save(entity);
        InterviewSheetCommandDTO deletedDTO = mapper.toDTO(deletedEntity);
        return deletedDTO;
    }
}
