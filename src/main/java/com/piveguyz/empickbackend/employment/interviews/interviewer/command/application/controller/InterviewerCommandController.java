package com.piveguyz.empickbackend.employment.interviews.interviewer.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviews.facade.InterviewFacade;
import com.piveguyz.empickbackend.employment.interviews.interviewer.command.application.dto.InterviewerCommandDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewer.command.application.service.InterviewerCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "면접 담당자 API", description = "면접 담당자 관리")
@RestController
@RequestMapping("/api/v1/employment/interviewer")
@SecurityRequirement(name = "bearerAuth")
public class InterviewerCommandController {
    private final InterviewerCommandService service;
    private final InterviewFacade facade;

    @Autowired
    public InterviewerCommandController(InterviewerCommandService service, InterviewFacade interviewFacade) {
        this.service = service;
        this.facade = interviewFacade;
    }

    @Operation(
            summary = "면접 담당자 등록",
            description = """
    - 면접 담당자를 등록합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2560", description = "이미 해당 면접 담당자가 존재합니다.")
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<InterviewerCommandDTO>> createInterviewer(@RequestBody InterviewerCommandDTO dto) {
        InterviewerCommandDTO createdDTO = service.createInterviewer(dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, createdDTO));
    }

    @Operation(
            summary = "면접 담당자 평가 수정",
            description = """
    - 면접 담당자의 총평을 수정합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2561", description = "해당 면접 담당자가 존재하지 않습니다.")
    })
    @PatchMapping("/{id}/review")
    public ResponseEntity<CustomApiResponse<InterviewerCommandDTO>> updateInterviewerReview(@PathVariable Integer id,
                                                                                 @RequestParam("review") String review){
        InterviewerCommandDTO updatedDTO = service.updateInterviewerReview(id, review);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, updatedDTO));
    }

    @Operation(
            summary = "면접 담당자 삭제",
            description = """
    - 면접 담당자를 삭제합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2561", description = "해당 면접 담당자가 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2562", description = "면접 담당자가 이미 점수를 입력하였습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<InterviewerCommandDTO>> deleteInterviewer(@PathVariable("id") Integer id) {
        InterviewerCommandDTO deletedDTO = facade.deleteInterviewer(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, deletedDTO));
    }
}
