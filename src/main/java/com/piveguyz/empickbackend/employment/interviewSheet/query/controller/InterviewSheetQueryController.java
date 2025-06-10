package com.piveguyz.empickbackend.employment.interviewSheet.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewSheet.query.dto.InterviewSheetQueryDTO;
import com.piveguyz.empickbackend.employment.interviewSheet.query.service.InterviewSheetQueryService;
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

@Tag(name="면접 평가표 Query API", description="면접 평가표 조회")
@RestController
@RequestMapping("/api/v1/employment/interviewSheet")
public class InterviewSheetQueryController {
    private final InterviewSheetQueryService service;

    @Autowired
    public InterviewSheetQueryController(InterviewSheetQueryService service) {
        this.service = service;
    }

    @Operation(
            summary = "면접 평가표 전체 조회",
            description = """
                    - 면접 평가표를 전부 조회합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/findAll")
    public ResponseEntity<CustomApiResponse<List<InterviewSheetQueryDTO>>> findAll() {
        List<InterviewSheetQueryDTO> dtoList = service.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @Operation(
            summary = "면접 평가표 id로 조회",
            description = """
                    - 면접 평가표를 id로 조회합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2520", description = "존재하지 않는 항목입니다.")

    })
    @GetMapping("/findById")
    public ResponseEntity<CustomApiResponse<InterviewSheetQueryDTO>> findById(@RequestParam("id") Integer id) {
        InterviewSheetQueryDTO dto = service.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }

    @Operation(
            summary = "면접 평가표 이름으로 검색",
            description = """
                    - 면접 평가표를 이름으로 검색합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/searchByName")
    public ResponseEntity<CustomApiResponse<List<InterviewSheetQueryDTO>>> searchByName(@RequestParam("name") String name) {
        List<InterviewSheetQueryDTO> dtoList = service.searchByName(name);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }
}
