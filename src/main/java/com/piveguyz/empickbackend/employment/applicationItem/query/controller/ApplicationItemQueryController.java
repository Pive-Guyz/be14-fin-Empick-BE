package com.piveguyz.empickbackend.employment.applicationItem.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicationItem.query.dto.ApplicationItemQueryDTO;
import com.piveguyz.empickbackend.employment.applicationItem.query.service.ApplicationItemQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "지원자,지원서 API", description = "지원자,지원서 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/applications/items")
public class ApplicationItemQueryController {
	private final ApplicationItemQueryService applicationItemQueryService;

	@Operation(
		summary = "채용공고별 지원서 항목 조회",
		description = """
        해당 채용공고에 연결된 지원서 항목 목록을 조회합니다.
        """
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "2053", description = "해당 채용공고에 연결된 지원서 양식을 찾을 수 없습니다."),
	})
	@GetMapping("/{recruitmentId}")
	public ResponseEntity<CustomApiResponse<List<ApplicationItemQueryDTO>>> getItemsByRecruitmentId(
		@PathVariable int recruitmentId) {
		List<ApplicationItemQueryDTO> result = applicationItemQueryService.getItemsByRecruitmentId(recruitmentId);
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS, result));
	}
}
