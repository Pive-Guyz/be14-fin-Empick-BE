package com.piveguyz.empickbackend.employment.jobtests.question.query.controller;


import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.question.query.dto.QuestionListQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.query.dto.QuestionQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.query.service.QuestionQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "실무 테스트 문제 API", description = "실무테스트 문제 조회 API")
@RestController
@RequestMapping("/api/v1/employment/jobtest/question")
public class QuestionQueryController {

    private final QuestionQueryService questionQueryService;

    QuestionQueryController(QuestionQueryService questionQueryService) {
        this.questionQueryService = questionQueryService;
    }

    // 전체 실무 테스트 문제 조회
    @Operation(
            summary = "전체 실무 테스트 문제 조회",
            description = """
                     전체 실무 테스트 문제를 조회합니다.
                    """
    )
    @GetMapping
    public ResponseEntity<List<QuestionListQueryDTO>> getAllQuestions() {
        List<QuestionListQueryDTO> questionList = questionQueryService.getAllQuestions();
        return ResponseEntity.ok(questionList);
    }

    @Operation(
            summary = "실무 테스트 문제 상세 조회",
            description = """
                     id에 해당하는 실무 테스트 문제의 정보를 조회합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2414", description = "요청한 문제를 찾을 수 없습니다."),
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<QuestionQueryDTO>> getQuestionById(@PathVariable int id) {
        QuestionQueryDTO questionDTO = questionQueryService.getQuestionById(id);
        return ResponseEntity.ok(CustomApiResponse.of(ResponseCode.SUCCESS, questionDTO));
    }
}
