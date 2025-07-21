// src/main/java/com/sorbet/service/UserService.java
package com.sorbet.service;

import com.sorbet.domain.UserLevel;
import com.sorbet.dto.UserRegisterDto;
import com.sorbet.entity.User;
import com.sorbet.repository.UserRepository;
import com.sorbet.repository.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;

    // ✅ DTO 기반 회원가입
    public Long join(UserRegisterDto dto) {
        if (userValidation.existsByUserId(dto.getUserId())) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        User user = User.builder()
                .userId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .level(UserLevel.PLAIN)
                .build();

        return userRepository.save(user).getId();
    }

    // 기타 유저 조회 로직
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElse(null);
    }
}
