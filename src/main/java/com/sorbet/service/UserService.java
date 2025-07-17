package com.sorbet.service;

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
    private final UserValidation userValidation; // 필드명 일치시킴

    public User register(String username, String rawPassword) {
        String encoded = passwordEncoder.encode(rawPassword);
        User user = User.builder()
                .username(username)
                .password(encoded)
                .build();
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public Long join(User user) {
        if (userValidation.existsByUsername(user.getUsername())) { // 인스턴스 메서드 호출로 변경
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }
}

