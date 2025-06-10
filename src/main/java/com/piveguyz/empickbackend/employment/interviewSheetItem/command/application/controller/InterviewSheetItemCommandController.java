package com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.dto.InterviewSheetItemCommandDTO;
import com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.service.InterviewSheetItemCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="면접 평가표 항목 Command API", description="면접 평가표 항목 관리")
@RestController
@RequestMapping("/api/v1/employment/interviewSheetItem")
public class InterviewSheetItemCommandController {
    private final InterviewSheetItemCommandService service;

    @Autowired
    public InterviewSheetItemCommandController(InterviewSheetItemCommandService service) {
        this.service = service;
    }

    @Operation(
            summary = "면접 평가표 항목 생성",
            description = """
                    - 면접 평가표에 항목을 새로 등록합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2531", description = "이미 존재하는 항목입니다.")
    })
    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse<InterviewSheetItemCommandDTO>> create(@RequestBody InterviewSheetItemCommandDTO dto) {
        InterviewSheetItemCommandDTO createdDTO = service.create(dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, createdDTO));
    }

    @Operation(
            summary = "면접 평가표 항목 삭제",
            description = """
                    - 면접 평가표에 등록되어 있던 항목을 삭제합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2530", description = "존재하지 않는 항목입니다.")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<CustomApiResponse<InterviewSheetItemCommandDTO>> delete(@RequestParam("id") Integer id){
        InterviewSheetItemCommandDTO dto = service.delete(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }
}
