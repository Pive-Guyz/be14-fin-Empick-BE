package com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.CreateApplicationJobtestCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.JobtestEntryRequestDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.UpdateApplicationJobtestCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.service.ApplicationJobtestCommandService;
import com.piveguyz.empickbackend.facade.JobtestFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "실무테스트 API", description = "실무테스트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/application-jobtests")
public class ApplicationJobtestCommandController {

    private final ApplicationJobtestCommandService applicationJobtestCommandService;

    @Operation(
            summary = "지원서별 실무테스트 등록",
            description = """
                    지원서별 실무테스트를 등록합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<CreateApplicationJobtestCommandDTO>> createApplicationJobtest(
            @RequestBody @Valid CreateApplicationJobtestCommandDTO createApplicationJobtestCommandDTO) {
        CreateApplicationJobtestCommandDTO newDTO = applicationJobtestCommandService.createApplicaionJobtest(createApplicationJobtestCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, newDTO));
    }

    @Operation(
            summary = "지원서별 실무테스트 수정",
            description = """
                    지원서별 실무테스트를 수정합니다.
                    - '평가자 코멘트, 제출일, 채점 점수, 평가 점수, 채점 상태, 평가 상태, 입장코드, 평가자'를 수정할 수 있습니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"),
            hidden = true
    )
    @ApiResponses(value = {
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UpdateApplicationJobtestCommandDTO>> updateApplicationJobtest(
            @PathVariable int id,
            @RequestBody @Valid UpdateApplicationJobtestCommandDTO updateApplicationJobtestCommandDTO) {
        UpdateApplicationJobtestCommandDTO updateDTO = applicationJobtestCommandService.updateApplicationJobtest(id, updateApplicationJobtestCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body((CustomApiResponse.of(ResponseCode.SUCCESS, updateDTO)));
    }

    @Operation(
            summary = "지원서별 실무테스트 삭제",
            description = """
                    지원서별 실무테스트를 삭제합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Integer>> deleteApplicationJobtest(@PathVariable int id) {
        int deleteId = applicationJobtestCommandService.deleteApplicationJobtest(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body((CustomApiResponse.of(ResponseCode.SUCCESS, deleteId)));
    }
}
