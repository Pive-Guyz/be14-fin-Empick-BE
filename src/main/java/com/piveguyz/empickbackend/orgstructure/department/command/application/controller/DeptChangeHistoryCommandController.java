package com.piveguyz.empickbackend.orgstructure.department.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.orgstructure.department.command.application.service.DeptChangeHistoryCommandService;
import com.piveguyz.empickbackend.orgstructure.department.command.domain.aggregate.DeptChangeHistoryEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/dept-change-history")
@Tag(name = "부서 이동 내역 API", description = "부서 이동 내역 생성, 조회 API")
@RequiredArgsConstructor
public class DeptChangeHistoryCommandController {

    private final DeptChangeHistoryCommandService deptChangeHistoryCommandService;

    @PostMapping
    @Operation(summary = "부서 이동 기록 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DeptChangeHistoryEntity.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomApiResponse.class)))
    })
    public ResponseEntity<CustomApiResponse<DeptChangeHistoryEntity>> createDeptChangeHistory(
            @Parameter(description = "사원 ID", example = "1")
            @RequestParam Integer memberId,
            @Parameter(description = "부서명", example = "인사팀")
            @RequestParam String deptName,
            @Parameter(description = "직책", example = "팀장")
            @RequestParam(required = false) String positionName,
            @Parameter(description = "직무", example = "HRM")
            @RequestParam(required = false) String jobName,
            @Parameter(description = "직급명", example = "과장")
            @RequestParam String rankName,
            @Parameter(description = "근무 시작일 (yyyy-MM-dd'T'HH:mm:ss)", example = "2025-06-01T09:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime workStartAt
    ) {
        DeptChangeHistoryEntity result = deptChangeHistoryCommandService.createDeptChangeHistory(memberId, deptName, positionName, jobName, rankName, workStartAt);
        return ResponseEntity.status(ResponseCode.CREATED.getHttpStatus()).body(CustomApiResponse.of(ResponseCode.CREATED, result));
    }

    @PatchMapping("/{historyId}/end")
    @Operation(summary = "부서 이동 기록 종료일 업데이트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "이동 기록을 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomApiResponse.class)))
    })
    public ResponseEntity<CustomApiResponse<Void>> endDeptChangeHistory(
            @Parameter(description = "이동 기록 ID", example = "1")
            @PathVariable Integer historyId,
            @Parameter(description = "근무 종료일 (yyyy-MM-dd'T'HH:mm:ss)", example = "2025-06-30T18:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime workEndAt
    ) {
        deptChangeHistoryCommandService.endDeptChangeHistory(historyId, workEndAt);
        return ResponseEntity.ok(CustomApiResponse.of(ResponseCode.SUCCESS));
    }
}
