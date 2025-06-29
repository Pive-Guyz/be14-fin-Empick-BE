package com.piveguyz.empickbackend.approvals.approval.query.controller;

import com.piveguyz.empickbackend.approvals.approval.query.dto.ApprovalQueryDTO;
import com.piveguyz.empickbackend.approvals.approval.query.dto.ApprovalReceivedQueryDTO;
import com.piveguyz.empickbackend.approvals.approval.query.dto.ApprovalRequestedListQueryDTO;
import com.piveguyz.empickbackend.approvals.approval.query.service.ApprovalQueryService;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "결재 문서 API", description = "결재 문서 관련 전체 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/approval/documents")
@SecurityRequirement(name = "bearerAuth")
public class ApprovalQueryController {
	private final ApprovalQueryService service;

	@Operation(summary = "결재 문서 전체 조회", description = "전체 결재 문서를 조회합니다.")
	@GetMapping
	public ResponseEntity<CustomApiResponse<List<ApprovalQueryDTO>>> findAll() {
		List<ApprovalQueryDTO> dtoList = service.findAll();
		ResponseCode result = ResponseCode.SUCCESS;
		return ResponseEntity.status(result.getHttpStatus())
			.body(CustomApiResponse.of(result, dtoList));
	}

	@Operation(summary = "결재 문서 상세 조회", description = "결재 문서를 id로 상세 조회합니다.")
	@GetMapping("/{id}")
	public ResponseEntity<CustomApiResponse<ApprovalQueryDTO>> findById(@PathVariable("id") Integer id) {
		ApprovalQueryDTO dto = service.findById(id);
		ResponseCode result = ResponseCode.SUCCESS;
		return ResponseEntity.status(result.getHttpStatus())
				.body(CustomApiResponse.of(result, dto));
	}
	@Operation(summary = "결재 유형별 결재 문서 조회", description = "결재 유형별로 문서를 조회합니다.")
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<CustomApiResponse<List<ApprovalQueryDTO>>> findByCategoryId(@PathVariable("categoryId") Integer categoryId) {
		List<ApprovalQueryDTO> dtoList = service.findByCategoryId(categoryId);
		ResponseCode result = ResponseCode.SUCCESS;
		return ResponseEntity.status(result.getHttpStatus())
				.body(CustomApiResponse.of(result, dtoList));
	}

	@Operation(summary = "작성자별 결재 문서 조회", description = "작성자별로 문서를 조회합니다.")
	@GetMapping("/writer/{writerId}")
	public ResponseEntity<CustomApiResponse<List<ApprovalQueryDTO>>> findByWriterId(@PathVariable("writerId") Integer writerId) {
		List<ApprovalQueryDTO> dtoList = service.findByWriterId(writerId);
		ResponseCode result = ResponseCode.SUCCESS;
		return ResponseEntity.status(result.getHttpStatus())
				.body(CustomApiResponse.of(result, dtoList));
	}

	// 자신이 결재자인 결재문서 목록
	@Operation(summary = "자신이 결재자인 결재문서 목록", description = "자신이 결재자인 결재문서 목록을 조회합니다.")
	@GetMapping("/received")
	public ResponseEntity<CustomApiResponse<List<ApprovalReceivedQueryDTO>>> findReceivedApprovals(
		@RequestParam("memberId") Integer memberId) {

		List<ApprovalReceivedQueryDTO> dtoList = service.findReceivedApprovals(memberId);
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS, dtoList));
	}

	// 내가 요청한 결재문서 목록
	@Operation(summary = "내가 요청한 결재문서 목록", description = "내가 요청한 결재문서 목록을 조회합니다.")
	@GetMapping("/requested")
	public ResponseEntity<CustomApiResponse<List<ApprovalRequestedListQueryDTO>>> findRequestedApprovals(
		@RequestParam("memberId") Integer memberId) {

		List<ApprovalRequestedListQueryDTO> dtoList = service.findRequestedApprovals(memberId);
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS, dtoList));
	}
}