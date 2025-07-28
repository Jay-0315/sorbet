package com.sorbet.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // 쿠키 삭제: 동일한 이름 & 경로 & maxAge = 0
        ResponseCookie cookie = ResponseCookie.from("sorbet-token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok("로그아웃 완료");
    }
}