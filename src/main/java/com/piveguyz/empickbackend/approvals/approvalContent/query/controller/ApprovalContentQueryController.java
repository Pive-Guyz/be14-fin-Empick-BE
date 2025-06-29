package com.piveguyz.empickbackend.approvals.approvalContent.query.controller;

import com.piveguyz.empickbackend.approvals.approvalContent.query.dto.ApprovalContentQueryDTO;
import com.piveguyz.empickbackend.approvals.approvalContent.query.service.ApprovalContentQueryService;
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

@Tag(name = "결재 문서 내용 API", description = "결재 문서 내용 관련 전체 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/approval-content")
@SecurityRequirement(name = "bearerAuth")
public class ApprovalContentQueryController {
	private final ApprovalContentQueryService service;

	@Operation(summary = "결재 문서 내용 전체 조회", description = "결재 문서 내용을 전체 조회합니다.")
	@GetMapping
	public ResponseEntity<CustomApiResponse<List<ApprovalContentQueryDTO>>> findAll() {
		List<ApprovalContentQueryDTO> dtoList = service.findAll();
		ResponseCode result = ResponseCode.SUCCESS;
		return ResponseEntity.status(result.getHttpStatus())
			.body(CustomApiResponse.of(result, dtoList));
	}

	@Operation(summary = "결재 문서 내용 상세 조회", description = "결재 문서 내용을 id로 상세 조회합니다.")
	@GetMapping("{id}")
	public ResponseEntity<CustomApiResponse<ApprovalContentQueryDTO>> findById(@PathVariable("id") Integer id) {
		ApprovalContentQueryDTO dto = service.findById(id);
		ResponseCode result = ResponseCode.SUCCESS;
		return ResponseEntity.status(result.getHttpStatus())
				.body(CustomApiResponse.of(result, dto));
	}

	@Operation(summary = "결재 문서 별 내용 조회", description = "결재 문서 별 내용을 조회합니다.")
	@GetMapping("/approval/{approvalId}")
	public  ResponseEntity<CustomApiResponse<List<ApprovalContentQueryDTO>>> findByApprovalId(@PathVariable("approvalId") Integer approvalId) {
		List<ApprovalContentQueryDTO> dtoList = service.findByApprovalId(approvalId);
		ResponseCode result = ResponseCode.SUCCESS;
		return ResponseEntity.status(result.getHttpStatus())
				.body(CustomApiResponse.of(result, dtoList));
	}
}