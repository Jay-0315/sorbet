package com.sorbet.service;

import com.sorbet.domain.User;
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
        String encoded = passwordEncoder. encode(rawPassword);
        User user = User.builder()
                .username(username)
                .password(encoded)
                        .build();
                return userRepository.save(user);
                
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
