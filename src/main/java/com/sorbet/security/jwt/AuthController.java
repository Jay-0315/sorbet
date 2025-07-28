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

        // 3. 쿠키에 저장
        Cookie cookie = new Cookie("sorbet-token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        cookie.setSecure(false); // 1시간
        response.addCookie(cookie);

        return ResponseEntity.ok().body("로그인 성공");
    }
}

