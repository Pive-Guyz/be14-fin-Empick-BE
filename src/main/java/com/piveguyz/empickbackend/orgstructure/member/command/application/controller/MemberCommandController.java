package com.piveguyz.empickbackend.orgstructure.member.command.application.controller;

import com.piveguyz.empickbackend.common.constants.ApiExamples;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.infra.s3.dto.S3UploadResponseDTO;
import com.piveguyz.empickbackend.orgstructure.facade.MemberProfileFacade;
import com.piveguyz.empickbackend.orgstructure.member.command.application.dto.MemberProfileUploadRequestDTO;
import com.piveguyz.empickbackend.orgstructure.member.command.application.dto.MemberSignUpRequestDTO;
import com.piveguyz.empickbackend.orgstructure.member.command.application.dto.MemberSignUpResponseDTO;
import com.piveguyz.empickbackend.orgstructure.member.command.application.service.MemberCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/members")
@Tag(name = "사원 API", description = "사원 등록 및 관리 API")
@RequiredArgsConstructor
public class MemberCommandController {

    private final MemberCommandService memberCommandService;
    private final MemberProfileFacade memberProfileFacade;

    @PostMapping(value = "/registrations", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "사원 등록", description = "새로운 사원을 등록합니다. (프로필 이미지 포함)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "사원 등록 성공",
                    content = @Content(schema = @Schema(implementation = MemberSignUpResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "이메일 중복 오류", content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_409_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패 또는 프로필 이미지 누락", content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_400_EXAMPLE))),
    })
    public ResponseEntity<?> signup(
            @Parameter(description = "사원명 (필수)", example = "김철수")
            @RequestParam("name") @NotBlank String name,

            @Parameter(description = "전화번호 (필수)", example = "010-8412-6774")
            @RequestParam("phone") @NotBlank String phone,

            @Parameter(description = "이메일 (필수)", example = "user1@company.com")
            @RequestParam("email") @NotBlank String email,

            @Parameter(description = "주소 (필수)", example = "서울시 강남구 테헤란로 5길")
            @RequestParam("address") @NotBlank String address,

            @Parameter(description = "생년월일", example = "1993-08-26")
            @RequestParam(value = "birth", required = false) String birth,

            @Parameter(description = "비밀번호")
            @RequestParam(value = "password", required = false) String password,

            @Parameter(description = "부서 ID", example = "1")
            @RequestParam(value = "departmentId", required = false) Integer departmentId,

            @Parameter(description = "직급 ID", example = "1")
            @RequestParam(value = "positionId", required = false) Integer positionId,

            @Parameter(description = "직무 ID", example = "1")
            @RequestParam(value = "jobId", required = false) Integer jobId,

            @Parameter(description = "계급 ID", example = "1")
            @RequestParam(value = "rankId", required = false) Integer rankId,

            @Parameter(description = "상태 (1: 활성, 0: 비활성)", example = "1")
            @RequestParam(value = "status", defaultValue = "1") @NotNull Integer status,

            @Parameter(description = "입사일", example = "2022-10-10T00:00:00")
            @RequestParam(value = "hireAt", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hireAt,

            @Parameter(description = "퇴사일", example = "2025-06-26T18:09:05")
            @RequestParam(value = "resignAt", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime resignAt,

            @Parameter(description = "마지막 로그인 시간", example = "2025-06-26T18:09:05")
            @RequestParam(value = "lastLoginAt", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastLoginAt,

            @Parameter(description = "휴가 일수", example = "8")
            @RequestParam(value = "vacationCount", defaultValue = "0") Integer vacationCount,

            @Parameter(description = "삭제한 사원 ID", example = "0")
            @RequestParam(value = "deletedMemberId", required = false) Integer deletedMemberId,

            @Parameter(description = "수정한 사원 ID", example = "0")
            @RequestParam(value = "updatedMemberId", required = false) Integer updatedMemberId,

            @Parameter(description = "프로필 이미지 파일 (필수)",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart("profileImage") @NotNull MultipartFile profileImage) {

        // 유효성 검사
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(CustomApiResponse.of(ResponseCode.VALIDATION_FAIL, "이름은 필수입니다."));
        }
        if (phone == null || phone.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(CustomApiResponse.of(ResponseCode.VALIDATION_FAIL, "전화번호는 필수입니다."));
        }
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(CustomApiResponse.of(ResponseCode.VALIDATION_FAIL, "이메일은 필수입니다."));
        }
        if (address == null || address.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(CustomApiResponse.of(ResponseCode.VALIDATION_FAIL, "주소는 필수입니다."));
        }
        if (profileImage == null || profileImage.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(CustomApiResponse.of(ResponseCode.VALIDATION_FAIL, "프로필 이미지는 필수입니다."));
        }

        // DTO 객체 생성 및 설정
        MemberSignUpRequestDTO request = new MemberSignUpRequestDTO();
        request.setName(name);
        request.setPhone(phone);
        request.setEmail(email);
        request.setAddress(address);
        request.setBirth(birth);
        request.setPassword(password);
        request.setDepartmentId(departmentId);
        request.setPositionId(positionId);
        request.setJobId(jobId);
        request.setRankId(rankId);
        request.setStatus(status);
        request.setVacationCount(vacationCount);
        request.setHireAt(hireAt);
        request.setResignAt(resignAt);
        request.setLastLoginAt(lastLoginAt);
        request.setDeletedMemberId(deletedMemberId);
        request.setUpdatedMemberId(updatedMemberId);
        request.setProfileImage(profileImage);

        MemberSignUpResponseDTO result = memberCommandService.signUp(request);
        return ResponseEntity.status(ResponseCode.CREATED.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.CREATED, result));
    }

    @PutMapping(value = "/{memberId}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "프로필 이미지 업로드", description = "프로필 이미지를 S3에 업로드하고 Member DB에 반영합니다.")
    public ResponseEntity<CustomApiResponse<S3UploadResponseDTO>> uploadProfileImage(
            @PathVariable int memberId,
            @Parameter(description = "업로드할 파일", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart("file") MultipartFile file) {

        // UUID 생성 (신규 등록과 동일한 방식)
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + ".png";

        // prefix를 profiles로 통일 (memberId 제거)
        String prefix = "profiles";

        MemberProfileUploadRequestDTO request = MemberProfileUploadRequestDTO.builder()
                .prefix(prefix)
                .fileName(fileName)
                .file(file)
                .build();

        S3UploadResponseDTO result = memberProfileFacade.uploadProfileImage(memberId, request);

        return ResponseEntity.status(ResponseCode.CREATED.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.CREATED, result));
    }

    @PatchMapping("/{memberId}")
    @Operation(
            summary = "사원 퇴사 처리",
            description = """
            - 해당 사원의 상태를 '퇴사'로 변경합니다.
            - `resign_at`(퇴사일)을 현재 시간으로 업데이트하고, `status`를 비활성(0)으로 설정합니다.
            """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퇴사 처리 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_400_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "회원 정보가 없음", content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_404_EXAMPLE)))
    })
    public ResponseEntity<CustomApiResponse<Void>> resignMember(
            @PathVariable int memberId
    ) {
        memberCommandService.resignMember(memberId);
        return ResponseEntity.ok(CustomApiResponse.of(ResponseCode.SUCCESS));
    }
}