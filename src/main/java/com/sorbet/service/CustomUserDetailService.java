package com.sorbet.service;

import com.sorbet.entity.User;
import com.sorbet.repository.UserRepository;
import com.sorbet.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // userId로 User 조회해서 CustomUserDetails로 감싸서 리턴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUserLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + username));

        return new CustomUserDetails(user);
    }
}
