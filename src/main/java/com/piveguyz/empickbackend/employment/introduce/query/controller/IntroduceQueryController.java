package com.piveguyz.empickbackend.employment.introduce.query.controller;


import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.service.IntroduceQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "자기소개서 API", description = "자기소개서 관련 API")
@RestController
@RequestMapping("/api/v1/employment/introduce")
@RequiredArgsConstructor
public class IntroduceQueryController {

    private final IntroduceQueryService introduceQueryService;

    @Operation(summary = "자기소개서 전체 조회", description = "자기소개서 전체를 조회합니다.")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
    })

    @GetMapping
    public ResponseEntity<CustomApiResponse<List<IntroduceQueryDTO>>> getAllIntroduce() {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, introduceQueryService.findAllIntroduce()));
    }

    @Operation(summary = "자기소개서 지원서로 조회", description = "자기소개서를 지원서로 조회합니다.")
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<CustomApiResponse<IntroduceQueryDTO>> getIntroduceByApplicationId(
            @PathVariable("applicationId") Integer applicationId) {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS,
                        introduceQueryService.findIntroduceByApplicationId(applicationId)));
    }

    @Operation(summary = "자기소개서 id로 조회", description = "자기소개서를 id로 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<IntroduceQueryDTO>> getIntroduceById(
            @PathVariable("id") Integer id) {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS,
                        introduceQueryService.findIntroduceById(id)));
    }
}
