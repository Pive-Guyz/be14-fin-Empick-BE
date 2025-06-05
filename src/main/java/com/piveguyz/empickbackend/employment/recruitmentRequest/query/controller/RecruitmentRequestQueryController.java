package com.piveguyz.empickbackend.employment.recruitmentRequest.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.recruitmentRequest.query.dto.RecruitmentRequestQueryDTO;
import com.piveguyz.empickbackend.employment.recruitmentRequest.query.service.RecruitmentRequestQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "채용 요청서 조회 API", description = "채용 요청서 목록 및 상세 조회")
@RestController("QueryRecruitmentController")
@RequestMapping("api/v1/employment/recruitment/request")
@RequiredArgsConstructor
public class RecruitmentRequestQueryController {
	private final RecruitmentRequestQueryService recruitmentRequestQueryService;

	@Operation(
		summary = "채용 요청서 전체 목록 조회",
		description = "등록된 모든 채용 요청서를 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})

	// 채용 요청서 전체 목록 조회
	@GetMapping
	public ResponseEntity<CustomApiResponse<List<RecruitmentRequestQueryDTO>>> getAllRequests() {
		List<RecruitmentRequestQueryDTO> result = recruitmentRequestQueryService.getAll();
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS, result));
	}

	@Operation(
		summary = "채용 요청서 상세 조회",
		description = """
			- 특정 채용 요청서를 ID로 조회합니다.
		"""
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
		@ApiResponse(responseCode = "2000", description = "채용 요청서를 찾을 수 없습니다."),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	// 채용 요청서 상세 조회
	@GetMapping("/{id}")
	public ResponseEntity<CustomApiResponse<RecruitmentRequestQueryDTO>> getRequestById(@PathVariable int id) {
		RecruitmentRequestQueryDTO result = recruitmentRequestQueryService.getById(id);
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS, result));
	}
}
