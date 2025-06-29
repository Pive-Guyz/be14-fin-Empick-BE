package com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.application.dto.InterviewSheetCommandDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.application.service.InterviewSheetCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="면접 평가표 API", description="면접 평가표 관리")
@RestController
@RequestMapping("/api/v1/employment/interview-sheet")
@SecurityRequirement(name = "bearerAuth")
public class InterviewSheetCommandController {
    private final InterviewSheetCommandService service;

    @Autowired
    public InterviewSheetCommandController(InterviewSheetCommandService service) {
        this.service = service;
    }

    @Operation(
            summary = "면접 평가표 등록",
            description = """
                    - 면접 평가표를 등록합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2521", description = "이름을 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2522", description = "중복된 이름이 존재합니다.")
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<InterviewSheetCommandDTO>> createSheet(@RequestBody InterviewSheetCommandDTO dto) {
        InterviewSheetCommandDTO createdDTO = service.createSheet(dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, createdDTO));
    }

    @Operation(
            summary = "면접 평가표 수정",
            description = """
                    - 면접 평가표를 수정합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2520", description = "존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2521", description = "이름을 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2522", description = "중복된 이름이 존재합니다.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<InterviewSheetCommandDTO>> updateSheet(@PathVariable("id") Integer id,
                                                                                   @RequestBody InterviewSheetCommandDTO dto) {
        InterviewSheetCommandDTO updatedDTO = service.updateSheet(id, dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, updatedDTO));
    }

    @Operation(
            summary = "면접 평가표 삭제",
            description = """
                    - 면접 평가표를 삭제합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2520", description = "존재하지 않습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<InterviewSheetCommandDTO>> deleteSheet(@PathVariable("id") Integer id) {
        InterviewSheetCommandDTO deletedDTO = service.deleteSheet(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, deletedDTO));
    }
}
