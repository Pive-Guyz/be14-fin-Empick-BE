package com.piveguyz.empickbackend.employment.jobtests.jobtest.query.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.JobtestEntryRequestDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestRequestDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestResponseDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.mapper.ApplicationJobtestMapper;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestQueryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationJobtestQueryServiceImpl implements ApplicationJobtestQueryService {
    private final ApplicationJobtestMapper applicationJobtestMapper;

    @Override
    public List<ApplicationJobtestQueryDTO> getAllApplicationJobTests() {
        return applicationJobtestMapper.selectAllApplicationJobtest();
    }

    @Override
    public int verifyEntryCode(int jobtestId, JobtestEntryRequestDTO requestDTO) {
        Integer applicationJobTestId = applicationJobtestMapper.selectApplicationJobTestIdByJobTestIdAndEntryCode(
                jobtestId, requestDTO.getEntryCode()
        );
        if (applicationJobTestId == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_JOBTEST_INVALID_ENTRY_CODE);
        }
        return applicationJobTestId;
    }

    @Override
    public void checkSubmittedAt(int applicationJobtestId) {
        Date submittedAt = applicationJobtestMapper.selectSubmittedAtById(applicationJobtestId);
        if (submittedAt != null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_APPLICATION_JOBTEST_ALREADY_COMPLETE);
        }
    }

    @Override
    public ApplicationJobtestResponseDTO getApplicationJobtestByApplicationId(int applicationId) {
        return applicationJobtestMapper.selectByApplicationId(applicationId);
    }

}
