package com.piveguyz.empickbackend.common.config;

import com.piveguyz.empickbackend.auth.command.jwt.JwtAuthenticationFilter;
import com.piveguyz.empickbackend.security.CustomMemberDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final CustomMemberDetailsService customMemberDetailsService;

    @Autowired
    public SecurityConfig(CustomMemberDetailsService customMemberDetailsService) {
        this.customMemberDetailsService = customMemberDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtAuthenticationFilter,
                                           JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                           CustomAccessDeniedHandler customAccessDeniedHandler) throws Exception {
        http
                .cors(Customizer.withDefaults()) // CORS 적용
                .csrf(csrf -> csrf.disable())    // CSRF 끄기
                .authorizeHttpRequests(auth -> auth
                        // ✅ 인증이 필요 없는 경로 (가장 먼저 설정)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/actuator/health",                     // ALB Health Check
                                "/api/v1/auth/**",                      // 로그인/회원가입
                                "/api/v1/employment/jobtests/exam/**",  // 실무테스트 응시 관련
                                "/api/v1/employment/answers/**",        // 실무테스트 답안 제출
                                "/api/v1/employment/jobtests/*"         // 실무테스트 문제 조회
                                // "/api/v1/**"                         // 테스트용 (필요시 주석 해제)
                        ).permitAll()

                        // ✅ 권한별 접근 제어 (구체적인 경로부터 설정)
                        // 실무테스트/문제 - 인사팀, 팀장까지 가능
                        .requestMatchers("/api/v1/employment/questions/**").hasAnyRole("HR_ACCESS", "APPROVAL_PROCESSOR")
                        .requestMatchers("/api/v1/employment/jobtests/**").hasAnyRole("HR_ACCESS", "APPROVAL_PROCESSOR")

                        // 채용 관련 - 인사팀만 가능
                        .requestMatchers("/api/v1/employment/**").hasAnyRole("HR_ACCESS")

                        // 결재 - USER 권한
                        .requestMatchers("/api/v1/approval/**").hasAnyRole("USER")

                        // 근태 - USER 권한
                        .requestMatchers("/api/v1/attendance/**").hasAnyRole("USER")

                        // 🔒 그 외 모든 /api/** 경로는 JWT 인증 필요
                        .requestMatchers("/api/**").authenticated()

                        // 🔒 나머지 모든 경로는 인증 필요 (반드시 마지막에!)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)      // 401 처리
                        .accessDeniedHandler(customAccessDeniedHandler)             // 403 처리
                )
                // JWT 인증 필터 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}