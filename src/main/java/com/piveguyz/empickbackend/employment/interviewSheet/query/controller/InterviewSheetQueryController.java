package com.piveguyz.empickbackend.employment.interviewSheet.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewSheet.query.dto.InterviewSheetQueryDTO;
import com.piveguyz.empickbackend.employment.interviewSheet.query.service.InterviewSheetQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/employment/interviewSheet")
public class InterviewSheetQueryController {
    private final InterviewSheetQueryService service;

    @Autowired
    public InterviewSheetQueryController(InterviewSheetQueryService service) {
        this.service = service;
    }

    @GetMapping("/findAll")
    public ResponseEntity<CustomApiResponse<List<InterviewSheetQueryDTO>>> findAll() {
        List<InterviewSheetQueryDTO> dtoList = service.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }

    @GetMapping("/findById")
    public ResponseEntity<CustomApiResponse<InterviewSheetQueryDTO>> findById(@RequestParam("id") Integer id) {
        InterviewSheetQueryDTO dto = service.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<CustomApiResponse<List<InterviewSheetQueryDTO>>> searchByName(@RequestParam("name") String name) {
        List<InterviewSheetQueryDTO> dtoList = service.searchByName(name);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dtoList));
    }
}
