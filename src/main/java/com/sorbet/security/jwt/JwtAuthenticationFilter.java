package com.sorbet.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // 생성자 주입
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // 정적 리소스는 필터 통과
        if (path.startsWith("/css/") || path.startsWith("/js/") || 
            path.startsWith("/images/") || path.startsWith("/static/")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 1. 쿠키에서 토큰 추출
            String token = resolveToken(request);

            // 2. 토큰이 있으면 유효성 검사 후 인증 등록
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // 다음 필터로 진행 (토큰이 없어도 진행)
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            // 토큰 만료 시 쿠키 삭제하고 계속 진행
            Cookie expiredCookie = new Cookie("sorbet-token", "");
            expiredCookie.setMaxAge(0);
            expiredCookie.setPath("/");
            response.addCookie(expiredCookie);
            
            filterChain.doFilter(request, response);

        } catch (JwtException | IllegalArgumentException e) {
            // 잘못된 토큰 시 쿠키 삭제하고 계속 진행
            Cookie invalidCookie = new Cookie("sorbet-token", "");
            invalidCookie.setMaxAge(0);
            invalidCookie.setPath("/");
            response.addCookie(invalidCookie);
            
            filterChain.doFilter(request, response);
        }
    }

    // ✅ HttpOnly 쿠키에서 sorbet-token 추출
    private String resolveToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("sorbet-token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

