package com.piveguyz.empickbackend.employment.introduce.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceTemplateCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceTemplateCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "자기소개서 API", description = "자기소개서 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/introduce-template")
public class IntroduceTemplateCommandController {

    private final IntroduceTemplateCommandService introduceTemplateCommandService;

    @Operation(summary = "자기소개서 템플릿 등록", description = "자기소개서 템플릿을 등록합니다.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<IntroduceTemplateCommandDTO>> create(
            @RequestBody @Valid IntroduceTemplateCommandDTO dto) {
        IntroduceTemplateCommandDTO created = introduceTemplateCommandService.create(dto);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, created));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "자기소개서 템플릿 삭제", description = "자기소개서 템플릿을 삭제합니다.")

    public ResponseEntity<CustomApiResponse<Integer>> delete(@PathVariable int id) {
        int deletedId = introduceTemplateCommandService.delete(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, deletedId));
    }
}
