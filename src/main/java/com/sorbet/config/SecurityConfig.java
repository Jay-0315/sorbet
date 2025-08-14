package com.sorbet.config;

import com.sorbet.security.jwt.JwtAuthenticationFilter;
import com.sorbet.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰 생성 및 검증 컴포넌트

    /**
     * 비밀번호 암호화 방식 등록 (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 인증 관리자 등록 (UsernamePasswordAuthenticationToken 처리를 위해 필요)
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Spring Security 설정 필터 체인
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(httpBasic -> httpBasic.disable()) // 기본 인증 (ID/PW 팝업) 비활성화
                .csrf(csrf -> csrf.disable()) // JWT 방식에서는 CSRF 비활성화 (HttpOnly 쿠키로 보호)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 상태 저장 안함
                )
                // 기본 보안 헤더 설정
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()) // 클릭재킹 방지
                        .contentTypeOptions(contentType -> {}) // MIME 스니핑 방지
                )
                .authorizeHttpRequests(auth -> auth
                        // 공개 접근 가능한 경로 (간단하게)
                        .requestMatchers("/", "/font/**","/home", "/register", "/login", "/logout",
                                       "/css/**", "/js/**", "/images/**", "/static/**",
                                       "/comments", "/posts/**","/favicon.ico","/uploads/**").permitAll()
                        // 인증이 필요한 경로
                        .requestMatchers(HttpMethod.POST, "/upload-image").permitAll()


                        .requestMatchers("/mypage", "/createpost","/gatcha/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider), // JWT 필터 등록
                        UsernamePasswordAuthenticationFilter.class     // UsernamePasswordAuthenticationFilter 전에 실행
                );

        return http.build();
    }
}
