package com.piveguyz.empickbackend.employment.interviewSheetItem.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewSheetItem.query.dto.InterviewSheetItemQueryDTO;
import com.piveguyz.empickbackend.employment.interviewSheetItem.query.service.InterviewSheetItemQueryService;
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

@Tag(name="면접 평가표 항목 Query API", description="면접 평가표 항목 조회")
@RestController
@RequestMapping("/api/v1/employment/interviewSheetItem")
public class InterviewSheetItemQueryController {
    private final InterviewSheetItemQueryService service;

    @Autowired
    public InterviewSheetItemQueryController(InterviewSheetItemQueryService service) {
        this.service = service;
    }

    @Operation(
            summary = "면접 평가표 항목 전체 조회",
            description = """
                    - 면접 평가표에 들어 있는 모든 항목을 조회한다.                    
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/findAll")
    public ResponseEntity<CustomApiResponse<List<InterviewSheetItemQueryDTO>>> findAll() {
        List<InterviewSheetItemQueryDTO> dtoList = service.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @Operation(
            summary = "면접 평가표 항목 id로 조회",
            description = """
                    id로 면접 평가표 항목을 조회한다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2530", description = "존재하지 않는 항목입니다.")
    })
    @GetMapping("/findById")
    public ResponseEntity<CustomApiResponse<InterviewSheetItemQueryDTO>> findById(@RequestParam("id") Integer id) {
        InterviewSheetItemQueryDTO dto = service.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }

    @Operation(
            summary = "면접 평가표 항목 평가표id로 조회",
            description = """
                    - 평가표id로 면접 평가표 안에 들어있는 항목을 조회한다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/findBySheetId")
    public ResponseEntity<CustomApiResponse<List<InterviewSheetItemQueryDTO>>> findBySheetId(@RequestParam("sheetId") Integer sheetId) {
        List<InterviewSheetItemQueryDTO> dtoList = service.findBySheetId(sheetId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @Operation(
            summary = "면접 평가표 항목 평가 항목id로 조회",
            description = """
                    - 평가 항목id로 면접 평가표 항목을 조회한다.(항목을 포함하고 있는 모든 평가표를 조회하는 기능)
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/findByCriteriaId")
    public ResponseEntity<CustomApiResponse<List<InterviewSheetItemQueryDTO>>> findByCriteriaId(@RequestParam("criteriaId") Integer criteriaId) {
        List<InterviewSheetItemQueryDTO> dtoList = service.findByCriteriaId(criteriaId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }
}
