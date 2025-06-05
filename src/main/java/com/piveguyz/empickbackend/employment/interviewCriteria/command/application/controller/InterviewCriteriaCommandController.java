package com.piveguyz.empickbackend.employment.interviewCriteria.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.interviewCriteria.command.application.dto.InterviewCriteriaCommandDTO;
import com.piveguyz.empickbackend.employment.interviewCriteria.command.application.service.InterviewCriteriaCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employment/interviewCriteria")
public class InterviewCriteriaCommandController {
    private final InterviewCriteriaCommandService interviewCriteriaCommandService;

    @Autowired
    public InterviewCriteriaCommandController(InterviewCriteriaCommandService interviewCriteriaCommandService) {
        this.interviewCriteriaCommandService = interviewCriteriaCommandService;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse<InterviewCriteriaCommandDTO>> createCriteria(@RequestBody InterviewCriteriaCommandDTO dto) {
        InterviewCriteriaCommandDTO createdDTO = interviewCriteriaCommandService.createCriteria(dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, createdDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<CustomApiResponse<InterviewCriteriaCommandDTO>> updateCriteria(@RequestParam("id") Integer id,
                                                                                         @RequestBody InterviewCriteriaCommandDTO dto) {
        InterviewCriteriaCommandDTO updatedDTO = interviewCriteriaCommandService.updateCriteria(id, dto);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, updatedDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CustomApiResponse<InterviewCriteriaCommandDTO>> deleteCriteria(@RequestParam("id") Integer id) {
        InterviewCriteriaCommandDTO deletedDTO = interviewCriteriaCommandService.deleteCriteria(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, deletedDTO));
    }
}
