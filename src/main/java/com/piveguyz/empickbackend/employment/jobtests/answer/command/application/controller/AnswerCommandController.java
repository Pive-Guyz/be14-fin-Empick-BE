package com.piveguyz.empickbackend.employment.jobtests.answer.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.answer.command.application.dto.CreateAnswerCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.answer.command.application.dto.UpdateAnswerCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.answer.command.application.service.AnswerCommandService;
import com.piveguyz.empickbackend.employment.jobtests.answer.command.application.dto.CreateAnswerResponseDTO;
import com.piveguyz.empickbackend.facade.JobtestFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "실무테스트 API", description = "실무테스트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/answers")
public class AnswerCommandController {
    private final AnswerCommandService answerCommandService;
    private final JobtestFacade jobtestFacade;

    @Operation(
            summary = "실무테스트 지원서별 답변 등록",
            description = """
                    실무테스트 지원서별 답변을 등록합니다.
                    - 지원자가 실무테스트 문제를 풀 때 다음 문제로 넘어가면 등록되는 데이터
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<CreateAnswerResponseDTO>> createAnswer(
            @RequestBody @Valid CreateAnswerCommandDTO createAnswerCommandDTO) {
        CreateAnswerResponseDTO newAnswerDTO = answerCommandService.createAnswer(createAnswerCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, newAnswerDTO));
    }

    @Operation(
            summary = "실무테스트 지원서별 답변 수정",
            description = """
                    실무테스트 지원서별 답변을 수정합니다.
                    - 인사팀이 수동을 답안의 정답 여부와 받은 점수를 수정하는 기능(정정하기 기능)
                    정답 여부, 점수 수정 가능합니다.
                    - isCorrect : WRONG, PARTIAL, CORRECT (null 가능)
                    """,
            security = @SecurityRequirement(name = "bearerAuth"),
            hidden = true
    )
    @ApiResponses(value = {
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UpdateAnswerCommandDTO>> updateAnswer(
            @PathVariable int id,
            @RequestBody @Valid UpdateAnswerCommandDTO updateAnswerCommandDTO
    ) {
        UpdateAnswerCommandDTO updateAnswerDTO = answerCommandService.updateAnswerCommandDTO(id, updateAnswerCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, updateAnswerDTO));
    }

    @Operation(
            summary = "실무테스트 지원서별 답변 채점",
            description = """
                    실무테스트 지원서별 답변을 채점합니다.
                    - 시스템이 실무테스트에 해당하는 답안을 채점하고 답변 테이블에 정답 여부와 받은 점수를 기록하는 기능
                    정답 여부, 점수가 자동으로 저장(수정)됩니다.
                    채점된 답안들을 반환합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PatchMapping("/{applicationJobtestId}/grade")
    public ResponseEntity<CustomApiResponse<List<UpdateAnswerCommandDTO>>> gradingJobtest(
            @PathVariable int applicationJobtestId) {
        List<UpdateAnswerCommandDTO> result = jobtestFacade.gradeApplicationJobTest(applicationJobtestId);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, result));
    }


    @Operation(
            summary = "실무테스트 지원서별 답변 삭제",
            description = """
                    실무테스트 지원서별 답변을 삭제합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Integer>> deleteAnswer(
            @PathVariable int id) {
        int deleteId = answerCommandService.deleteAnswer(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, deleteId));
    }
}
