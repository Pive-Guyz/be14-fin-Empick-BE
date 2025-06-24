package com.piveguyz.empickbackend.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 401 에러 처리 클래스
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        CustomApiResponse<?> error = CustomApiResponse.of(
                ResponseCode.UNAUTHORIZED,
                "토큰이 없거나 만료되었습니다."
        );

        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }
}
