package com.piveguyz.empickbackend.employment.introduce.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceStandardCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceStandardCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employment/introduce-standard")
@RequiredArgsConstructor
@Tag(name = "자기소개서 API", description = "자기소개서 관련 API")
public class IntroduceStandardCommandController {

    private final IntroduceStandardCommandService introduceStandardCommandService;

    @PostMapping
    @Operation(summary = "자기소개서 기준표 등록", description = "자기소개서 기준표을 등록합니다.")
    public ResponseEntity<CustomApiResponse<
            IntroduceStandardCommandDTO>> create(@RequestBody IntroduceStandardCommandDTO dto) {
        IntroduceStandardCommandDTO created = introduceStandardCommandService.create(dto);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, created));
    }

    @Operation(summary = "자기소개서 기준표 삭제", description = "자기소개서 기준표를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Integer>> delete(@PathVariable int id) {
        int deletedId = introduceStandardCommandService.delete(id);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, deletedId));
    }
}
