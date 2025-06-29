package com.piveguyz.empickbackend.employment.jobtests.grading.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.grading.command.application.dto.CreateGradingCriteriaCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.grading.command.application.dto.UpdateGradingCriteriaCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.grading.command.application.service.GradingCriteriaCommandService;
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
@RequestMapping("/api/v1/employment/grading-criteria")
public class GradingCriteriaCommandController {
    private final GradingCriteriaCommandService gradingCriteriaCommandService;

    public GradingCriteriaCommandController(GradingCriteriaCommandService gradingCriteriaCommandService) {
        this.gradingCriteriaCommandService = gradingCriteriaCommandService;
    }


    // 문제 채점 기준 등록
    @Operation(
            summary = "실무테스트 문제 채점 기준 등록",
            description = """
                    실무테스트 채점 기준을 등록합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {

    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<CreateGradingCriteriaCommandDTO>> createGradingCriteria(
            @RequestBody @Valid CreateGradingCriteriaCommandDTO createGradingCriteriaCommandDTO,
            @RequestBody int questionId) {
        CreateGradingCriteriaCommandDTO newGradingCriteriaDTO =
                gradingCriteriaCommandService.createGradingCriteria(createGradingCriteriaCommandDTO, questionId);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, newGradingCriteriaDTO));
    }


    // 문제 채점 기준 수정
    @Operation(
            summary = "실무테스트 문제 채점 기준 수정",
            description = """
                    실무테스트 채점 기준을 수정합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {

    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UpdateGradingCriteriaCommandDTO>> updateGradingCriteria(
            @PathVariable int id,
            @RequestBody UpdateGradingCriteriaCommandDTO updateGradingCriteriaCommandDTO) {
        UpdateGradingCriteriaCommandDTO updated =
                gradingCriteriaCommandService.updateGradingCriteria(id, updateGradingCriteriaCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, updated));
    }

    // 문제 채점 기준 삭제
    @Operation(
            summary = "실무테스트 문제 채점 기준 삭제",
            description = """
                    실무테스트 채점 기준을 삭제합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Integer>> deleteGradingCriteria(@PathVariable int id) {
        int deleteId = gradingCriteriaCommandService.deleteGradingCriteria(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, deleteId));
    }
}
