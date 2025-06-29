package com.piveguyz.empickbackend.employment.jobtests.jobtest.query.service;

import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.JobtestEntryRequestDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestAnswerPageDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestRequestDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestResponseDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.JobtestExamQueryDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface ApplicationJobtestQueryService {
    List<ApplicationJobtestQueryDTO> getAllApplicationJobTests();
    int verifyEntryCode(int jobtestId, JobtestEntryRequestDTO requestDTO);

    void checkSubmittedAt(int applicationJobtestId);

    ApplicationJobtestResponseDTO getApplicationJobtestByApplicationId(int applicationId);

    ApplicationJobtestAnswerPageDTO getApplicationJobtest(int id);
}
