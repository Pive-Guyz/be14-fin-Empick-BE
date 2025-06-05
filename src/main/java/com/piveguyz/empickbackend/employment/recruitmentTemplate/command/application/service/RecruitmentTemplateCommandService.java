package com.piveguyz.empickbackend.employment.recruitmentTemplate.command.application.service;

import com.piveguyz.empickbackend.employment.recruitmentTemplate.command.application.dto.RecruitmentTemplateCommandDTO;

public interface RecruitmentTemplateCommandService {
	void createTemplate(RecruitmentTemplateCommandDTO dto);

	void updateTemplate(int id, RecruitmentTemplateCommandDTO dto);

	void deleteTemplate(int id);
}
