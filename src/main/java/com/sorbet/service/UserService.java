package com.sorbet.service;

import com.sorbet.domain.Role;
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

    private final CustomUserDetailService customUserDetailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;

    // DTO 기반 회원가입
    public Long join(UserRegisterDto dto) {
        if (userValidation.existsByUserId(dto.getUserId())) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        if (userValidation.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("이미 등록된 이메일입니다.");
        }

        if (userValidation.existsByNickname(dto.getNickname())) {
            throw new IllegalStateException("이미 사용 중인 닉네임입니다.");
        }

        // User 객체 생성 + 기본값 설정
        User user = User.builder()
                .userLoginId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .level(UserLevel.PLAIN)         // 포인트 등급
                .role(Role.USER)                // 기본 권한: 일반 사용자
                .build();

        user.updateLevelByPoint(); // 등급 재계산 (포인트 기반)

        return userRepository.save(user).getId(); // 저장 후 ID 반환
    }

    // 유저 아이디로 유저 조회
    public User findByUserId(String userId) {
        return userRepository.findByUserLoginId(userId).orElse(null);
    }
}
