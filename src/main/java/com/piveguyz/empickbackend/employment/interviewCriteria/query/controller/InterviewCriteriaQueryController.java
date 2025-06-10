package com.piveguyz.empickbackend.employment.interviewCriteria.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewCriteria.query.dto.InterviewCriteriaQueryDTO;
import com.piveguyz.empickbackend.employment.interviewCriteria.query.service.InterviewCriteriaQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "면접 평가 기준 Query API", description = "면접 평가 기준 조회")
@RestController
@RequestMapping("/api/v1/employment/interviewCriteria")
public class InterviewCriteriaQueryController {
    private final InterviewCriteriaQueryService interviewCriteriaQueryService;

    @Autowired
    public InterviewCriteriaQueryController(InterviewCriteriaQueryService interviewCriteriaQueryService) {
        this.interviewCriteriaQueryService = interviewCriteriaQueryService;
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
    @GetMapping("/findAll")
    public ResponseEntity<CustomApiResponse<List<InterviewCriteriaQueryDTO>>> findAll() {
        List<InterviewCriteriaQueryDTO> interviewCriteriaQueryDTOList = interviewCriteriaQueryService.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, interviewCriteriaQueryDTOList));
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
    @GetMapping("/findById")
    public ResponseEntity<CustomApiResponse<InterviewCriteriaQueryDTO>> findById(@RequestParam("id") Integer id) {
        InterviewCriteriaQueryDTO interviewCriteriaQueryDTO = interviewCriteriaQueryService.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, interviewCriteriaQueryDTO));
    }

    @Operation(
            summary = "내용으로 면접 평가 기준 검색",
            description = """
    - 내용을 가지는 면접 평가 기준을 검색합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/searchByContent")
    public ResponseEntity<CustomApiResponse<List<InterviewCriteriaQueryDTO>>> searchByContent(@RequestParam("content") String content) {
        List<InterviewCriteriaQueryDTO> interviewCriteriaQueryDTOList = interviewCriteriaQueryService.searchByContent(content);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, interviewCriteriaQueryDTOList));
    }
}