package com.piveguyz.empickbackend.employment.jobtests.evaluation.command.application.controller;


import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.evaluation.command.application.dto.CreateEvaluationResultCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.evaluation.command.application.dto.UpdateEvaluationResultCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.evaluation.command.application.service.EvaluationResultCommandService;
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
@RequestMapping("/api/v1/employment/evaluation-results")
public class EvaluationResultCommandController {
    private final EvaluationResultCommandService evaluationResultCommandService;

    public EvaluationResultCommandController(EvaluationResultCommandService evaluationResultCommandService) {
        this.evaluationResultCommandService = evaluationResultCommandService;
    }
    
//     실무테스트 평가 결과 등록
    @Operation(
            summary = "실무 테스트 평가 결과 등록",
            description = """
                    실무 테스트 평가 결과를 등록합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<CreateEvaluationResultCommandDTO>> createEvaluationResult(
            @RequestBody @Valid CreateEvaluationResultCommandDTO createEvaluationResultCommandDTO) {
        CreateEvaluationResultCommandDTO newDTO = evaluationResultCommandService.createEvaluationResult(createEvaluationResultCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, newDTO));
    }
    
    // 실무테스트 평가 결과 수정
    @Operation(
            summary = "실무테스트 평가 결과 수정",
            description = """
                    실무테스트 평가 결과를 수정합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UpdateEvaluationResultCommandDTO>> updateEvaluationResult(
            @PathVariable int id,
            @RequestBody @Valid UpdateEvaluationResultCommandDTO updateEvaluationResultCommandDTO) {
        UpdateEvaluationResultCommandDTO updated = evaluationResultCommandService.updateEvaluationResult(id, updateEvaluationResultCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, updated));
    }
    
    
    // 실무테스트 평가 결과 삭제
    @Operation(
            summary = "실무테스트 평가 결과 삭제",
            description = """
                    실무 테스트 평가 결과를 삭제합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Integer>> deleteEvaluationResult(@PathVariable int id) {
        int deleteId = evaluationResultCommandService.deleteEvaluationResult(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, deleteId));
    }
    
}
