package com.piveguyz.empickbackend.employment.recruitmentTemplate.query.mapper;

import java.util.List;

import com.piveguyz.empickbackend.employment.recruitmentTemplate.query.dto.RecruitmentTemplateItemQueryDTO;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.query.dto.RecruitmentTemplateQueryDTO;

public interface RecruitmentTemplateQueryMapper {
	List<RecruitmentTemplateQueryDTO> findAll();

	RecruitmentTemplateQueryDTO findById(int id);

	List<RecruitmentTemplateItemQueryDTO> findItemsByTemplateId(int templateId);
}
