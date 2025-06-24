package com.piveguyz.empickbackend.employment.jobtests.jobtest.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestRequestDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestResponseDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.service.ApplicationJobtestQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "실무테스트 API", description = "실무테스트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/application-jobtests")
public class ApplicationJobtestQueryController {

    private final ApplicationJobtestQueryService applicationJobtestQueryService;

    // 지원서별 실무테스트 전체 목록 조회
    @Operation(
            summary = "지원서별 실무테스트 전체 목록 조회",
            description = """
                    지원서별 실무테스트 전체 목록을 조회합니다.
                    """
    )
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<ApplicationJobtestQueryDTO>>> getApplicationJobtestList() {
        List<ApplicationJobtestQueryDTO> applicationJobtestList = applicationJobtestQueryService.getAllApplicationJobTests();
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, applicationJobtestList));
    }

    // 특정 지원서에게 할당된 실무테스트 조회
    @Operation(
            summary = "특정 지원서에게 할당된 실무테스트 조회",
            description = """
                    특정 지원서에게 할당된 실무테스트를 조회합니다.
                    """
    )
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<CustomApiResponse<ApplicationJobtestResponseDTO>> getApplicationJobtest(
            @PathVariable int applicationId
    ) {
        ApplicationJobtestResponseDTO dto = applicationJobtestQueryService.getApplicationJobtestByApplicationId(applicationId);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, dto));
    }

}
