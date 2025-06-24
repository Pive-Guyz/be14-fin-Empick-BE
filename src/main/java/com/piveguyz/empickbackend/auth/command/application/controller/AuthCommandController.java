package com.piveguyz.empickbackend.auth.command.application.controller;

import com.piveguyz.empickbackend.auth.command.application.dto.LoginRequestDTO;
import com.piveguyz.empickbackend.auth.command.application.dto.LoginResponseDTO;
import com.piveguyz.empickbackend.auth.command.application.dto.LogoutRequestDTO;
import com.piveguyz.empickbackend.auth.command.application.dto.RefreshTokenRequestDTO;
import com.piveguyz.empickbackend.auth.command.application.service.AuthCommandService;
import com.piveguyz.empickbackend.auth.command.application.service.RefreshTokenService;
import com.piveguyz.empickbackend.auth.command.jwt.JwtProvider;
import com.piveguyz.empickbackend.common.constants.ApiExamples;
import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "인증 API", description = "로그인/회원가입/토큰 발급 등 인증 관련 API")
public class AuthCommandController {

    private final AuthCommandService authCommandService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;

    @Operation(
            summary = "로그인",
            description = """
                    - 사번과 비밀번호를 통해 로그인을 수행하고 AccessToken 및 RefreshToken을 발급합니다.
                    - employeeNumber : 100001, pwd :  password1234@@
                    - employeeNumber : 100002, pwd :  password5678@@
                    - employeeNumber : 100003, pwd :  password9876@@ (퇴사)
                    - employeeNumber : 100004, pwd :  123qwe@@ (인사팀 권한 가짐)
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(examples = @ExampleObject(
                                    value = ApiExamples.ERROR_400_EXAMPLE))),

            @ApiResponse(responseCode = "1001", description = "삭제된 사원",
                    content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_1001_EXAMPLE))),

            @ApiResponse(responseCode = "1004", description = "차단된 사원",
                    content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_1004_EXAMPLE))),

            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_401_EXAMPLE))),

            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_500_EXAMPLE)))
    })

    @PostMapping("/login")
    public ResponseEntity<CustomApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO responseDTO = authCommandService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }

    @Operation(
            summary = "로그아웃",
            description = "Refresh Token을 Redis에서 삭제하여 로그아웃 처리"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(examples = @ExampleObject(
                            value = ApiExamples.ERROR_400_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(examples = @ExampleObject(value = ApiExamples.ERROR_500_EXAMPLE)))
    })
    @PostMapping("/logout")
    public ResponseEntity<CustomApiResponse<Void>> logout(@RequestBody LogoutRequestDTO request) {
        int memberId = jwtProvider.getMemberIdFromToken(request.getRefreshToken());
        refreshTokenService.deleteRefreshToken(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.of(ResponseCode.SUCCESS, null));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<CustomApiResponse<Void>> logout(@RequestBody LogoutRequestDTO request) {
//        int memberId = jwtProvider.getMemberIdFromToken(request.getRefreshToken());
//
//        refreshTokenService.deleteRefreshToken(memberId);
//
//        return ResponseEntity.ok(CustomApiResponse.of(ResponseCode.SUCCESS, null));
//    }

    @Operation(
            summary = "Access Token 재발급",
            description = """
                    - Refresh Token을 검증한 후 Access Token을 재발급합니다.
                    - Refresh Token은 로그인 시 발급되며, 만료되기 전까지 사용 가능합니다.
                    - 만약 Refresh Token이 유효하지 않으면 INVALID_REFRESH_TOKEN 오류가 발생합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access Token 재발급 성공",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(examples = @ExampleObject(
                            value = ApiExamples.ERROR_400_EXAMPLE))),

//            @ApiResponse(responseCode = "401", description = "Refresh Token이 유효하지 않음",
//                    content = @Content(examples = @ExampleObject(
//                            value = ApiExamples.INVALID_REFRESH_TOKEN_EXAMPLE))),

            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(examples = @ExampleObject(
                            value = ApiExamples.ERROR_500_EXAMPLE)))
    })

    @PostMapping("/token/refresh")
    public ResponseEntity<CustomApiResponse<LoginResponseDTO>> refresh(@Valid @RequestBody RefreshTokenRequestDTO request) {
        int memberId = jwtProvider.getMemberIdFromToken(request.getRefreshToken());

        if (request.getRefreshToken() == null || request.getRefreshToken().isBlank() || !refreshTokenService.validateRefreshToken(memberId, request.getRefreshToken())) {
            throw new BusinessException(ResponseCode.INVALID_REFRESH_TOKEN);
        }

        // access token 재발급
        String newAccessToken = jwtProvider.createAccessToken(memberId, request.getEmployeeNumber(), request.getRoles());

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse
                        .of(ResponseCode.SUCCESS, new LoginResponseDTO(newAccessToken, request.getRefreshToken())));
    }
}
