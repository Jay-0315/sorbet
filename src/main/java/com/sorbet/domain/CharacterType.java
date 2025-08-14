package com.sorbet.domain;

import java.util.Random;

// CharacterType.java
public enum CharacterType {
    OTTER("수달 소르", "/images/character/otterRabit.png", Grade.R),
    HERO("용기사 소르", "/images/character/heroRabit.png", Grade.SR),
    SAKURA2("벚꽃 오목이", "/images/character/sakuraBird.png", Grade.SR),
    MATSURI("여름축제 오목이", "/images/character/matsuriBird.png", Grade.SR),
    BIRD("오목이", "/images/character/Bird.png", Grade.R),
    PENGUIN("펭귄 소르", "/images/character/penguinRabit.png", Grade.SR),
    IDOL("아이돌 오목이", "/images/character/idolBird.png", Grade.SR),
    RABIT("소르", "/images/character/Rabit.png", Grade.R),
    HIRABIT("안녕 소르", "/images/character/hiRabit.png", Grade.R),
    ORANGRABIT("태닝 소르", "/images/character/orangRabit.png", Grade.R),
    RUNRABIT("달려! 소르", "/images/character/runRabit.png", Grade.R),
    PBEAR("백고미", "/images/character/pBear.png", Grade.R),
    MUDANG1("견습 백고미", "/images/character/swordBear.png", Grade.R),
    CART("썰매 백고미", "/images/character/cartBear.png", Grade.R),
    MAGIC("견습마녀 오목이", "/images/character/magicBird.png", Grade.R),
    CHOCOBEAR("핫초코 백고미", "/images/character/chocoBear.png", Grade.R),
    MA(
            "maRabbit",
            "천마 소르",
            "/images/character/maRabit.png",
            "/images/bg/maDisplay.png",
            Grade.SSR
    ),
    SAKURA_RABBIT(
            "sakuraRabbit",
            "매화검수 베티",
            "/images/character/sakuraRabit.png",
            "/images/bg/sakuraDisplay.png",
            Grade.SSR
    ),
    MUDANG(
            "mudangBear",
            "무당지검 백고미",
            "/images/character/mudangBear.png",
            "/images/bg/mudangDisplay.png",
            Grade.SSR
    ),
    SACHEON(
            "sacheonBird",
            "독룡 오목이",
            "/images/character/sacheonBird.png",
            "/images/bg/sacheonDisplay.png",
            Grade.SSR
    );

    private final String code;
    private final String displayName;
    private final String imagePath;
    private final String bgImagePath;   // 대표 캐릭터 박스 전용 배경 (없으면 null)
    private final Grade grade;

    /** R/SR 등: code 자동 생성, bg 없음 */
    CharacterType(String displayName, String imagePath, Grade grade) {
        this.code = toSlug(name());   // 예: HIRABIT -> "hirabit"
        this.displayName = displayName;
        this.imagePath = imagePath;
        this.bgImagePath = null;
        this.grade = grade;
    }

    /** SSR 등: code / bg 명시 */
    CharacterType(String code, String displayName, String imagePath, String bgImagePath, Grade grade) {
        this.code = code;
        this.displayName = displayName;
        this.imagePath = imagePath;
        this.bgImagePath = bgImagePath;
        this.grade = grade;
    }

    public String getDisplayName() { return displayName; }
    public String getImagePath() { return imagePath; }
    public Grade getGrade() { return grade; }
    public String getBgImagePath() {
        return bgImagePath;
    }
    public String getCode() {
        return this.code;
    }


    public static CharacterType getRandom() {
        CharacterType[] types = values();
        return types[new Random().nextInt(types.length)];
    }
    private static String toSlug(String name) {
        return name.toLowerCase().replaceAll("_", "");
    }
    }