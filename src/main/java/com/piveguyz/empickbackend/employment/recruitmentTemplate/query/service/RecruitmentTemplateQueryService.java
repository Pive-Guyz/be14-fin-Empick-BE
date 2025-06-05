package com.piveguyz.empickbackend.employment.recruitmentTemplate.query.service;

import java.util.List;

import com.piveguyz.empickbackend.employment.recruitmentTemplate.query.dto.RecruitmentTemplateQueryDTO;

public interface RecruitmentTemplateQueryService {
	List<RecruitmentTemplateQueryDTO> getAll();

	RecruitmentTemplateQueryDTO getById(int id);
}
