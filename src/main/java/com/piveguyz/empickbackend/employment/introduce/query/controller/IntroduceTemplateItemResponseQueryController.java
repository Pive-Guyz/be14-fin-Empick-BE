package com.piveguyz.empickbackend.employment.introduce.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceTemplateItemResponseQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.service.IntroduceTemplateItemResponseQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "자기소개서 API", description = "자기소개서 관련 API")
@RestController
@RequestMapping("/api/v1/employment/introduce-template-item-response")
@RequiredArgsConstructor
public class IntroduceTemplateItemResponseQueryController {

    private final IntroduceTemplateItemResponseQueryService introduceTemplateItemResponseQueryService;

    @Operation(summary = "자기소개서 템플릿 항목별 응답 전체 조회",
            description = "자기소개서 템플릿 항목별 응답을 전체 조회합니다.")

    @GetMapping
    public ResponseEntity<CustomApiResponse<List<IntroduceTemplateItemResponseQueryDTO>>>
    getAllIntroduceTemplateItemResponse() {
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS,introduceTemplateItemResponseQueryService.
                        findAllIntroduceTemplateItemResponse()));
    }
}
