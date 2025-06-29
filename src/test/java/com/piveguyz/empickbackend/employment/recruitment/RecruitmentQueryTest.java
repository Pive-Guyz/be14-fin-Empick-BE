package com.piveguyz.empickbackend.employment.recruitment;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicationQueryDTO;
import com.piveguyz.empickbackend.employment.applicationItem.query.dto.ApplicationItemCategoryQueryDTO;
import com.piveguyz.empickbackend.employment.applicationItem.query.dto.ApplicationItemQueryDTO;
import com.piveguyz.empickbackend.employment.applicationItem.query.service.ApplicationItemCategoryQueryService;
import com.piveguyz.empickbackend.employment.applicationItem.query.service.ApplicationItemQueryService;
import com.piveguyz.empickbackend.employment.recruitment.query.dto.RecruitmentQueryConditionDTO;
import com.piveguyz.empickbackend.employment.recruitment.query.dto.RecruitmentQueryDTO;
import com.piveguyz.empickbackend.employment.recruitment.query.service.RecruitmentQueryService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecruitmentQueryTest {

	@Autowired
	private RecruitmentQueryService recruitmentQueryService;

	@Autowired
	private ApplicationItemQueryService applicationItemQueryService;

	@Autowired
	private ApplicationItemCategoryQueryService categoryQueryService;

	@Test
	@DisplayName("채용공고 전체 조회")
	public void findAllRecruitments() {
		// given
		RecruitmentQueryConditionDTO condition = new RecruitmentQueryConditionDTO();

		// when
		List<RecruitmentQueryDTO> list = recruitmentQueryService.findRecruitments(condition);

		// then
		assertThat(list).isNotNull();
		assertThat(list.size()).isGreaterThan(0);

		System.out.println("=== 전체 채용공고 목록 ===");
		for (RecruitmentQueryDTO dto : list) {
			System.out.println("ID: " + dto.getId());
			System.out.println("제목: " + dto.getTitle());
			System.out.println("내용: " + dto.getContent());
			System.out.println("채용유형: " + dto.getRecruitType());
			System.out.println("상태: " + dto.getStatus());
			System.out.println("시작일: " + dto.getStartedAt());
			System.out.println("종료일: " + dto.getEndedAt());
			System.out.println("작성일: " + dto.getCreatedAt());
			System.out.println("수정일: " + dto.getUpdatedAt());
			System.out.println("삭제일: " + dto.getDeletedAt());
			System.out.println("이미지 URL: " + dto.getImageUrl());
			System.out.println("작성자 ID: " + dto.getMemberId());
			System.out.println("작성자 이름: " + dto.getMemberName());
			System.out.println("부서명: " + dto.getDepartmentName());
			System.out.println("템플릿 ID: " + dto.getRecruitmentTemplateId());
			System.out.println("자기소개서 템플릿 ID: " + dto.getIntroduceTemplateId());
			System.out.println("채용 요청서 ID: " + dto.getRecruitmentRequestId());
			System.out.println("--------------------------------------------");
		}
	}

	@Test
	@DisplayName("채용공고 상세 조회 - ID 기준")
	public void findRecruitmentDetail() {
		// given
		int recruitmentId = 1;

		// when
		RecruitmentQueryDTO dto = recruitmentQueryService.findById(recruitmentId);

		// then
		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(recruitmentId);

		System.out.println("=== 채용공고 상세 정보 ===");
		System.out.println("ID: " + dto.getId());
		System.out.println("제목: " + dto.getTitle());
		System.out.println("내용: " + dto.getContent());
		System.out.println("채용유형: " + dto.getRecruitType());
		System.out.println("상태: " + dto.getStatus());
		System.out.println("이미지 URL: " + dto.getImageUrl());
		System.out.println("시작일: " + dto.getStartedAt());
		System.out.println("종료일: " + dto.getEndedAt());
		System.out.println("작성일: " + dto.getCreatedAt());
		System.out.println("수정일: " + dto.getUpdatedAt());
		System.out.println("삭제일: " + dto.getDeletedAt());
		System.out.println("작성자 ID: " + dto.getMemberId());
		System.out.println("작성자 이름: " + dto.getMemberName());
		System.out.println("부서명: " + dto.getDepartmentName());
		System.out.println("자기소개서 템플릿 ID: " + dto.getIntroduceTemplateId());
		System.out.println("채용 템플릿 ID: " + dto.getRecruitmentTemplateId());
		System.out.println("채용 요청서 ID: " + dto.getRecruitmentRequestId());
		System.out.println("--------------------------------------------");
	}

	@Test
	@DisplayName("채용공고별 지원서 목록 조회")
	public void findApplicationsByRecruitmentId() {
		// given
		int recruitmentId = 1;

		// when
		List<ApplicationQueryDTO> applications = recruitmentQueryService.findApplicationsByRecruitmentId(recruitmentId);

		// then
		assertThat(applications).isNotNull();

		System.out.println("=== 채용공고 ID " + recruitmentId + " 의 지원서 목록 ===");
		for (ApplicationQueryDTO dto : applications) {
			System.out.println("지원서 ID: " + dto.getId());
			System.out.println("지원 상태: " + dto.getStatus());
			System.out.println("지원자 ID: " + dto.getApplicantId());
			System.out.println("작성일: " + dto.getCreatedAt());
			System.out.println("수정일: " + dto.getUpdatedAt());
			System.out.println("수정자 ID: " + dto.getUpdatedBy());
			System.out.println("자기소개서 평가 ID: " + dto.getIntroduceRatingResultId());
			System.out.println("인터뷰 ID: " + dto.getInterviewId());
			System.out.println("실무테스트 ID: " + dto.getApplicationJobtestId());
			System.out.println("실무테스트 점수: " + dto.getJobtestGradingScore());
			System.out.println("실무테스트 상태: " + dto.getJobtestGradingStatus());
			System.out.println("------------------------------------------");
		}
	}

	@Test
	@DisplayName("채용공고별 지원서 항목 조회")
	public void getApplicationItemsByRecruitmentId() {
		// given
		int recruitmentId = 1;

		// when
		List<ApplicationItemQueryDTO> items = applicationItemQueryService.getItemsByRecruitmentId(recruitmentId);

		// then
		assertThat(items).isNotNull();
		assertThat(items.size()).isGreaterThan(0);

		System.out.println("=== 채용공고 ID " + recruitmentId + " 의 지원서 항목 목록 ===");
		for (ApplicationItemQueryDTO dto : items) {
			System.out.println("항목 ID: " + dto.getId());
			System.out.println("카테고리 ID: " + dto.getCategoryId());
			System.out.println("카테고리명: " + dto.getCategoryName());
			System.out.println("입력 형태: " + dto.getInputType());
			System.out.println("필수 여부: " + dto.isRequired());
			System.out.println("-------------------------------------------");
		}
	}

	@Test
	@DisplayName("지원서 항목 카테고리 전체 조회")
	public void getAllApplicationItemCategories() {
		// when
		List<ApplicationItemCategoryQueryDTO> list = categoryQueryService.getAllCategories();

		// then
		assertThat(list).isNotNull();
		assertThat(list.size()).isGreaterThan(0);

		System.out.println("=== 전체 지원서 항목 카테고리 목록 ===");
		for (ApplicationItemCategoryQueryDTO dto : list) {
			System.out.println("ID: " + dto.getId());
			System.out.println("이름: " + dto.getName());
			System.out.println("입력 타입: " + dto.getInputType());
			System.out.println("표시 순서: " + dto.getDisplayOrder());
			System.out.println("상위 카테고리 ID: " + dto.getApplicationItemCategoryId());
			System.out.println("--------------------------------------------");
		}
	}
}
