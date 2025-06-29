package com.piveguyz.empickbackend.employment.recruitmentRequest.command.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piveguyz.empickbackend.auth.command.jwt.JwtProvider;
import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.recruitmentRequest.command.application.dto.RecruitmentRequestCommandDTO;
import com.piveguyz.empickbackend.employment.recruitmentRequest.command.application.service.RecruitmentRequestCommandService;
import com.piveguyz.empickbackend.security.CustomMemberDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "채용 공고 API", description = "채용 공고 관련 전체 API")
@RestController("RecruitmentRequestCommandController")
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/recruitments/requests")
@SecurityRequirement(name = "bearerAuth")
public class RecruitmentRequestCommandController {

	private final RecruitmentRequestCommandService recruitmentRequestCommandService;
	private final JwtProvider jwtProvider;

	@Operation(
		summary = "채용 요청서 등록",
		description = """
                    채용 요청서를 등록합니다.
                    
                    부서 ID, 인원 수, 기간, 직무 요건 등을 포함해야 합니다.
                    """
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	@PostMapping
	public ResponseEntity<CustomApiResponse<Void>> create(
		@RequestBody RecruitmentRequestCommandDTO dto,
		@AuthenticationPrincipal CustomMemberDetails memberDetails,
		HttpServletRequest request) {

		int memberId;

		// 1. 인증 객체에서 꺼내기
		if (memberDetails != null) {
			memberId = memberDetails.getId();
		} else {
			// 2. 인증 객체가 없을 경우 토큰에서 직접 추출
			String token = request.getHeader("Authorization");
			if (token != null && token.startsWith("Bearer ")) {
				token = token.substring(7); // "Bearer " 제거
				memberId = jwtProvider.getMemberIdFromToken(token); // ⬅️ 이 메서드는 JwtProvider에 있어야 함
			} else {
				throw new BusinessException(ResponseCode.UNAUTHORIZED);
			}
		}

		recruitmentRequestCommandService.create(dto, memberId);
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS));
	}
}
