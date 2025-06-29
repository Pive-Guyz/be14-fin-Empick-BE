package com.piveguyz.empickbackend.employment.interviews.interviewer.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.query.dto.InterviewCriteriaQueryDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.query.service.InterviewCriteriaQueryService;
import com.piveguyz.empickbackend.employment.interviews.interviewer.query.dto.InterviewerQueryDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewer.query.service.InterviewerQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "면접 담당자 API", description = "면접 담당자 관리")
@RestController
@RequestMapping("/api/v1/employment/interviewer")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class InterviewerQueryController {
    private final InterviewerQueryService service;

    @Operation(
            summary = "면접 담당자 전체 조회",
            description = """
    - 면접 담당자를 모두 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<InterviewerQueryDTO>>> findAll() {
        List<InterviewerQueryDTO> dtoList = service.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @Operation(
            summary = "면접 담당자 id로 조회",
            description = """
    - id 값을 가지는 면접 담당자를 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2561", description = "해당 면접 담당자가 존재하지 않습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<InterviewerQueryDTO>> findById(@PathVariable("id") Integer id) {
        InterviewerQueryDTO dto = service.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }

    @Operation(
            summary = "면접 id로 면접 담당자 조회",
            description = """
    - 면접을 담당하는 면접 담당자들을 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/interview/{interviewId}")
    public ResponseEntity<CustomApiResponse<List<InterviewerQueryDTO>>> findByInterviewId(@PathVariable("interviewId") Integer interviewId) {
        List<InterviewerQueryDTO> dtoList = service.findByInterviewId(interviewId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @GetMapping("/interview/{interviewId}/member/{memberId}")
    public ResponseEntity<CustomApiResponse<InterviewerQueryDTO>> findByInterviewMemberId(@PathVariable("interviewId") Integer interviewId,
                                                                                               @PathVariable("memberId") Integer memberId) {
        InterviewerQueryDTO dto = service.findByInterviewMemberId(interviewId, memberId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }
}