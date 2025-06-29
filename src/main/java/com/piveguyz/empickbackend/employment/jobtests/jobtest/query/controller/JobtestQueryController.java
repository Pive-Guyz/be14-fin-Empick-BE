package com.piveguyz.empickbackend.employment.jobtests.jobtest.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.JobtestEntryRequestDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.JobtestExamQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.JobtestQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.JobtestQuestionListQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.service.JobtestQueryService;
import com.piveguyz.empickbackend.facade.JobtestFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "실무테스트 API", description = "실무테스트 관련 API")
@RestController
@RequiredArgsConstructor
    @RequestMapping("/api/v1/employment/jobtests")
public class JobtestQueryController {
    private final JobtestQueryService jobtestQueryService;
    private final JobtestFacade jobtestFacade;

    // 전체 실무테스트 조회
    @Operation(
            summary = "전체 실무테스트 조회",
            description = """
                    전체 실무테스트를 조회합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<JobtestQuestionListQueryDTO>>> getAllApplicationJobTests() {
        List<JobtestQuestionListQueryDTO> result = jobtestQueryService.getAllJobTests();
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, result));
    }

    // 실무테스트 상세 조회
    @Operation(
            summary = "실무테스트 상세 조회",
            description = """
                    id에 해당하는 실무테스트를 조회합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<JobtestQueryDTO>> getJobTestById(@PathVariable int id) {
        JobtestQueryDTO result = jobtestQueryService.findJobTestWithApplications(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, result));
    }

    @Operation(
            summary = "실무테스트 입장 & 시험 정보 반환",
            description = """
                    지원자에게 할당한 입장 코드를 입력하면 실무테스트에 입장할 수 있습니다.
                    입장할 때 시험 정보를 함께 반환해 저장합니다.
                    실무테스트 입장 검증 후 입장 / 시험 정보 반환
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PostMapping("/exam/enter/{jobtestId}")
    public ResponseEntity<CustomApiResponse<JobtestExamQueryDTO>> verifyEntry(
            @PathVariable int jobtestId,
            @RequestBody JobtestEntryRequestDTO request
    ) {
        JobtestExamQueryDTO jobtestExamQueryDTO = jobtestFacade.enterJobtestExam(jobtestId, request);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body((CustomApiResponse.of(ResponseCode.SUCCESS, jobtestExamQueryDTO)));
    }
}
