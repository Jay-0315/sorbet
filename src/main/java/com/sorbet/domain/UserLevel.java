package com.sorbet.domain;

import lombok.Getter;

@Getter
public enum UserLevel {
    PLAIN("플레인", "🥛", "#c0c0c0"),
    MINT("민트", "🍃", "#a2f1d0"),
    LIME("라임", "🍋", "#b5e642"),
    CHERRY("체리", "🍒", "#ff6781"),
    BLACK("블랙", "👑", "#333");


    private final String label;
    private final String emoji;
    private final String colorCode;

    UserLevel(String label, String emoji, String colorCode) {
        this.label = label;
        this.emoji = emoji;
        this.colorCode = colorCode;
    }

    public static UserLevel calculateLevel(int point) {
        if(point >= 1000) return BLACK;
        else if(point >= 500) return CHERRY;
        else if(point >= 200) return LIME;
        else if (point >= 100) return MINT;
        else return PLAIN;
    }

    public String getColorCode() {
        return switch (this) {
            case PLAIN -> "#c0c0c0";
            case MINT -> "#a2f1d0";
            case LIME -> "#b5e642";
            case CHERRY -> "#ff6781";
            case BLACK -> "#333";
        };
    }

}
