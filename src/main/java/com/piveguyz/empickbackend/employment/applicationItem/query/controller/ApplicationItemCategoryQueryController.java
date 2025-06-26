package com.piveguyz.empickbackend.employment.applicationItem.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicationItem.query.dto.ApplicationItemCategoryQueryDTO;
import com.piveguyz.empickbackend.employment.applicationItem.query.service.ApplicationItemCategoryQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "지원자,지원서 API", description = "지원자,지원서 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/applications/item-categories")
public class ApplicationItemCategoryQueryController {
	private final ApplicationItemCategoryQueryService applicationItemCategoryQueryService;

	@Operation(summary = "지원서 항목 카테고리 목록 조회", description = "지원서 양식 항목의 카테고리 전체 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "2050", description = "지원서 항목 카테고리를 찾을 수 없습니다."),
		@ApiResponse(responseCode = "2051", description = "지원서 항목 카테고리가 존재하지 않습니다.")
	})
	@GetMapping
	public ResponseEntity<CustomApiResponse<List<ApplicationItemCategoryQueryDTO>>> getAllCategories() {
		List<ApplicationItemCategoryQueryDTO> result = applicationItemCategoryQueryService.getAllCategories();
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS, result));
	}
}