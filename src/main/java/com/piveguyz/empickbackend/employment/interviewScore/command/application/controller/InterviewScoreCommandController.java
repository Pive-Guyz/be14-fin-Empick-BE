package com.piveguyz.empickbackend.employment.interviewScore.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewScore.command.application.dto.InterviewScoreCommandDTO;
import com.piveguyz.empickbackend.employment.interviewScore.command.application.service.InterviewScoreCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "면접 평가 점수 Command API", description = "면접 평가 점수 관리")
@RestController
@RequestMapping("/api/v1/employment/interviewScore")
public class InterviewScoreCommandController {
    private final InterviewScoreCommandService service;

    public InterviewScoreCommandController(InterviewScoreCommandService service) {
        this.service = service;
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
    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse<InterviewScoreCommandDTO>> createInterviewScore(@RequestBody InterviewScoreCommandDTO dto) {
        InterviewScoreCommandDTO createdDTO = service.create(dto);
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
    @PostMapping("/update")
    public ResponseEntity<CustomApiResponse<InterviewScoreCommandDTO>> updateInterviewScore(@RequestParam("id") Integer id,
                                                                                            @RequestBody InterviewScoreCommandDTO dto) {
        InterviewScoreCommandDTO updatedDTO = service.update(id, dto);
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
    @DeleteMapping("/delete")
    public ResponseEntity<CustomApiResponse<InterviewScoreCommandDTO>> deleteInterviewScore(@RequestParam("id") Integer id){
        InterviewScoreCommandDTO deletedDTO = service.delete(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, deletedDTO));
    }
}

