package com.sorbet.security.jwt;


import com.sorbet.entity.User;
import com.sorbet.repository.UserRepository;
import com.sorbet.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.security.Key;
import java.util.List;


@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey; // application.yml 에서 가져올 비밀키!

    private Key key; // 암호화에 사용할 실제 SecretKey 객체

    private final long tokenValidityInMillis =1000L*60*60;
    
    private final UserRepository userRepository;

    public JwtTokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 초기화 시 SecretKey를 Base64 인코딩해서 키 객체로 전환
    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //JWT 토큰 생성 메소드!
    public String createToken(String userId, String role) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + tokenValidityInMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    //토큰에서 인증 정보를 추출
    public Authentication getAuthentication(String token) {
        String userId = getUserId(token);
        String role = parseClaims(token).getBody().get("role", String.class);

        // DB에서 User 정보를 가져와서 CustomUserDetails 생성
        User user = userRepository.findByUserLoginId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));
        
        CustomUserDetails userDetails = new CustomUserDetails(user);
        
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 userId 추출
    public String getUserId(String token) {
        return parseClaims(token).getBody().getSubject();
    }
    
    //토큰 유효성 검사 (만료 되었는지 아닌지)
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        }catch(JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    //클레임 파싱
    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}




