// src/main/java/com/sorbet/service/MypageService.java
package com.sorbet.service;

import com.sorbet.domain.CharacterType;
import com.sorbet.dto.CharacterSummaryDto;
import com.sorbet.dto.UserActivityDto;
import com.sorbet.dto.UserCharacterInventoryDto;
import com.sorbet.entity.User;
import com.sorbet.entity.UserCharacterInventory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class MyPageService {

    public UserActivityDto getUserInfo(User user) {
        user.updateLevelByPoint();

        List<UserCharacterInventoryDto> inventoryDtos = user.getInventory().stream()
                .map(item -> UserCharacterInventoryDto.builder()
                        .characterType(item.getCharacterType())
                        .acquiredAt(item.getAcquiredAt())
                        .mainCharacter(item.getCharacterType() == user.getCharacter()) // 대표 여부
                        .build())
                .toList();

        return UserActivityDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .userId(user.getUserLoginId())
                .point(user.getPoint())
                .level(user.getLevel())
                .character(user.getCharacter())
                .inventory(inventoryDtos)
                .build();
    }

    public List<CharacterSummaryDto> getInventorySummary(User user) {
        Map<CharacterType, Long> countMap = user.getInventory().stream()
                .collect(Collectors.groupingBy(
                        UserCharacterInventory::getCharacterType,
                        Collectors.counting()
                ));

        return countMap.entrySet().stream()
                .map(entry -> new CharacterSummaryDto(entry.getKey(), entry.getValue()))
                .toList();
    }

}
