package com.piveguyz.empickbackend.employment.interviewSheet.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewSheet.command.application.dto.InterviewSheetCommandDTO;
import com.piveguyz.empickbackend.employment.interviewSheet.command.application.service.InterviewSheetCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employment/interviewSheet")
public class InterviewSheetCommandController {
    private final InterviewSheetCommandService service;

    @Autowired
    public InterviewSheetCommandController(InterviewSheetCommandService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse<InterviewSheetCommandDTO>> createSheet(@RequestBody InterviewSheetCommandDTO dto) {
        InterviewSheetCommandDTO createdDTO = service.createSheet(dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, createdDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<CustomApiResponse<InterviewSheetCommandDTO>> updateSheet(@RequestParam("id") Integer id,
                                                                                   @RequestBody InterviewSheetCommandDTO dto) {
        InterviewSheetCommandDTO updatedDTO = service.updateSheet(id, dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, updatedDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CustomApiResponse<InterviewSheetCommandDTO>> deleteSheet(@RequestParam("id") Integer id) {
        InterviewSheetCommandDTO deletedDTO = service.deleteSheet(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, deletedDTO));
    }
}
