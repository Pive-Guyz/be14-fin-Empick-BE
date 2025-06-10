package com.piveguyz.empickbackend.employment.interviewScore.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewScore.query.dto.InterviewScoreQueryDTO;
import com.piveguyz.empickbackend.employment.interviewScore.query.service.InterviewScoreQueryService;
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

@Tag(name = "면접 평가 점수 Query API", description = "면접 평가 점수 조회")
@RestController
@RequestMapping("/api/v1/employment/interviewScore")
public class InterviewScoreQueryController {
    private final InterviewScoreQueryService interviewScoreQueryService;

    @Autowired
    public InterviewScoreQueryController(InterviewScoreQueryService interviewScoreQueryService) {
        this.interviewScoreQueryService = interviewScoreQueryService;
    }

    @Operation(
            summary = "면접 평가 점수 전체 조회",
            description = """
    - 모든 면접 평가 점수를 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
    })
    @GetMapping("/findAll")
    public ResponseEntity<CustomApiResponse<List<InterviewScoreQueryDTO>>> findAll() {
        List<InterviewScoreQueryDTO> dtoList = interviewScoreQueryService.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @Operation(
            summary = "면접 평가 점수 id로 조회",
            description = """
    - id로 면접 평가 점수를 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2550", description = "존재하지 않습니다.")
    })
    @GetMapping("/findById")
    public ResponseEntity<CustomApiResponse<InterviewScoreQueryDTO>> findById(@RequestParam("id") Integer id) {
        InterviewScoreQueryDTO dto = interviewScoreQueryService.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }

    @Operation(
            summary = "면접 평가 점수 면접id로 조회",
            description = """
    - 면접id로 그 면접에서의 평가 점수를 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
    })
    @GetMapping("/findByInterviewId")
    public ResponseEntity<CustomApiResponse<List<InterviewScoreQueryDTO>>> findByInterviewId(@RequestParam("interviewId") Integer interviewId) {
        List<InterviewScoreQueryDTO> dtoList = interviewScoreQueryService.findByInterviewId(interviewId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }
}
