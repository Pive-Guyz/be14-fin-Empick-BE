package com.piveguyz.empickbackend.employment.applicant.command.application.controller;


import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicant.command.application.dto.ApplicationCommandDTO;
import com.piveguyz.empickbackend.employment.applicant.command.application.service.ApplicationCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "지원자,지원서 API", description = "지원자,지원서 관련 API")
@RestController
@RequestMapping("/api/v1/employment/application")
@RequiredArgsConstructor
public class ApplicationCommandController {

    private final ApplicationCommandService applicationCommandService;

    @Operation(
            summary = "지원서 등록",
            description = """
                    지원서를 등록합니다.
                    동일한 공고에는 한 번만 지원할 수 있습니다.
                    status는 기본값 '서류검토대기중'으로 설정됩니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "지원서 등록 성공"),
            @ApiResponse(responseCode = "400", description = "바보"),//""요청 값 오류"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<ApplicationCommandDTO>> registerApplication(
            @RequestBody @Valid ApplicationCommandDTO dto) {

        ApplicationCommandDTO response = applicationCommandService.register(dto);
        return ResponseEntity
                .status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, response));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "지원서 수정", description = "지원서를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "지원서 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<CustomApiResponse<ApplicationCommandDTO>> updateApplicationStatus(
            @PathVariable Integer id,
            @RequestBody ApplicationCommandDTO dto){

        dto.setId(id);
        ApplicationCommandDTO updated = applicationCommandService.updated(dto);
        return ResponseEntity
                .status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "지원서 삭제", description = "지원서를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지원서 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "지원서 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<CustomApiResponse<Integer>> deleteApplication(@PathVariable int id) {
        int deletedId = applicationCommandService.deleted(id);

        return ResponseEntity
                .status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, deletedId));
    }
}