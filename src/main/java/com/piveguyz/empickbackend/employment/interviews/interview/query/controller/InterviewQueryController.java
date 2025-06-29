package com.piveguyz.empickbackend.employment.interviews.interview.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviews.interview.query.dto.InterviewQueryDTO;
import com.piveguyz.empickbackend.employment.interviews.interview.query.service.InterviewQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name="면접 API", description="면접 관리")
@RestController
@RequestMapping("/api/v1/employment/interview")
public class InterviewQueryController {
    private final InterviewQueryService service;

    @Autowired
    public InterviewQueryController(InterviewQueryService service) {
        this.service = service;
    }

    @Operation(
            summary = "면접 전체 조회",
            description = """
                    - 면접 일정을 모두 조회합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<InterviewQueryDTO>>> findAll() {
        List<InterviewQueryDTO> dtoList = service.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @Operation(
            summary = "면접 id로 조회",
            description = """
                    - 면접 일정을 id로 조회합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2540", description = "존재하지 않습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<InterviewQueryDTO>> findById(@PathVariable("id") Integer id){
        InterviewQueryDTO dto = service.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }

    @Operation(
            summary = "지원서 id로 면접 조회",
            description = """
                    - 면접 일정을 지원서id로 조회합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<CustomApiResponse<InterviewQueryDTO>> findByApplicationId(@PathVariable("applicationId") Integer applicationId){
        InterviewQueryDTO dto = service.findByApplicationId(applicationId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }

    @Operation(
            summary = "면접 날짜로 조회",
            description = """
                    - 면접 일정을 날짜별로 조회합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/date")
    public ResponseEntity<CustomApiResponse<List<InterviewQueryDTO>>> findByDate(@RequestParam("date")
                                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                 LocalDate date) {
        List<InterviewQueryDTO> dtoList = service.findByDate(date);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @Operation(
            summary = "해당 면접 시간 가능 여부 확인",
            description = """
                    - 입력한 면접 시간이 가능한지를 확인합니다.
                    - 입력 면접 시간 ±30분 이내에 다른 일정이 있으면 false를 반환합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/checkAvailable")
    public ResponseEntity<CustomApiResponse<Boolean>> checkAvailable(@RequestParam("datetime")
                                                                         @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
                                                                         LocalDateTime datetime) {
        Boolean availability = service.checkAvailable(datetime);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, availability));
    }

}
