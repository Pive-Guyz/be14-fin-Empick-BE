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

                                // ✅ 인증이 필요 없는 경로
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources/**",
                                        "/webjars/**",
                                        "/actuator/**",                         // Actuator 엔드포인트 (Health Check용)
                                        "/api/v1/auth/**",                      // 로그인/회원가입
                                        "/api/v1/employment/jobtests/exam/**",  // 실무테스트 응시 관련
                                        "/api/v1/employment/answers/**",        // 실무테스트 답안 제출
                                        "/api/v1/employment/jobtests/*",      // 실무테스트 문제 조회
//                                "/api/v1/**"                // 테스트용으로 모든 경로 sequrity 처리 안되게
                                  
                                //  Career 페이지 - 채용공고 관련
                                "/api/v1/employment/recruitments",                           // 채용공고 목록
                                "/api/v1/employment/recruitments/**",                       // 채용공고 상세 및 하위 경로
                                "/api/v1/employment/recruitments/requests/**",              // 채용 요청서
                                "/api/v1/employment/recruitments/processes/**",             // 채용 프로세스

                                //  Career 페이지 - 지원자/지원서 관련
                                "/api/v1/employment/applicant/create",                      // 지원자 생성
                                "/api/v1/employment/application",                           // 지원서 생성
                                "/api/v1/employment/application-response",                  // 이력서 응답 저장
                                "/api/v1/employment/applications/items/**",                 // 지원서 항목 조회

                                //  Career 페이지 - 자기소개서 관련
                                "/api/v1/employment/introduce",                             // 자기소개서 생성/조회
                                "/api/v1/employment/introduce/**",                          // 자기소개서 관련 모든 경로
                                "/api/v1/employment/introduce-template/**",                 // 자기소개서 템플릿
                                "/api/v1/employment/introduce-template-item-response",      // 자기소개서 항목별 응답

                                //  Career 페이지 - 마스터 데이터
                                "/api/v1/jobs",                                             // 직무 목록
                                "/api/v1/departments",                                      // 부서 목록

                                //  Career 페이지 - 파일 관련
                                "/api/v1/files/upload",                                     // 파일 업로드 (프로필 이미지)
                                "/api/v1/files/download"                                    // 파일 다운로드
                                  
                                ).permitAll()

                                // ✅ 인증이 필요한 경로
                                // 결재
                                .requestMatchers("/api/v1/approval/**").hasAnyRole("USER")

                                // 근태
                                .requestMatchers("/api/v1/attendance/**").hasAnyRole("USER")


                                // 실무테스트/문제 - 인사팀, 팀장까지 가능
                                .requestMatchers("/api/v1/employment/questions/**").hasAnyRole("HR_ACCESS", "APPROVAL_PROCESSOR")
                                .requestMatchers("/api/v1/employment/jobtests/**").hasAnyRole("HR_ACCESS", "APPROVAL_PROCESSOR")
                                // 채용 관련 - 인사팀만 가능
                                .requestMatchers("/api/v1/employment/**").hasAnyRole("HR_ACCESS")

                                // 🔒 그 외 모든 /api/** 경로는 JWT 인증 필터 작동
                                .requestMatchers("/api/**").authenticated()
                                // 🔒 나머지 경로는 기본 인증
                                .anyRequest().authenticated()
                ).exceptionHandling(ex -> ex
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