package com.piveguyz.empickbackend.orgstructure.member.query.controller;

import com.piveguyz.empickbackend.auth.facade.AuthFacade;
import com.piveguyz.empickbackend.common.constants.ApiExamples;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.orgstructure.facade.MemberProfileFacade;
import com.piveguyz.empickbackend.orgstructure.member.query.dto.MemberEditProposalQueryDTO;
import com.piveguyz.empickbackend.orgstructure.member.query.dto.MemberResponseDTO;
import com.piveguyz.empickbackend.orgstructure.member.query.dto.MemberRoleQueryDTO;
import com.piveguyz.empickbackend.orgstructure.member.query.service.MemberEditProposalQueryService;
import com.piveguyz.empickbackend.orgstructure.member.query.service.MemberQueryService;
import com.piveguyz.empickbackend.orgstructure.member.query.service.MemberQueryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "사원 API", description = "사원 조회 API")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberProfileFacade memberProfileFacade;
    private final MemberQueryService memberQueryService;
    private final AuthFacade authFacade;
    private final MemberEditProposalQueryService queryService;
    private final MemberQueryServiceImpl memberQueryServiceImpl;

    @Operation(
            summary = "내 정보 조회",
            description = """
            - 로그인한 사용자의 정보를 조회합니다. (JWT 토큰 필요)
            """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공", content = @Content(schema = @Schema(implementation = MemberResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패 (JWT 토큰 필요)", content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_401_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음", content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_404_EXAMPLE)))
    })
    @GetMapping("/me")
    public ResponseEntity<CustomApiResponse<MemberResponseDTO>> getCurrentMember() {
        Integer memberId = authFacade.getCurrentMemberId();
        MemberResponseDTO responseDTO = memberProfileFacade.getMemberInfo(memberId);
        return ResponseEntity.ok(CustomApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }

    @Operation(
            summary = "사원 검색",
            description = "- 이름(name) 또는 사번(employeeNumber)으로 사원을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사원 조회 성공", content = @Content(schema = @Schema(implementation = MemberResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "사원 정보를 찾을 수 없음", content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_404_EXAMPLE)))
    })
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<MemberResponseDTO>>> getMembers(
            @RequestParam(value = "employeeNumber", required = false) Integer employeeNumber
    ) {

        if (employeeNumber == null) {
            List<MemberResponseDTO> members = memberQueryService.findAllMembers();
            return ResponseEntity.ok(CustomApiResponse.of(ResponseCode.SUCCESS, members));
        } else {
            List<MemberResponseDTO> members = memberQueryServiceImpl.getMembersByEmployeeNumber(employeeNumber);
            return ResponseEntity.ok(CustomApiResponse.of(ResponseCode.SUCCESS, members));
        }
    }

    @Operation(
            summary = "프로필 이미지 다운로드",
            description = "- memberId로 S3에서 프로필 이미지를 다운로드합니다."
    )
    @GetMapping("/{memberId}/profile-image")
    public ResponseEntity<?> getProfileImage(@PathVariable int memberId) {
        try {
            // Facade를 통해 프로필 이미지 다운로드
            byte[] imageData = memberProfileFacade.downloadProfileImage(memberId);
            String profileImageKey = memberProfileFacade.getProfileImageKey(memberId);

            // profileImageKey가 null이거나 빈 문자열인 경우 처리
            if (profileImageKey == null || profileImageKey.isEmpty()) {
                return ResponseEntity.status(404).body("프로필 이미지가 없습니다.");
            }

            String contentType = guessContentType(profileImageKey);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageData);

        } catch (Exception e) {
            // S3에 파일이 없거나 기타 오류 발생 시
            if (e.getMessage() != null && e.getMessage().contains("NoSuchKey")) {
                return ResponseEntity.status(404).body("프로필 이미지를 찾을 수 없습니다.");
            }
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }

    @Operation(
            summary = "사원 정보 수정 요청 조회",
            description = """
                    - 특정 사원의 수정 요청 내역을 조회합니다.
                    - status(0: PENDING, 1: APPROVED, 2: REJECTED)로 필터링 가능합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 요청 조회 성공", content = @Content(schema = @Schema(implementation = MemberEditProposalQueryDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_400_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "데이터를 찾을 수 없음", content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_404_EXAMPLE)))
    })
    @GetMapping("/proposals")
    public ResponseEntity<CustomApiResponse<List<MemberEditProposalQueryDTO>>> getProposals(
            @RequestParam(value = "memberId", required = false) Integer memberId
    ) {
        List<MemberEditProposalQueryDTO> proposals = queryService.getProposals(memberId);
        return ResponseEntity.ok(CustomApiResponse.of(ResponseCode.SUCCESS, proposals));
    }

    private String guessContentType(String key) {
        String lowerKey = key.toLowerCase();

        if (lowerKey.endsWith(".png")) return "image/png";
        if (lowerKey.endsWith(".jpg") || lowerKey.endsWith(".jpeg")) return "image/jpeg";
        if (lowerKey.endsWith(".webp")) return "image/webp";
        if (lowerKey.endsWith(".gif")) return "image/gif";
        if (lowerKey.endsWith(".svg")) return "image/svg+xml";

        return "application/octet-stream";
    }

    @Operation(
            summary = "내 권한 목록 조회",
            description = """
        - 사번(employeeNumber)에 해당하는 사원의 권한(Role) 목록을 조회합니다.
        - 권한에는 code, name, description, roleType 등이 포함됩니다.
        """
            ,security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "권한 목록 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MemberRoleQueryDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_400_EXAMPLE))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "데이터를 찾을 수 없음",
                    content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_404_EXAMPLE))
            )
    })
    @GetMapping("/my-roles")
    private ResponseEntity<CustomApiResponse<List<MemberRoleQueryDTO>>> getMyRoles() {
        authFacade.getCurrentMemberId();
        int employeeNumber = Integer.parseInt(authFacade.getCurrentEmployeeNumber());
        List<MemberRoleQueryDTO> memberRoles = memberQueryService.getMemberRoles(employeeNumber);
        return ResponseEntity.ok(CustomApiResponse.of(ResponseCode.SUCCESS, memberRoles));
    }

    @Operation(
            summary = "사원 권한 목록 조회",
            description = """
        - 사번(employeeNumber)에 해당하는 다른 사원의 권한(Role) 목록을 조회합니다.
        - 권한에는 code, name, description, roleType, createdAt, updatedAt, deletedAt이 포함됩니다.
        """
    )
    @Parameters({
            @Parameter(name = "employeeNumber", description = "조회할 사원의 사번", required = true, example = "100004")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "권한 목록 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MemberRoleQueryDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_400_EXAMPLE))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "데이터를 찾을 수 없음",
                    content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_404_EXAMPLE))
            )
    })
    @GetMapping("/roles")
    private ResponseEntity<CustomApiResponse<List<MemberRoleQueryDTO>>> getMemberRoles(@RequestParam(value = "employeeNumber", required = true) Integer employeeNumber) {
        List<MemberRoleQueryDTO> memberRoles = memberQueryService.getMemberRoles(employeeNumber);
        return ResponseEntity.ok(CustomApiResponse.of(ResponseCode.SUCCESS, memberRoles));
    }

    @GetMapping("/{id}")
    private ResponseEntity<CustomApiResponse<MemberResponseDTO>> getMemberById(@PathVariable("id") Integer id){
        MemberResponseDTO dto = memberQueryService.getMemberById(id);
        return ResponseEntity.ok(CustomApiResponse.of(ResponseCode.SUCCESS, dto));
    }
}
