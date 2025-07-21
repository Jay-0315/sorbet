package com.sorbet.repository;

import com.sorbet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    public boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }
}
