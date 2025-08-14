package com.sorbet.dto;

import com.sorbet.domain.CharacterType;
import com.sorbet.domain.UserLevel;
import com.sorbet.entity.UserCharacterInventory;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder
public class UserActivityDto {
    private String userId;      // = nickname
    private String nickname;
    private String email;
    private int point;
    private UserLevel level;
    private int postCount;
    //확인필요.
    private String levelLabel = "";
    private String levelColor = "";
    private String levelEmoji = "";
    private CharacterType character;
    private List<UserCharacterInventoryDto> inventory;

    // 추후 postCount, recentPosts 같은 필드도 추가 가능
}
