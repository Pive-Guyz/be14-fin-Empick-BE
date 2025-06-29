package com.piveguyz.empickbackend.employment.interviews.interviewScore.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviews.facade.InterviewFacade;
import com.piveguyz.empickbackend.employment.interviews.interviewScore.command.application.dto.InterviewScoreCommandDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewScore.command.application.service.InterviewScoreCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "면접 평가 점수 API", description = "면접 평가 점수 관리")
@RestController
@RequestMapping("/api/v1/employment/interview-score")
@SecurityRequirement(name = "bearerAuth")
public class InterviewScoreCommandController {
    private final InterviewScoreCommandService service;
    private final InterviewFacade facade;

    @Autowired
    public InterviewScoreCommandController(InterviewScoreCommandService service, InterviewFacade facade)
    {
        this.service = service;
        this.facade = facade;
    }

    @Operation(
            summary = "면접 평가 점수 등록",
            description = """
    - 면접 평가 점수를 등록합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2551", description = "점수를 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2552", description = "평가를 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2554", description = "이미 평가 점수가 등록되어 있습니다."),
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<InterviewScoreCommandDTO>> createInterviewScore(@RequestBody InterviewScoreCommandDTO dto) {
        InterviewScoreCommandDTO createdDTO = facade.createInterviewScore(dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, createdDTO));
    }

    @Operation(
            summary = "면접 평가 점수 수정",
            description = """
    - 면접 평가 점수를 수정합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2550", description = "존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2551", description = "점수를 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2552", description = "평가를 입력하지 않았습니다.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<InterviewScoreCommandDTO>> updateInterviewScore(@PathVariable("id") Integer id,
                                                                                            @RequestBody InterviewScoreCommandDTO dto) {
        InterviewScoreCommandDTO updatedDTO = facade.updateInterviewScore(id, dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, updatedDTO));
    }

    @Operation(
            summary = "면접 평가 점수 삭제",
            description = """
    - 면접 평가 점수를 삭제합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2550", description = "존재하지 않습니다."),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<InterviewScoreCommandDTO>> deleteInterviewScore(@PathVariable("id") Integer id){
        InterviewScoreCommandDTO deletedDTO = service.delete(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, deletedDTO));
    }
}

