package com.sorbet.dto;

import com.sorbet.domain.CharacterType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CharacterSummaryDto {
    private CharacterType characterType;
    private Long count;
}