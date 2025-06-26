package com.piveguyz.empickbackend.employment.introduce.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceTemplateItemCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceTemplateItemCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "자기소개서 API", description = "자기소개서 관련 API")
@RestController
@RequestMapping("/api/v1/employment/introduce-template-item")
@RequiredArgsConstructor
public class IntroduceTemplateItemCommandController {

    private final IntroduceTemplateItemCommandService introduceTemplateItemCommandService;

    @PostMapping
    @Operation(summary = "자기소개서 템플릿 항목 등록", description = "자기소개서 템플릿 항목을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<CustomApiResponse<
            IntroduceTemplateItemCommandDTO>> create(@RequestBody IntroduceTemplateItemCommandDTO dto) {
        IntroduceTemplateItemCommandDTO created = introduceTemplateItemCommandService.create(dto);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, created));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "자기소개서 템플릿 항목 삭제", description = "자기소개서 템플릿 항목을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "템플릿 항목을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<CustomApiResponse<Integer>> delete(@PathVariable int id) {
        int deletedId = introduceTemplateItemCommandService.delete(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, deletedId));
    }

}