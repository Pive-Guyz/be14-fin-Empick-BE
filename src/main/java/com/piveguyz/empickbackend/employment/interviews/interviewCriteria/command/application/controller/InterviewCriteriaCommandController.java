package com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.application.dto.InterviewCriteriaCommandDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.application.service.InterviewCriteriaCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "면접 평가 기준 API", description = "면접 평가 기준 관리")
@RestController
@RequestMapping("/api/v1/employment/interview-criteria")
@SecurityRequirement(name = "bearerAuth")
public class InterviewCriteriaCommandController {
    private final InterviewCriteriaCommandService interviewCriteriaCommandService;

    @Autowired
    public InterviewCriteriaCommandController(InterviewCriteriaCommandService interviewCriteriaCommandService) {
        this.interviewCriteriaCommandService = interviewCriteriaCommandService;
    }

    @Operation(
            summary = "면접 평가 기준 등록",
            description = """
    - 면접 평가 기준을 등록합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2510", description = "내용을 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2511", description = "상세 내용을 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2512", description = "이미 존재하는 내용입니다.")
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<InterviewCriteriaCommandDTO>> createCriteria(@RequestBody InterviewCriteriaCommandDTO dto) {
        InterviewCriteriaCommandDTO createdDTO = interviewCriteriaCommandService.createCriteria(dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, createdDTO));
    }

    @Operation(
            summary = "면접 평가 기준 수정",
            description = """
    - 면접 평가 기준을 수정합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2510", description = "내용을 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2511", description = "상세 내용을 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2512", description = "이미 존재하는 내용입니다.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<InterviewCriteriaCommandDTO>> updateCriteria(@PathVariable("id") Integer id,
                                                                                         @RequestBody InterviewCriteriaCommandDTO dto) {
        InterviewCriteriaCommandDTO updatedDTO = interviewCriteriaCommandService.updateCriteria(id, dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, updatedDTO));
    }

    @Operation(
            summary = "면접 평가 기준 삭제",
            description = """
    - 면접 평가 기준을 삭제합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2500", description = "존재하지 않는 면접 기준입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2511", description = "상세 내용을 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2512", description = "이미 존재하는 내용입니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<InterviewCriteriaCommandDTO>> deleteCriteria(@PathVariable("id") Integer id) {
        InterviewCriteriaCommandDTO deletedDTO = interviewCriteriaCommandService.deleteCriteria(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, deletedDTO));
    }
}
