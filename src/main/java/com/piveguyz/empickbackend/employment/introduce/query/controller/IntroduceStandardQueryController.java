package com.piveguyz.empickbackend.employment.introduce.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicantQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceStandard;
import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceStandardItemQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceStandardQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.service.IntroduceStandardQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "자기소개서 기준표", description = "자기소개ㅐ서 기준표")
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/introduce-standard")
@RestController
public class IntroduceStandardQueryController {

    private final IntroduceStandardQueryService introduceStandardQueryService;

    @Operation(summary = "지원자 전체 조회", description = "지원자 목록 전체를 조회합니다.")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
    })

    @GetMapping
    public ResponseEntity<CustomApiResponse<List<IntroduceStandardQueryDTO>>> getAllIntroduceStandard() {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, introduceStandardQueryService.findAllIntroduceStandard()));
    }

    @Operation(summary = "자기소개서 기준 항목 전체 조회", description = "기준 항목 데이터를 전체 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
    })
    @GetMapping("/item")
    public ResponseEntity<CustomApiResponse<List<IntroduceStandardItemQueryDTO>>> getAllIntroduceStandardItem() {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS,
                        introduceStandardQueryService.findAllIntroduceStandardItem()));
    }
}

