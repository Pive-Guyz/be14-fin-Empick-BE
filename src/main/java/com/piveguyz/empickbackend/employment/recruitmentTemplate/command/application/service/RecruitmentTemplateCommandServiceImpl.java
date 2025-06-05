package com.piveguyz.empickbackend.employment.recruitmentTemplate.command.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.command.application.dto.RecruitmentTemplateCommandDTO;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.command.application.dto.RecruitmentTemplateItemCommandDTO;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.command.domain.aggregate.RecruitmentTemplate;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.command.domain.aggregate.RecruitmentTemplateItem;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.command.domain.repository.RecruitmentTemplateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentTemplateCommandServiceImpl implements RecruitmentTemplateCommandService {
	private final RecruitmentTemplateRepository recruitmentTemplateRepository;

	@Override
	public void createTemplate(RecruitmentTemplateCommandDTO dto) {
		RecruitmentTemplate template = RecruitmentTemplate.builder()
			.name(dto.getName())
			.memberId(dto.getMemberId())
			.createdAt(LocalDateTime.now())
			.isDeleted("N")
			.build();

		for (RecruitmentTemplateItemCommandDTO itemDto : dto.getItems()) {
			RecruitmentTemplateItem item = RecruitmentTemplateItem.builder()
				.itemTitle(itemDto.getItemTitle())
				.defaultContent(itemDto.getDefaultContent())
				.displayOrder(itemDto.getDisplayOrder())
				.build();
			template.addItem(item);
		}

		recruitmentTemplateRepository.save(template);
	}

	@Override
	public void updateTemplate(int id, RecruitmentTemplateCommandDTO dto) {
		RecruitmentTemplate template = recruitmentTemplateRepository.findById(id)
			.orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_TEMPLATE_NOT_FOUND));

		if (dto.getItems() == null || dto.getItems().isEmpty()) {
			throw new BusinessException(ResponseCode.EMPLOYMENT_TEMPLATE_NO_ITEMS);
		}

		template.update(dto.getName());

		// 기존 항목 관계 끊고 삭제 유도
		template.getItems().forEach(item -> item.setRecruitmentTemplate(null));
		template.getItems().clear();

		// 플러시 시점 제어 → 삭제 먼저 반영
		recruitmentTemplateRepository.flush();

		for (RecruitmentTemplateItemCommandDTO itemDto : dto.getItems()) {
			RecruitmentTemplateItem item = RecruitmentTemplateItem.builder()
				.itemTitle(itemDto.getItemTitle())
				.defaultContent(itemDto.getDefaultContent())
				.displayOrder(itemDto.getDisplayOrder())
				.build();
			template.addItem(item);
		}
	}

	@Override
	public void deleteTemplate(int id) {
		RecruitmentTemplate template = recruitmentTemplateRepository.findById(id)
			.orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_TEMPLATE_NOT_FOUND));

		template.delete();
	}
}
