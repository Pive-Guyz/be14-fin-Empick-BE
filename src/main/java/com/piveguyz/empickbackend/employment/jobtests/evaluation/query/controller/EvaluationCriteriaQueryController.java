package com.piveguyz.empickbackend.employment.jobtests.evaluation.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.evaluation.query.dto.EvaluationCriteriaQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.evaluation.query.service.EvaluationCriteriaQueryService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Hidden
@Tag(name = "실무테스트 API", description = "실무테스트 관련 API")
@RestController
@RequestMapping("/api/v1/employment")
public class EvaluationCriteriaQueryController {
    private final EvaluationCriteriaQueryService evaluationCriteriaQueryService;

    EvaluationCriteriaQueryController(EvaluationCriteriaQueryService evaluationCriteriaQueryService) {
        this.evaluationCriteriaQueryService = evaluationCriteriaQueryService;
    }

    @Operation(
            summary = "실무테스트에 대한 평가 기준 조회",
            description = """
                    특정 실무테스트에 관한 평가 기준을 조회합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @GetMapping("/jobtests/{jobtestId}/evaluation-criteria")
    public ResponseEntity<CustomApiResponse<List<EvaluationCriteriaQueryDTO>>> getEvaluationCriteriaByJobtestId(@PathVariable int jobtestId) {
        List<EvaluationCriteriaQueryDTO> evaluationCriteriaList = evaluationCriteriaQueryService.getEvaluationCriteriaByJobtestId(jobtestId);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, evaluationCriteriaList));
    }

    @Operation(
            summary = "평가 기준 상세 조회",
            description = """
                    특정 평가 기준을 조회합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @GetMapping("/evaluation-criteria/{id}")
    public ResponseEntity<CustomApiResponse<EvaluationCriteriaQueryDTO>> getEvaluationCriteriaById(@PathVariable int id) {
        EvaluationCriteriaQueryDTO evaluationCriteriaQueryDTO = evaluationCriteriaQueryService.getEvaluationCriteriaById(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, evaluationCriteriaQueryDTO));
    }
}
