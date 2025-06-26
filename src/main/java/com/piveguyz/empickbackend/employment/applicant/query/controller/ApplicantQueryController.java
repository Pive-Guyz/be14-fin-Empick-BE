package com.piveguyz.empickbackend.employment.applicant.query.controller;


import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicantFullInfoDTO;
import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicantQueryDTO;
import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicationQueryDTO;
import com.piveguyz.empickbackend.employment.applicant.query.service.ApplicantQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "지원자,지원서 API", description = "지원자,지원서 관련 API")
@RestController
@RequestMapping("api/v1/employment/applicant")
@RequiredArgsConstructor
public class ApplicantQueryController {

    private final ApplicantQueryService applicantQueryService;

    @Operation(summary = "지원자 전체 조회", description = "지원자 전체를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
    })
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<ApplicantQueryDTO>>> getAllApplicant() {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, applicantQueryService.findAllApplicant()));
    }

    @Operation(summary = "지원자 단건 조회", description = "지원자 단건을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "404", description = "지원자를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<CustomApiResponse<ApplicantQueryDTO>> getApplicantById(@PathVariable Integer id) {
        ApplicantQueryDTO dto = applicantQueryService.findApplicantById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }

    @Operation(summary = "이름으로 지원자 조회", description = "이름 일부 또는 전체로 지원자들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검색 결과 조회 성공"),
            @ApiResponse(responseCode = "400", description = "검색어가 올바르지 않거나 누락되었습니다.")
    })
    @GetMapping("/search")
    public ResponseEntity<CustomApiResponse<List<ApplicantQueryDTO>>> searchApplicantsByName(
            @RequestParam("name") String name) {
        List<ApplicantQueryDTO> dtoList = applicantQueryService.searchApplicantsByName(name);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @Operation(summary = "지원자 상세 리스트 조회", description = """
        지원자의 상세 정보(지원서, 채용공고, 직무 포함)를 조인하여 전체 목록을 조회합니다.
        일반적으로 목록은 지원자당 하나씩 최신 지원서 기준으로 구성됩니다.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 지원자 상세 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "지원자 데이터가 존재하지 않습니다.")
    })
    @GetMapping("/applicant-list")
    public ResponseEntity<CustomApiResponse<List<ApplicantFullInfoDTO>>> getApplicantList() {
        List<ApplicantFullInfoDTO> list = applicantQueryService.findAllApplicantFullInfo();
        if (list.isEmpty()) {
            return ResponseEntity.status(ResponseCode.NOT_FOUND.getHttpStatus())
                    .body(CustomApiResponse.of(ResponseCode.NOT_FOUND, null));
        }
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, list));
    }
}

