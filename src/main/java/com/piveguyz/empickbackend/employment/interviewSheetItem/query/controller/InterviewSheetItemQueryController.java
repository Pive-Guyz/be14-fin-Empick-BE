package com.piveguyz.empickbackend.employment.interviewSheetItem.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewSheetItem.query.dto.InterviewSheetItemQueryDTO;
import com.piveguyz.empickbackend.employment.interviewSheetItem.query.service.InterviewSheetItemQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/employment/interviewSheetItem")
public class InterviewSheetItemQueryController {
    private final InterviewSheetItemQueryService service;

    @Autowired
    public InterviewSheetItemQueryController(InterviewSheetItemQueryService service) {
        this.service = service;
    }

    @GetMapping("/findAll")
    public ResponseEntity<CustomApiResponse<List<InterviewSheetItemQueryDTO>>> findAll() {
        List<InterviewSheetItemQueryDTO> dtoList = service.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @GetMapping("/findById")
    public ResponseEntity<CustomApiResponse<InterviewSheetItemQueryDTO>> findById(@RequestParam("id") Integer id) {
        InterviewSheetItemQueryDTO dto = service.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }

    @GetMapping("/findBySheetId")
    public ResponseEntity<CustomApiResponse<List<InterviewSheetItemQueryDTO>>> findBySheetId(@RequestParam("sheetId") Integer sheetId) {
        List<InterviewSheetItemQueryDTO> dtoList = service.findBySheetId(sheetId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @GetMapping("/findByCriteriaId")
    public ResponseEntity<CustomApiResponse<List<InterviewSheetItemQueryDTO>>> findByCriteriaId(@RequestParam("criteriaId") Integer criteriaId) {
        List<InterviewSheetItemQueryDTO> dtoList = service.findByCriteriaId(criteriaId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }
}
