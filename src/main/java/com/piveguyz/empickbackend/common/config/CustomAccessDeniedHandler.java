package com.piveguyz.empickbackend.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        CustomApiResponse<?> error = CustomApiResponse.of(
                ResponseCode.FORBIDDEN,
                "접근 권한이 없습니다."
        );

        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }
}
