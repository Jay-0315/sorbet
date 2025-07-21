package com.sorbet.dto;

import com.sorbet.domain.UserLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserActivityDto {
    private String userId;      // = nickname
    private String nickname;
    private String email;
    private int point;
    private UserLevel level;
    private int postCount;
    // 추후 postCount, recentPosts 같은 필드도 추가 가능
}
