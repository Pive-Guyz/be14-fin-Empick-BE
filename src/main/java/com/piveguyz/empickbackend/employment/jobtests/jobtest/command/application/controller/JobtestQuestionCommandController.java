package com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.CreateJobtestQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.CreateJobtestQuestionResponseDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.UpdateJobtestQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.service.JobtestQuestionCommandService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@Tag(name = "실무테스트 API", description = "실무테스트 관련 API")
@RestController
@RequestMapping("/api/v1/employment/jobtest-questions")
public class JobtestQuestionCommandController {

    private final JobtestQuestionCommandService jobtestQuestionCommandService;

    public JobtestQuestionCommandController(JobtestQuestionCommandService jobtestQuestionCommandService) {
        this.jobtestQuestionCommandService = jobtestQuestionCommandService;
    }

    @Operation(
            summary = "실무테스트별 문제 등록",
            description = """
                    실무테스트별 문제를 등록합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<CreateJobtestQuestionResponseDTO>> createJobtestQuestion(
            @RequestBody @Valid CreateJobtestQuestionCommandDTO createJobtestQuestionCommandDTO,
            @RequestBody int jobtestId) {
        CreateJobtestQuestionResponseDTO newDTO = jobtestQuestionCommandService.createJobtestQuestion(createJobtestQuestionCommandDTO, jobtestId);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, newDTO));
    }

    // 실무테스트별 문제 수정
    @Operation(
            summary = "실무테스트별 문제 수정",
            description = """
                    실무테스트별 문제를 수정합니다.
                    - 점수, 문제 순서 수정 가능
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UpdateJobtestQuestionCommandDTO>> updateQuestion(
            @PathVariable int id,
            @RequestBody UpdateJobtestQuestionCommandDTO updateJobtestQuestionCommandDTO) {
        UpdateJobtestQuestionCommandDTO updateDTO = jobtestQuestionCommandService.updateJobtestQuestion(id, updateJobtestQuestionCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body((CustomApiResponse.of(ResponseCode.SUCCESS, updateDTO)));
    }

    //  실무 테스트별 문제 삭제
    @Operation(
            summary = "실무테스트별 문제 삭제",
            description = """
                    실무테스트별 문제를 삭제합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Integer>> deleteJobtestQuestion(@PathVariable int id) {
        int deleteId = jobtestQuestionCommandService.deleteJobtestQuestion(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body((CustomApiResponse.of(ResponseCode.SUCCESS, deleteId)));
    }
}
