package com.piveguyz.empickbackend.employment.introduce.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceStandardItemCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceStandardItemCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "자기소개서 API", description = "자기소개서 관련 API")
@RestController
@RequestMapping("/api/v1/employment/introduce-standard-item")
@RequiredArgsConstructor
public class IntroduceStandardItemCommandController {

    private final IntroduceStandardItemCommandService introduceStandardItemCommandService;

    @Operation(summary = "자기소개서 기준표 항목 등록", description = "자기소개서 기준표 항목을 등록합니다.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<IntroduceStandardItemCommandDTO>> create(
            @RequestBody @Valid IntroduceStandardItemCommandDTO dto) {
        IntroduceStandardItemCommandDTO created = introduceStandardItemCommandService.create(dto);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, created));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "자기소개서 기준표 항목 수정", description = "자기소개서 기준표 항목을 수정합니다.")
    public ResponseEntity<CustomApiResponse<IntroduceStandardItemCommandDTO>> update(
            @PathVariable int id,
            @RequestBody @Valid IntroduceStandardItemCommandDTO dto) {
        IntroduceStandardItemCommandDTO updated = introduceStandardItemCommandService.update(id, dto);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "자기소개서 기준표 항목 삭제", description = "자기소개서 기준표 항목을 삭제합니다.")

    public ResponseEntity<CustomApiResponse<Integer>> delete(@PathVariable int id) {
        int deletedId = introduceStandardItemCommandService.delete(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, deletedId));
    }

}
