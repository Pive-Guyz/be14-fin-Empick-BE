package com.piveguyz.empickbackend.employment.recruitmentTemplate.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.piveguyz.empickbackend.employment.recruitmentTemplate.query.dto.RecruitmentTemplateItemQueryDTO;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.query.dto.RecruitmentTemplateQueryDTO;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.query.mapper.RecruitmentTemplateQueryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentTemplateQueryServiceImpl implements RecruitmentTemplateQueryService {
	private final RecruitmentTemplateQueryMapper recruitmentTemplateQueryMapper;

	@Override
	public List<RecruitmentTemplateQueryDTO> getAll() {
		return recruitmentTemplateQueryMapper.findAll();
	}

	@Override
	public RecruitmentTemplateQueryDTO getById(int id) {
		RecruitmentTemplateQueryDTO template = recruitmentTemplateQueryMapper.findById(id);
		if (template == null) return null;

		List<RecruitmentTemplateItemQueryDTO> items = recruitmentTemplateQueryMapper.findItemsByTemplateId(id);
		template.setItems(items);

		return template;
	}
}
