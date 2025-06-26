package com.piveguyz.empickbackend.employment.applicant.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicationResponseQueryDTO;
import com.piveguyz.empickbackend.employment.applicant.query.service.ApplicationResponseQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "지원자,지원서 API", description = "지원자,지원서 관련 API")
@RestController
@RequestMapping("/api/v1/employment/application-response")
@RequiredArgsConstructor
public class ApplicationResponseQueryController {

    private final ApplicationResponseQueryService applicationResponseQueryService;

    @Operation(summary = "지원서 항목별 응답 조회", description = "지원서 항목별 응답을 조회합니다.")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })

    @GetMapping
    public ResponseEntity<CustomApiResponse<List<ApplicationResponseQueryDTO>>> getAllApplicationResponse() {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, applicationResponseQueryService.findAllApplicationResponse()));
    }

    @GetMapping("/application/{applicationId}")
    @Operation(summary = "지원서 ID로 지원서 항목별 응답 조회", description = "지원서 ID로 지원서 항목별 응답을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "404", description = "해당 지원서 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<CustomApiResponse<List<ApplicationResponseQueryDTO>>> getApplicationResponsesByApplicationId(
            @PathVariable("applicationId") Integer applicationId) {
        List<ApplicationResponseQueryDTO> responses = applicationResponseQueryService.findApplicationResponsesByApplicationId(applicationId);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, responses));
    }
}

