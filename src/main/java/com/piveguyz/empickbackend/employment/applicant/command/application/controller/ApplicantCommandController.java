package com.piveguyz.empickbackend.employment.applicant.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicant.command.application.dto.ApplicantCommandDTO;
import com.piveguyz.empickbackend.employment.applicant.command.application.service.ApplicantCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "지원자,지원서 API", description = "지원자,지원서 관련 API")
@RestController
@RequestMapping("/api/v1/employment/applicant")
@RequiredArgsConstructor
public class ApplicantCommandController {

    private final ApplicantCommandService applicantCommandService;

    // 지원자 등록
    @Operation(summary = "지원자 등록", description = "지원자를 등록합니다.")
    @ApiResponses(value = {})
    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse<ApplicantCommandDTO>> createApplicant(
            @RequestBody @Valid ApplicantCommandDTO applicantCommandDTO) {
        ApplicantCommandDTO created = applicantCommandService.createApplicant(applicantCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, created));
    }
}