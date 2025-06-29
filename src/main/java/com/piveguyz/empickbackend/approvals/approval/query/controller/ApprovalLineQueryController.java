package com.piveguyz.empickbackend.approvals.approval.query.controller;

import com.piveguyz.empickbackend.approvals.approval.query.dto.ApprovalLineQueryDTO;
import com.piveguyz.empickbackend.approvals.approval.query.mapper.ApprovalQueryMapper;
import com.piveguyz.empickbackend.approvals.approval.query.service.ApprovalLineService;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "결재 문서 API", description = "결재 문서 관련 전체 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/approval/categories")
@SecurityRequirement(name = "bearerAuth")
public class ApprovalLineQueryController {

    private final ApprovalLineService approvalLineService;

    // 결재라인 미리보기
    @Operation(summary = "결재라인 미리보기", description = "결재라인을 미리 확인할 수 있습니다.")
    @GetMapping("/{categoryId}/lines")
    public ResponseEntity<CustomApiResponse<List<ApprovalLineQueryDTO>>> getApprovalLinePreview(
            @PathVariable Integer categoryId,
            @RequestParam Integer writerId
    ) {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, approvalLineService.getApprovalLinePreview(categoryId, writerId)));
    }
}
