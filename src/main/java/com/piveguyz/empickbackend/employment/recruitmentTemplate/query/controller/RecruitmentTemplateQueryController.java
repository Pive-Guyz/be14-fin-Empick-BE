package com.piveguyz.empickbackend.employment.recruitmentTemplate.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.query.dto.RecruitmentTemplateQueryDTO;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.query.service.RecruitmentTemplateQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "채용 공고 템플릿 조회 API", description = "채용 공고 템플릿 목록 및 상세 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/recruitment/template")
public class RecruitmentTemplateQueryController {

	private final RecruitmentTemplateQueryService recruitmentTemplateQueryService;

	@Operation(
		summary = "채용 공고 템플릿 목록 조회",
		description = "- 등록된 채용 공고 템플릿 목록을 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	@GetMapping
	public ResponseEntity<CustomApiResponse<List<RecruitmentTemplateQueryDTO>>> getAll() {
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS, recruitmentTemplateQueryService.getAll()));
	}

	@Operation(
		summary = "채용 공고 템플릿 상세 조회",
		description = """
        - 템플릿 ID를 기준으로 상세 정보를 조회합니다.
        - 템플릿 항목 리스트 (items)도 함께 반환됩니다.
        """
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "2010", description = "요청한 템플릿을 찾을 수 없습니다."
		),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	@GetMapping("/{id}")
	public ResponseEntity<CustomApiResponse<RecruitmentTemplateQueryDTO>> getById(@PathVariable int id) {
		RecruitmentTemplateQueryDTO dto = recruitmentTemplateQueryService.getById(id);
		ResponseCode code = (dto != null) ? ResponseCode.SUCCESS : ResponseCode.EMPLOYMENT_TEMPLATE_NOT_FOUND;
		return ResponseEntity.status(code.getHttpStatus()).body(CustomApiResponse.of(code, dto));
	}
}
