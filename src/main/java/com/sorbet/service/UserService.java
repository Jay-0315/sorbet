package com.sorbet.service;

import com.sorbet.entity.User;
import com.sorbet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String username, String rawPassword) {
        String encoded = passwordEncoder.encode(rawPassword);
        User user = User.builder()
                .username(username)
                .password(encoded)
                .build();  // ← 여기가 build()로 닫혀야 해!
        return userRepository.save(user);  // ← 이 return도 build 밖으로 나와야 함
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
