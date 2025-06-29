package com.piveguyz.empickbackend.approvals.approval.command.application.controller;

import com.piveguyz.empickbackend.approvals.approval.command.application.dto.ApproveRequestDTO;
import com.piveguyz.empickbackend.approvals.approval.command.application.dto.CreateApprovalCommandDTO;
import com.piveguyz.empickbackend.approvals.approval.command.application.dto.RejectRequestDTO;
import com.piveguyz.empickbackend.approvals.approval.command.application.service.ApprovalCommandService;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "결재 API", description = "결재 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/approval/documents")
@SecurityRequirement(name = "bearerAuth")
public class ApprovalCommandController {

    private final ApprovalCommandService approvalCommandService;

    @Operation(
            summary = "결재 문서 생성",
            description = """
                    결재 문서를 생성합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<Integer>> createApproval(@RequestBody CreateApprovalCommandDTO dto) {
        Integer approvalId = approvalCommandService.createApproval(dto);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, approvalId));
    }
    
    @Operation(
            summary = "결재 문서 승인",
            description = """
                    결재 문서를 승인합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PostMapping("/{approvalId}/approve")
    public ResponseEntity<CustomApiResponse<Void>> approve(
            @PathVariable Integer approvalId,
            @RequestBody ApproveRequestDTO dto
    ) {
        approvalCommandService.approve(approvalId, dto);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS));
    }

    @Operation(
            summary = "결재 문서 반려",
            description = """
                    결재 문서를 반려합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PostMapping("/{approvalId}/reject")
    public ResponseEntity<CustomApiResponse<Void>> reject(
            @PathVariable Integer approvalId,
            @RequestBody RejectRequestDTO dto) {
        approvalCommandService.reject(approvalId, dto);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS));
    }
}
