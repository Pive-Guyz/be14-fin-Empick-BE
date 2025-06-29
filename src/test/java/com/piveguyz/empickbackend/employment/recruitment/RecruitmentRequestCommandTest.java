package com.piveguyz.empickbackend.employment.recruitment;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import com.piveguyz.empickbackend.employment.recruitmentRequest.command.domain.repository.RecruitmentRequestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.piveguyz.empickbackend.employment.recruitmentRequest.command.application.dto.RecruitmentRequestCommandDTO;
import com.piveguyz.empickbackend.employment.recruitmentRequest.command.application.service.RecruitmentRequestCommandService;

@SpringBootTest
public class RecruitmentRequestCommandTest {

	@Autowired
	private RecruitmentRequestCommandService recruitmentRequestCommandService;

	@Autowired
	private RecruitmentRequestRepository recruitmentRequestRepository;

	@Test
	@DisplayName("채용 요청서 등록")
	void createRecruitmentRequest() {
		// given
		RecruitmentRequestCommandDTO dto = new RecruitmentRequestCommandDTO();
		dto.setHeadcount(2);
		dto.setStartedAt(LocalDateTime.of(2025, 7, 1, 0, 0));
		dto.setEndedAt(LocalDateTime.of(2025, 7, 31, 23, 59));
		dto.setQualification("관련 전공 학사 이상");
		dto.setPreference("유관 경력자 우대");
		dto.setResponsibility("신규 서비스 개발");
		dto.setEmploymentType("정규직");
		dto.setWorkLocation("서울 본사");
		dto.setDepartmentId(1);
		dto.setJobId(1);

		int memberId = 1;

		// when
		recruitmentRequestCommandService.create(dto, memberId);

		// then
		assertThat(
			recruitmentRequestRepository.findAll().stream()
				.anyMatch(r -> r.getMemberId() == memberId && r.getHeadcount() == 2)
		).isTrue();
	}
}
