package com.piveguyz.empickbackend.employment.introduce.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceTemplateItemResponseCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceTemplateItemResponseCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/employment/introduce-template-item-response")
@RequiredArgsConstructor
@Tag(name = "자기소개서 API", description = "자기소개서 관련 API")
public class IntroduceTemplateItemResponseCommandController {

    private final IntroduceTemplateItemResponseCommandService introduceTemplateItemResponseCommandService;

    @PostMapping
    @Operation(summary = "자기소개서 템플릿 항목별 응답 등록", description = "자기소개서 템플릿 항목별 응답을 등록합니다.")
    public ResponseEntity<CustomApiResponse<IntroduceTemplateItemResponseCommandDTO>>
    createIntroduceTemplateItemResponse(@RequestBody @Valid IntroduceTemplateItemResponseCommandDTO dto) {

        IntroduceTemplateItemResponseCommandDTO created = introduceTemplateItemResponseCommandService.create(dto);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, created));
        }
    }

