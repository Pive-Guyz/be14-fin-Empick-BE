package com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.dto.InterviewSheetItemCommandDTO;
import com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.service.InterviewSheetItemCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employment/interviewSheetItem")
public class InterviewSheetItemCommandController {
    private final InterviewSheetItemCommandService service;

    @Autowired
    public InterviewSheetItemCommandController(InterviewSheetItemCommandService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse<InterviewSheetItemCommandDTO>> create(@RequestBody InterviewSheetItemCommandDTO dto) {
        InterviewSheetItemCommandDTO createdDTO = service.create(dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, createdDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CustomApiResponse<InterviewSheetItemCommandDTO>> delete(@RequestParam("id") Integer id){
        InterviewSheetItemCommandDTO dto = service.delete(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, dto));
    }
}
