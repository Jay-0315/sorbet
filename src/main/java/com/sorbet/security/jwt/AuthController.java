package com.sorbet.security.jwt;

import com.sorbet.dto.LoginRequestDto;
import com.sorbet.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto,
                                   HttpServletResponse response) {

        // 1. 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUserId(), dto.getPassword())
        );

        // 2. JWT 토큰 생성
        String token = jwtTokenProvider.createToken(dto.getUserId(),
                authentication.getAuthorities().stream().findFirst().get().getAuthority());

        // 3. 보안 강화된 HttpOnly 쿠키 설정
        Cookie cookie = new Cookie("sorbet-token", token);
        cookie.setHttpOnly(true);        // XSS 방지 - JavaScript에서 접근 불가
        cookie.setSecure(true);          // HTTPS에서만 전송
        cookie.setPath("/");             // 모든 경로에서 접근 가능
        cookie.setMaxAge(60 * 60 * 24); // 24시간
        cookie.setAttribute("SameSite", "Lax"); // CSRF 방지 + 편의성
        response.addCookie(cookie);

        // 4. 추가 보안 헤더 설정
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");

        return ResponseEntity.ok().body("로그인 성공");
    }
}

