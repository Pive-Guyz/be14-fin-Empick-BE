package com.piveguyz.empickbackend.employment.interviewCriteria.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewCriteria.query.dto.InterviewCriteriaQueryDTO;
import com.piveguyz.empickbackend.employment.interviewCriteria.query.service.InterviewCriteriaQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/employment/interviewCriteria")
public class InterviewCriteriaQueryController {
    private final InterviewCriteriaQueryService interviewCriteriaQueryService;

    @Autowired
    public InterviewCriteriaQueryController(InterviewCriteriaQueryService interviewCriteriaQueryService) {
        this.interviewCriteriaQueryService = interviewCriteriaQueryService;
    }

//    @Operation(
//            summary = "안내 메일 조회",
//            description = """
//    - 안내 메일을 조회합니다.
//    """
//    )
//    @ApiResponses(value = {
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2630", description = "요청한 메일을 찾을 수 없습니다.")
//    })

    @GetMapping("/findAll")
    public ResponseEntity<CustomApiResponse<List<InterviewCriteriaQueryDTO>>> findAll() {
        List<InterviewCriteriaQueryDTO> interviewCriteriaQueryDTOList = interviewCriteriaQueryService.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, interviewCriteriaQueryDTOList));
    }

    @GetMapping("/findById")
    public ResponseEntity<CustomApiResponse<InterviewCriteriaQueryDTO>> findById(@RequestParam("id") Integer id) {
        InterviewCriteriaQueryDTO interviewCriteriaQueryDTO = interviewCriteriaQueryService.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, interviewCriteriaQueryDTO));
    }

    @GetMapping("/searchByContent")
    public ResponseEntity<CustomApiResponse<List<InterviewCriteriaQueryDTO>>> searchByContent(@RequestParam("content") String content) {
        List<InterviewCriteriaQueryDTO> interviewCriteriaQueryDTOList = interviewCriteriaQueryService.searchByContent(content);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, interviewCriteriaQueryDTOList));
    }
}