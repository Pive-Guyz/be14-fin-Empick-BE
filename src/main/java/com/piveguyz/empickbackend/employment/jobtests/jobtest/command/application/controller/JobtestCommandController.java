package com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.CreateJobtestCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.CreateJobtestResponseDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.UpdateJobtestCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.service.JobtestCommandService;
import com.piveguyz.empickbackend.facade.JobtestFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "실무테스트 API", description = "실무테스트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/jobtests")
public class JobtestCommandController {
    private final JobtestCommandService jobtestCommandService;
    private final JobtestFacade jobtestFacade;

    // 실무테스트 등록
    @Operation(
            summary = "실무테스트 등록",
            description = """
                    실무테스트를 등록합니다.
                    - difficulty : EASY / MEDIUM / HARD
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2402", description = "작성자 정보가 유효하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2421", description = "동일한 이름의 실무테스트가 이미 등록되어 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2400", description = "유효하지 않은 난이도입니다."),
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<CreateJobtestResponseDTO>> createJobtest(@RequestBody @Valid CreateJobtestCommandDTO createJobtestCommandDTO) {
        CreateJobtestResponseDTO newJobtestDTO = jobtestFacade.createJobtest(createJobtestCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, newJobtestDTO));
    }

    //     실무테스트 수정
    @Operation(
            summary = "실무테스트 수정",
            description = """
                    실무테스트를 수정합니다.
                    - id에 해당하는 실무테스트의 시험 시간, 난이도 수정 가능
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2402", description = "작성자 정보가 유효하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2420", description = "이 실무테스트는 다른 곳에서 사용중이므로 수정하거나 삭제할 수 없습니다."),
    })
    @PatchMapping("/{jobtestId}")
    public ResponseEntity<CustomApiResponse<UpdateJobtestCommandDTO>> updateJobtest(
            @PathVariable int jobtestId,
            @RequestBody @Valid UpdateJobtestCommandDTO updateJobtestCommandDTO) {
        UpdateJobtestCommandDTO newJobtestDTO = jobtestCommandService.updateJobtest(jobtestId, updateJobtestCommandDTO);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, newJobtestDTO));
    }

    @Operation(
            summary = "실무테스트 삭제",
            description = """
                    id에 해당하는 실무테스트를 삭제합니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2422", description = "요청한 실무테스트를 찾을 수 없습니다."),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Integer>> deleteJobtest(@PathVariable int id) {
        int deletId = jobtestCommandService.deleteJobtest(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, deletId));
    }
}
