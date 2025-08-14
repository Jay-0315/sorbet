package com.sorbet.dto;

import com.sorbet.domain.CharacterType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserCharacterInventoryDto {
    private CharacterType characterType;
    private LocalDateTime acquiredAt;
    private boolean mainCharacter;
}