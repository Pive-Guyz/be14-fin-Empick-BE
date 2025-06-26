package com.piveguyz.empickbackend.employment.applicant.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicant.command.application.dto.ApplicationResponseCommandDTO;
import com.piveguyz.empickbackend.employment.applicant.command.application.service.ApplicationResponseCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/application-response")
@Tag(name = "지원자,지원서 API", description = "지원자,지원서 관련 API")
public class ApplicationResponseCommandController {

    private final ApplicationResponseCommandService applicationResponseCommandService;

    @PostMapping
    @Operation(summary = "지원서 항목별 응답 등록", description = "지원서 항목별 응답을 등록합니다.")
    public ResponseEntity<CustomApiResponse<ApplicationResponseCommandDTO>> createApplicationResponse(
            @RequestBody @Valid ApplicationResponseCommandDTO dto
    ) {
        ApplicationResponseCommandDTO savedDto = applicationResponseCommandService.saveResponse(dto);
        return ResponseEntity
                .status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, savedDto));
    }
}
