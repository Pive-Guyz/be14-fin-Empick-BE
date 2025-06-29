package com.piveguyz.empickbackend.employment.introduce.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceTemplateQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.service.IntroduceTemplateQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "자기소개서 API", description = "자기소개서 관련 API")
@RestController
@RequestMapping("/api/v1/employment/introduce-template")
@RequiredArgsConstructor
public class IntroduceTemplateQueryController {

    private final IntroduceTemplateQueryService introduceTemplateQueryService;

    @Operation(summary = "자기소개서 템플릿 전체 조회", description = "자기소개서 템플릿을 전체 조회합니다.")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })

    @GetMapping
    public ResponseEntity<CustomApiResponse<List<IntroduceTemplateQueryDTO>>> getAllIntroduceTemplate() {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, introduceTemplateQueryService.
                        findAllIntroduceTemplate()));
    }

    @Operation(summary = "자기소개서 템플릿 항목 전체 조회", description = "자기소개서 템플릿 항목을 전체 조회합니다.")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/item")
    public ResponseEntity<CustomApiResponse<List<IntroduceTemplateQueryDTO>>> getAllIntroduceTemplateItem() {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS,
                        introduceTemplateQueryService.findAllIntroduceTemplateItem()));
    }

    @Operation(summary = "자기소개서 템플릿 id로 조회", description = "자기소개서 템플릿을 id로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "템플릿을 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IntroduceTemplateQueryDTO> getIntroduceTemplateById(@PathVariable int id) {
        IntroduceTemplateQueryDTO template = introduceTemplateQueryService.getIntroduceTemplateById(id);
        if (template == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(template);
    }
}
