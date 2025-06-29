package com.piveguyz.empickbackend.employment.applicant.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.applicant.command.application.dto.ApplicantBookmarkCommandDTO;
import com.piveguyz.empickbackend.employment.applicant.command.application.service.ApplicantBookmarkCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "지원자,지원서 API", description = "지원자 지원서 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/employment/applicant-bookmark")
public class ApplicantBookmarkCommandController {

    private final ApplicantBookmarkCommandService applicantBookmarkCommandService;

    @Operation(summary = "지원자 북마크 등록", description = "지원자를 북마크에 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "북마크 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<ApplicantBookmarkCommandDTO>> addBookmark(
            @RequestBody @Valid ApplicantBookmarkCommandDTO dto) {
        ApplicantBookmarkCommandDTO result = applicantBookmarkCommandService.addBookmark(dto);
        return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS, result));
    }

    @Operation(summary = "지원자 북마크 삭제", description = "지원자를 북마크에서 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/{memberId}/{applicantId}")
    public ResponseEntity<CustomApiResponse<Void>> removeBookmark(
            @PathVariable int memberId,
            @PathVariable int applicantId) {

        ApplicantBookmarkCommandDTO dto = new ApplicantBookmarkCommandDTO(memberId, applicantId);
        applicantBookmarkCommandService.removeBookmark(dto);

        return ResponseEntity
                .status(ResponseCode.SUCCESS.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.SUCCESS));
    }
}
