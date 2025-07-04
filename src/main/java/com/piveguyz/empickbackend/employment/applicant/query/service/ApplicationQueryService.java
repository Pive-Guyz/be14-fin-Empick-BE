package com.piveguyz.empickbackend.employment.applicant.query.service;

import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicationQueryDTO;

import java.util.List;

public interface ApplicationQueryService {
    List<ApplicationQueryDTO> findAllApplication();


    ApplicationQueryDTO findApplicationById(int id);

    ApplicationQueryDTO findApplicationByApplicantId(Integer applicantId);
}
