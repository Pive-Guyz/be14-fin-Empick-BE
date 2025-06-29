package com.piveguyz.empickbackend.employment.jobtests.answer.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.answer.command.application.dto.CreateGradingResultCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.answer.command.application.dto.UpdateGradingResultCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.answer.command.application.service.GradingResultCommandService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "실무테스트 API", description = "실무테스트 관련 API")
@RestController
@RequestMapping("/api/v1/employment/grading-results")
public class GradingResultCommandController {
    private final GradingResultCommandService gradingResultCommandService;

    public GradingResultCommandController(GradingResultCommandService gradingResultCommandService) {
        this.gradingResultCommandService = gradingResultCommandService;
    }

    @Operation(
            summary = "실무테스트 답안 채점 결과 등록",
            description = """
                    실무테스트 답안 채점 결과를 등록합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<CreateGradingResultCommandDTO>> createGradingResult(
            @RequestBody CreateGradingResultCommandDTO createGradingResultCommandDTO) {
        CreateGradingResultCommandDTO newResultDTO = gradingResultCommandService.createGradingResult(createGradingResultCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, newResultDTO));
    }

    @Operation(
            summary = "실무테스트 답안 채점 결과 수정",
            description = """
                    실무테스트 답안 채점 결과를 수정합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UpdateGradingResultCommandDTO>> updateGradingResult(
            @PathVariable int id,
            @RequestBody UpdateGradingResultCommandDTO updateGradingResultCommandDTO) {
        UpdateGradingResultCommandDTO updated = gradingResultCommandService.updateGradingResult(id, updateGradingResultCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, updated));
    }

    @Operation(
            summary = "실무테스트 답안 채점 결과 삭제",
            description = """
                    실무 테스트 답안 채점 결과를 삭제합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Integer>> deleteGradingResult(
            @PathVariable int id) {
        int deleteId = gradingResultCommandService.deleteGradingResult(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, deleteId));
    }
}
