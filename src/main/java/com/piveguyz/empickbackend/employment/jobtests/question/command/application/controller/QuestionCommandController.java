package com.piveguyz.empickbackend.employment.jobtests.question.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.CreateQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.DeleteQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.UpdateQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.service.QuestionCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "실무 테스트 문제 API", description = "실무테스트 문제 등록, 수정, 삭제 API")
@RestController
@RequestMapping("/api/v1/employment/jobtest/question")
public class QuestionCommandController {
    private final QuestionCommandService questionCommandService;

    public QuestionCommandController(QuestionCommandService questionCommandService) {
        this.questionCommandService = questionCommandService;
    }

    // 실무 테스트 문제 등록
    @Operation(
            summary = "실무테스트 문제 등록",
            description = """
                     실무 테스트 문제를 등록합니다.
                    - type : MULTIPLE / SUBJECTIVE / DESCRIPTIVE,
                    - difficulty : EASY / MEDIUM / HARD
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2400", description = "유효하지 않은 난이도입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2401", description = "유효하지 않은 실무 테스트 유형입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2411", description = "동일한 문제가 이미 등록되어 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2412", description = "작성자 정보가 유효하지 않습니다.")
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<CreateQuestionCommandDTO>> createQuestion(@RequestBody @Valid CreateQuestionCommandDTO createQuestionCommandDTO) {
        CreateQuestionCommandDTO newQuestionDTO = questionCommandService.createQuestion(createQuestionCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, newQuestionDTO));
    }

    // 실무 테스트 문제 수정
    @Operation(
            summary = "실무테스트 문제 수정",
            description = """
                     실무 테스트 문제를 수정합니다.
                     - updatedMemberId는 필수로 있어야 함
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2414", description = "요청한 문제를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2413", description = "수정자 정보가 유효하지 않습니다."),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UpdateQuestionCommandDTO>> updateQuestion(
            @PathVariable int id,
            @RequestBody UpdateQuestionCommandDTO updateQuestionCommandDTO) {
        UpdateQuestionCommandDTO updatedQuestionDTO = questionCommandService.updateQuestion(id, updateQuestionCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body((CustomApiResponse.of(ResponseCode.SUCCESS, updatedQuestionDTO)));
    }

    // 실무 테스트 문제 삭제
    @Operation(
            summary = "실무테스트 문제 삭제",
            description = """
                     실무 테스트 문제를 삭제합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2414", description = "요청한 문제를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2415", description = "이 문제는 다른 곳에서 사용 중이므로 삭제할 수 없습니다."),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<DeleteQuestionCommandDTO>> deleteQuestion(@PathVariable int id) {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, questionCommandService.deleteQuestion(id)));
    }
}
