package com.piveguyz.empickbackend.employment.jobtests.evaluation.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.evaluation.command.application.dto.CreateEvaluationCriteriaCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.evaluation.command.application.dto.UpdateEvaluationCriteriaCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.evaluation.command.application.service.EvaluationCriteriaCommandService;
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
@RequestMapping("/api/v1/employment/evaluation-criteria")
public class EvaluationCriteriaCommandController {
    private final EvaluationCriteriaCommandService evaluationCriteriaCommandService;

    public EvaluationCriteriaCommandController(EvaluationCriteriaCommandService evaluationCriteriaCommandService) {
        this.evaluationCriteriaCommandService = evaluationCriteriaCommandService;
    }

    // 실무 테스트 평가 기준 등록
    @Operation(
            summary = "실무 테스트 평가 기준 등록",
            description = """
                    실무 테스트 평가 기준를 등록합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {

    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<CreateEvaluationCriteriaCommandDTO>> createEvaluationCriteria(@RequestBody @Valid CreateEvaluationCriteriaCommandDTO createEvaluationCriteriaCommandDTO) {
        CreateEvaluationCriteriaCommandDTO newDTO = evaluationCriteriaCommandService.createEvaluationCriteria(createEvaluationCriteriaCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, newDTO));
    }


    // 실무 테스트 평가 기준 수정
    @Operation(
            summary = "실무테스트 평가 기준 수정",
            description = """
                    실무테스트 평가 기준을 수정합니다.
                    - content, detail_content, score_weight 을 수정할 수 있습니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {

    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UpdateEvaluationCriteriaCommandDTO>> updateEvaluationCriteria(
            @PathVariable int id,
            @RequestBody @Valid UpdateEvaluationCriteriaCommandDTO updateEvaluationCriteriaCommandDTO) {
        UpdateEvaluationCriteriaCommandDTO updateEvaluationCriteriaDTO = evaluationCriteriaCommandService.updateEvaluationCriteriaCommandDTO(id, updateEvaluationCriteriaCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, updateEvaluationCriteriaDTO));
    }


//     실무 테스트 평가 기준 삭제
    @Operation(
            summary = "실무테스트 평가 기준 삭제",
            description = """
                    실무 테스트 평가 기준을 삭제합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Integer>> deleteEvaluationCriteria(@PathVariable int id) {
        int deleteId = evaluationCriteriaCommandService.deleteEvaluationCriteria(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, deleteId));
    }


}
