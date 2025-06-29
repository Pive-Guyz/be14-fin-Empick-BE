package com.piveguyz.empickbackend.approvals.approvalCategoryItem.query.controller;

import com.piveguyz.empickbackend.approvals.approvalCategoryItem.query.dto.ApprovalCategoryItemQueryDTO;
import com.piveguyz.empickbackend.approvals.approvalCategoryItem.query.service.ApprovalCategoryItemQueryService;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "결재 유형별 항목 API", description = "결재 유형별 항목 전체 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/approval/categories/{categoryId}/items")
@SecurityRequirement(name = "bearerAuth")
public class ApprovalCategoryItemQueryController {
	private final ApprovalCategoryItemQueryService service;

//	@GetMapping
//	public ResponseEntity<CustomApiResponse<List<ApprovalCategoryItemQueryDTO>>> findAll() {
//		List<ApprovalCategoryItemQueryDTO> dtoList = service.findAll();
//		ResponseCode result =  	ResponseCode.SUCCESS;
//		return ResponseEntity.status(result.getHttpStatus())
//			.body(CustomApiResponse.of(result, dtoList));
//	}

	// 카테고리 내 단일 항목
	@Operation(summary = "카테고리 내 단일 항목", description = "카테고리 내 단일 항목을 조회합니다.")
	@GetMapping("{itemId}")
	public ResponseEntity<CustomApiResponse<ApprovalCategoryItemQueryDTO>> findById(
					@PathVariable("categoryId") Integer categoryId,
					@PathVariable("itemId") Integer id) {
		ApprovalCategoryItemQueryDTO dto = service.findById(id);
		ResponseCode result = ResponseCode.SUCCESS;
		return ResponseEntity.status(result.getHttpStatus())
				.body(CustomApiResponse.of(result, dto));
	}

	// 카테고리별 전체 항목
	@Operation(summary = "카테고리별 전체 항목", description = "카테고리별 전체 항목을 조회합니다.")
	@GetMapping
	public ResponseEntity<CustomApiResponse<List<ApprovalCategoryItemQueryDTO>>> findByCategoryId(@PathVariable("categoryId") Integer categoryId) {
		List<ApprovalCategoryItemQueryDTO> dtoList = service.findByCategoryId(categoryId);
		ResponseCode result = ResponseCode.SUCCESS;
		return ResponseEntity.status(result.getHttpStatus())
				.body(CustomApiResponse.of(result, dtoList));
	}
}