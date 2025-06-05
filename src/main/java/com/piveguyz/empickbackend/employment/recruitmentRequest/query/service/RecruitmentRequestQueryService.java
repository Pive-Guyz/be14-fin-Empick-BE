package com.piveguyz.empickbackend.employment.recruitmentRequest.query.service;

import java.util.List;

import com.piveguyz.empickbackend.employment.recruitmentRequest.query.dto.RecruitmentRequestQueryDTO;

public interface RecruitmentRequestQueryService {
	List<RecruitmentRequestQueryDTO> getAll();

	RecruitmentRequestQueryDTO getById(int id);
}
