package com.piveguyz.empickbackend.employment.interviews.interviewCriteria.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.query.dto.InterviewCriteriaQueryDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.query.service.InterviewCriteriaQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "면접 평가 기준 API", description = "면접 평가 기준 관리")
@RestController
@RequestMapping("/api/v1/employment/interview-criteria")
@SecurityRequirement(name = "bearerAuth")
public class InterviewCriteriaQueryController {
    private final InterviewCriteriaQueryService service;

    @Autowired
    public InterviewCriteriaQueryController(InterviewCriteriaQueryService service) {
        this.service = service;
    }

    @Operation(
            summary = "면접 평가 기준 전체 조회",
            description = """
    - 면접 평가 기준을 모두 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<InterviewCriteriaQueryDTO>>> findAll() {
        List<InterviewCriteriaQueryDTO> dtoList = service.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @Operation(
            summary = "면접 평가 기준 id로 조회",
            description = """
    - id 값을 가지는 면접 평가 기준을 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2500", description = "존재하지 않는 면접 기준입니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<InterviewCriteriaQueryDTO>> findById(@PathVariable("id") Integer id) {
        InterviewCriteriaQueryDTO dto = service.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }

    @Operation(
            summary = "제목으로 면접 평가 기준 검색",
            description = """
    - 제목으로 면접 평가 기준을 검색합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/title")
    public ResponseEntity<CustomApiResponse<List<InterviewCriteriaQueryDTO>>> searchByTitle(@RequestParam("title") String title) {
        List<InterviewCriteriaQueryDTO> dtoList = service.searchByTitle(title);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @Operation(
            summary = "면접 평가표 id로 면접 평가 기준 검색",
            description = """
    - 면접 평가표 id로 면접 평가 기준을 검색합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/sheet/{sheetId}")
    public ResponseEntity<CustomApiResponse<List<InterviewCriteriaQueryDTO>>> findBySheetId(@PathVariable("sheetId") Integer sheetId) {
        List<InterviewCriteriaQueryDTO> dtoList = service.findBySheetId(sheetId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }
}