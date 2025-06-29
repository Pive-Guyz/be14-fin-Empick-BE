package com.piveguyz.empickbackend.employment.jobtests.question.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.CreateQuestionOptionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.CreateQuestionOptionResponse;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.UpdateQuestionOptionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.service.QuestionOptionCommandService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@Tag(name = "실무테스트 API", description = "실무테스트 관련 API")
@RestController
@RequestMapping("/api/v1/employment/question-options")
public class QuestionOptionCommandController {
    private final QuestionOptionCommandService questionOptionCommandService;

    public QuestionOptionCommandController(QuestionOptionCommandService questionOptionCommandService) {
        this.questionOptionCommandService = questionOptionCommandService;
    }


    @Operation(
            summary = "실무테스트 문제 선택지 등록",
            description = """
                    실무테스트 선택지를 등록합니다.
                    실무테스트 당 최대 5개까지 가능합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2416", description = "선택지는 최대 5개까지만 등록할 수 있습니다."),
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<CreateQuestionOptionResponse>> createQuestion(@RequestBody @Valid CreateQuestionOptionCommandDTO createQuestionOptionCommandDTO, @RequestBody int questionId) {
        CreateQuestionOptionResponse newOptionDTO = questionOptionCommandService.createQuestionOption(createQuestionOptionCommandDTO,
                questionId);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, newOptionDTO));
    }

    @Operation(
            summary = "실무테스트 문제 선택지 수정",
            description = """
                    실무테스트 선택지를 수정합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2417", description = "선택지를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "2418", description = "선택지는 5번을 초과할 수 없습니다."),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UpdateQuestionOptionCommandDTO>> updateOption(
            @PathVariable int id,
            @RequestBody UpdateQuestionOptionCommandDTO updateQuestionOptionCommandDTO) {
        UpdateQuestionOptionCommandDTO updated = questionOptionCommandService.updateQuestionOption(id, updateQuestionOptionCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, updated));
    }

    @Operation(
            summary = "실무테스트 문제 선택지 삭제",
            description = """
                    실무테스트 선택지를 삭제합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2417", description = "선택지를 찾을 수 없습니다."),
    })
    @DeleteMapping("/{questionId}")
    public ResponseEntity<CustomApiResponse<String>> deleteOption(
            @PathVariable int questionId) {
        questionOptionCommandService.deleteQuestionOption(questionId);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, ResponseCode.SUCCESS.getMessage()));
    }
}
