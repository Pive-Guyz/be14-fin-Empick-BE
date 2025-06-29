package com.piveguyz.empickbackend.employment.recruitment;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.piveguyz.empickbackend.employment.applicationItem.query.dto.ApplicationItemCreateDTO;
import com.piveguyz.empickbackend.employment.recruitment.command.application.dto.RecruitmentCommandDTO;
import com.piveguyz.empickbackend.employment.recruitment.command.application.service.RecruitmentCommandService;
import com.piveguyz.empickbackend.employment.recruitment.command.domain.aggregate.Recruitment;
import com.piveguyz.empickbackend.employment.recruitment.command.domain.enums.RecruitType;
import com.piveguyz.empickbackend.employment.recruitment.command.domain.repository.RecruitmentRepository;
import com.piveguyz.empickbackend.employment.recruitmentProcess.command.application.dto.RecruitmentProcessCreateDTO;
import com.piveguyz.empickbackend.employment.recruitmentProcess.command.domain.enums.StepType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecruitmentCommandTest {

	@Autowired
	private RecruitmentCommandService recruitmentCommandService;

	@Autowired
	private RecruitmentRepository recruitmentRepository;

	@Test
	@DisplayName("채용공고 등록")
	public void createRecruitment() {
		// given
		RecruitmentCommandDTO dto = new RecruitmentCommandDTO();
		dto.setTitle("백엔드 개발자 채용");
		dto.setContent("Spring Boot를 사용하는 백엔드 개발자 모집");
		dto.setRecruitType(RecruitType.fromCode(0)); // REGULAR
		dto.setImageUrl("https://example.com/image.png");
		dto.setStartedAt(LocalDateTime.of(2025, 6, 30, 0, 0));
		dto.setEndedAt(LocalDateTime.of(2025, 7, 15, 23, 59));
		dto.setRecruitmentTemplateId(null);
		dto.setIntroduceTemplateId(1);
		dto.setMemberId(1);
		dto.setRecruitmentRequestId(null);

		dto.setApplicationItems(List.of(
			new ApplicationItemCreateDTO(1, true),
			new ApplicationItemCreateDTO(2, false)
		));

		List<RecruitmentProcessCreateDTO> processes = new ArrayList<>();

		RecruitmentProcessCreateDTO step1 = new RecruitmentProcessCreateDTO();
		step1.setStepType(StepType.DOCUMENT);
		step1.setDisplayOrder(1);

		RecruitmentProcessCreateDTO step2 = new RecruitmentProcessCreateDTO();
		step2.setStepType(StepType.PRACTICAL);
		step2.setDisplayOrder(2);

		RecruitmentProcessCreateDTO step3 = new RecruitmentProcessCreateDTO();
		step3.setStepType(StepType.INTERVIEW);
		step3.setDisplayOrder(3);

		processes.add(step1);
		processes.add(step2);
		processes.add(step3);

		dto.setRecruitmentProcesses(processes);

		// when
		recruitmentCommandService.createRecruitment(dto);

		// then
		assertThat(
			recruitmentRepository.findAll().stream()
				.anyMatch(r -> r.getTitle().equals("백엔드 개발자 채용"))
		).isTrue();
	}

	@Test
	@DisplayName("채용공고 수정")
	public void updateRecruitment() {

		int recruitmentId = 11;

		// given
		RecruitmentCommandDTO dto = new RecruitmentCommandDTO();
		dto.setTitle("백엔드 개발자 채용 (수정)");
		dto.setContent("Spring Boot 기반 백엔드 포지션입니다. (업데이트됨)");
		dto.setRecruitType(RecruitType.fromCode(1)); // SEASONAL
		dto.setImageUrl("https://example.com/updated-image.png");
		dto.setStartedAt(LocalDateTime.of(2025, 6, 20, 0, 0));
		dto.setEndedAt(LocalDateTime.of(2025, 7, 31, 23, 59));
		dto.setRecruitmentTemplateId(null);
		dto.setIntroduceTemplateId(1);
		dto.setMemberId(1);
		dto.setRecruitmentRequestId(null);

		dto.setApplicationItems(List.of(
			new ApplicationItemCreateDTO(1, false),
			new ApplicationItemCreateDTO(3, true)
		));

		List<RecruitmentProcessCreateDTO> processes = new ArrayList<>();

		RecruitmentProcessCreateDTO step1 = new RecruitmentProcessCreateDTO();
		step1.setStepType(StepType.DOCUMENT);
		step1.setDisplayOrder(1);

		RecruitmentProcessCreateDTO step2 = new RecruitmentProcessCreateDTO();
		step2.setStepType(StepType.INTERVIEW);
		step2.setDisplayOrder(2);

		processes.add(step1);
		processes.add(step2);

		dto.setRecruitmentProcesses(processes);

		// when
		recruitmentCommandService.updateRecruitment(recruitmentId, dto);

		// then
		Recruitment updated = recruitmentRepository.findById(recruitmentId)
			.orElseThrow(() -> new IllegalStateException("채용공고가 존재하지 않습니다."));

		assertThat(updated.getTitle()).isEqualTo("백엔드 개발자 채용 (수정)");
		assertThat(updated.getContent()).contains("업데이트됨");
		assertThat(updated.getRecruitType()).isEqualTo(RecruitType.SEASONAL);
		assertThat(updated.getImageUrl()).isEqualTo("https://example.com/updated-image.png");
	}

	@Test
	@DisplayName("채용공고 삭제")
	public void deleteRecruitment() {
		// given
		int recruitmentId = 11;

		// when
		recruitmentCommandService.deleteRecruitment(recruitmentId);

		// then
		Optional<Recruitment> deleted = recruitmentRepository.findById(recruitmentId);

		assertThat(deleted).isPresent();
		assertThat(deleted.get().getDeletedAt()).isNotNull(); // soft delete 확인
	}
}