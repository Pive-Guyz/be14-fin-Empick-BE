package com.piveguyz.empickbackend.auth.command.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.security.CustomMemberDetails;
import com.piveguyz.empickbackend.security.CustomMemberDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomMemberDetailsService customMemberDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Actuator 엔드포인트와 사용자 정의 Health Check는 JWT 검증 제외
        String requestURI = request.getRequestURI();
        if (requestURI != null && (requestURI.startsWith("/actuator") || requestURI.equals("/health"))) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtProvider.parseClaims(token);
                String memberId = claims.getSubject();

                // memberId로 사용자 정보 조회
                CustomMemberDetails memberDetails =
                        (CustomMemberDetails) customMemberDetailsService.loadUserByUsername(memberId);

                // 🔥 roles 클레임에서 권한 추출 (null-safe)
                List<String> roles = claims.get("roles", List.class);
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (roles != null) {
                    for (String role : roles) {
                        if (role != null && !role.isBlank()) {
                            authorities.add(new SimpleGrantedAuthority(role));
                        }
                    }
                }

                // 🔥 SecurityContextHolder에 권한 포함해서 인증 객체 주입
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(memberDetails, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                SecurityContextHolder.clearContext();
                authenticationEntryPoint.commence(
                        request,
                        response,
                        new InsufficientAuthenticationException("JWT invalid or expired", e)
                );
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
