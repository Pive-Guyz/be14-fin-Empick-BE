package com.piveguyz.empickbackend.employment.recruitment;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import com.piveguyz.empickbackend.employment.recruitmentRequest.query.dto.RecruitmentRequestQueryDTO;
import com.piveguyz.empickbackend.employment.recruitmentRequest.query.service.RecruitmentRequestQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecruitmentRequestQueryTest {

	@Autowired
	private RecruitmentRequestQueryService recruitmentRequestQueryService;

	@Test
	@DisplayName("채용 요청서 전체 조회")
	public void getAllRecruitmentRequests() {
		// when
		List<RecruitmentRequestQueryDTO> requests = recruitmentRequestQueryService.getAll();

		// then
		assertThat(requests).isNotNull();
		assertThat(requests.size()).isGreaterThan(0);

		System.out.println("=== 채용 요청서 전체 조회 ===");
		for (RecruitmentRequestQueryDTO dto : requests) {
			System.out.println("요청서 ID: " + dto.getId());
			System.out.println("직무: " + dto.getJobName() + " (ID: " + dto.getJobId() + ")");
			System.out.println("부서: " + dto.getDepartmentName() + " (ID: " + dto.getDepartmentId() + ")");
			System.out.println("작성자: " + dto.getMemberName() + " (ID: " + dto.getMemberId() + ")");
			System.out.println("모집 인원: " + dto.getHeadcount());
			System.out.println("기간: " + dto.getStartedAt() + " ~ " + dto.getEndedAt());
			System.out.println("근무지: " + dto.getWorkLocation());
			System.out.println("고용 형태: " + dto.getEmploymentType());
			System.out.println("--------------------------------------------");
		}
	}

	@Test
	@DisplayName("채용 요청서 상세 조회")
	public void getRecruitmentRequestDetail() {
		// given
		int requestId = 1;

		// when
		RecruitmentRequestQueryDTO dto = recruitmentRequestQueryService.getById(requestId);

		// then
		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(requestId);
		assertThat(dto.getJobName()).isNotNull();
		assertThat(dto.getDepartmentName()).isNotNull();
		assertThat(dto.getHeadcount()).isGreaterThan(0);

		System.out.println("=== 채용 요청서 상세 조회 ===");
		System.out.println("ID: " + dto.getId());
		System.out.println("직무: " + dto.getJobName());
		System.out.println("부서: " + dto.getDepartmentName());
		System.out.println("작성자: " + dto.getMemberName());
		System.out.println("모집 인원: " + dto.getHeadcount());
		System.out.println("모집 기간: " + dto.getStartedAt() + " ~ " + dto.getEndedAt());
		System.out.println("자격 요건: " + dto.getQualification());
		System.out.println("우대 사항: " + dto.getPreference());
		System.out.println("담당 업무: " + dto.getResponsibility());
		System.out.println("근무지: " + dto.getWorkLocation());
		System.out.println("고용 형태: " + dto.getEmploymentType());
		System.out.println("------------------------------------------");
	}
}
