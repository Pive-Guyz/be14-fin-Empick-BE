package com.piveguyz.empickbackend.employment.interview.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interview.command.application.dto.InterviewCommandDTO;
import com.piveguyz.empickbackend.employment.interview.command.application.service.InterviewCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="면접 Command API", description="면접 관리")
@RestController
@RequestMapping("/api/v1/employment/interview")
public class InterviewCommandController {
    private final InterviewCommandService service;

    @Autowired
    public InterviewCommandController(InterviewCommandService service) {
        this.service = service;
    }

    @Operation(
            summary = "면접 생성",
            description = """
                    - 면접 일정을 새로 등록합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse<InterviewCommandDTO>> create(@RequestBody InterviewCommandDTO dto) {
        InterviewCommandDTO createdDTO = service.create(dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, createdDTO));
    }

    @Operation(
            summary = "면접 수정",
            description = """
                    - 면접 일정을 수정합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @PostMapping("/update")
    public ResponseEntity<CustomApiResponse<InterviewCommandDTO>> update(@RequestParam("id") Integer id,
                                                                         @RequestBody InterviewCommandDTO dto) {
        InterviewCommandDTO updatedDTO = service.update(id, dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, updatedDTO));
    }

    @Operation(
            summary = "면접 삭제",
            description = """
                    - 면접 일정을 삭제합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<CustomApiResponse<InterviewCommandDTO>> delete(Integer id) {
        InterviewCommandDTO deletedDTO = service.delete(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, deletedDTO));
    }
}
