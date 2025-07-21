// src/main/java/com/sorbet/service/MypageService.java
package com.sorbet.service;

import com.sorbet.dto.UserActivityDto;
import com.sorbet.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    public UserActivityDto getUserInfo(User user) {
        return UserActivityDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .userId(user.getUserId())
                .point(user.getPoint())
                .level(user.getLevel())
                .build();
    }
}
