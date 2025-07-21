package com.sorbet.service;

import com.sorbet.entity.User;
import com.sorbet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    //username 으로 DB에서 유저를 조회해서 userDetils 반환
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository
                .findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다" + username));

    }

}
