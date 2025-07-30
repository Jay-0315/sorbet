package com.sorbet.domain;

import java.util.Random;

// CharacterType.java
public enum CharacterType {
    MA("천마 토끼", "/images/character/maRabit.png"),
    PENGUIN("펭귄 토끼", "/images/character/penguinRabit.png"),
    SAKURA("화산 토끼", "/images/character/sakuraRabit.png"),
    OTTER("수달 토끼", "/images/character/otterRabit.png");

    private final String displayName;
    private final String imagePath;

    CharacterType(String displayName, String imagePath) {
        this.displayName = displayName;
        this.imagePath = imagePath;
    }

    public String getDisplayName() { return displayName; }
    public String getImagePath() { return imagePath; }

    public static CharacterType getRandom() {
        CharacterType[] types = values();
        return types[new Random().nextInt(types.length)];
    }
}
