package com.piveguyz.empickbackend.employment.applicant.query.controller;


import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicationQueryDTO;
import com.piveguyz.empickbackend.employment.applicant.query.service.ApplicationQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/employment/application")
@RequiredArgsConstructor
public class ApplicationQueryController {

    private final ApplicationQueryService applicationQueryService;

    @Operation(summary = "지원서 전체 조회", description = "지원서 전체를 조회합니다.")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
    })

    @GetMapping
    public ResponseEntity<CustomApiResponse<List<ApplicationQueryDTO>>> getAllApplication() {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, applicationQueryService.findAllApplication()));
    }

    @GetMapping("{id}")
    @Operation(summary = "지원서 단건 조회", description = "지원서의 단건을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 ID 없음")
    })
    public ResponseEntity<CustomApiResponse<ApplicationQueryDTO>> getApplicationById(
            @Parameter(description = "조회할 지원서 ID", required = true)
            @PathVariable("id") int id) {

        ApplicationQueryDTO result = applicationQueryService.findApplicationById(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, result));
    }

    @GetMapping("/applicant/{applicantId}")
    @Operation(
            summary = "지원자 id로 지원서 조회",
            description = """
    - 지원자 id로 해당 지원자가 작성한 지원서를 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    public ResponseEntity<CustomApiResponse<ApplicationQueryDTO>> getApplicationByApplicantId(@PathVariable("applicantId") Integer applicantId){
        ApplicationQueryDTO dto = applicationQueryService.findApplicationByApplicantId(applicantId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }

}
