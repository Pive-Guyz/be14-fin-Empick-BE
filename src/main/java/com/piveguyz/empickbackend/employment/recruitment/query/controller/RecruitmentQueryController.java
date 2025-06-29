package com.piveguyz.empickbackend.employment.recruitment.query.controller;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicantQueryDTO;
import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicationQueryDTO;
import com.piveguyz.empickbackend.employment.recruitment.query.dto.RecruitmentQueryConditionDTO;
import com.piveguyz.empickbackend.employment.recruitment.query.dto.RecruitmentQueryDTO;
import com.piveguyz.empickbackend.employment.recruitment.query.service.RecruitmentQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "채용 공고 API", description = "채용 공고 관련 전체 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/recruitments")
@SecurityRequirement(name = "bearerAuth")
public class RecruitmentQueryController {

	private final RecruitmentQueryService recruitmentQueryService;

	@Operation(summary = "채용 공고 목록 조회", description = "등록된 채용 공고 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	@GetMapping
	public ResponseEntity<CustomApiResponse<List<RecruitmentQueryDTO>>> getRecruitments(
		RecruitmentQueryConditionDTO condition) {
		List<RecruitmentQueryDTO> result = recruitmentQueryService.findRecruitments(condition);
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS, result));
	}

	@Operation(summary = "채용 공고 상세 조회", description = "특정 채용 공고의 상세 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
		@ApiResponse(responseCode = "2030", description = "요청한 채용 공고를 찾을 수 없습니다."),
		@ApiResponse(responseCode = "2032", description = "이미 삭제된 채용 공고입니다."),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	@GetMapping("/{recruitmentId}")
	public ResponseEntity<CustomApiResponse<RecruitmentQueryDTO>> getRecruitmentDetail(
		@PathVariable("recruitmentId") int recruitmentId
	) {
		RecruitmentQueryDTO dto = recruitmentQueryService.findById(recruitmentId);
		if (dto == null) {
			throw new BusinessException(ResponseCode.EMPLOYMENT_RECRUITMENT_NOT_FOUND);
		}

		if (dto.getDeletedAt() != null) {
			throw new BusinessException(ResponseCode.EMPLOYMENT_RECRUITMENT_ALREADY_DELETED);
		}

		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS, dto));
	}

	@Operation(summary = "채용공고별 지원서 조회", description = "특정 채용공고에 등록된 모든 지원서를 조회합니다.")
	@GetMapping("/{recruitmentId}/applications")
	public ResponseEntity<CustomApiResponse<List<ApplicationQueryDTO>>> getApplicationsByRecruitmentId(
		@PathVariable("recruitmentId") int recruitmentId
	) {
		List<ApplicationQueryDTO> result = recruitmentQueryService.findApplicationsByRecruitmentId(recruitmentId);
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS, result));
	}
}
