package com.sorbet.domain;

public enum UserLevel {
    PLAIN, MINT, LIME, CHERRY, BLCAK;

    public static UserLevel calculateLevel(int point) {
        if(point >= 1000) return BLCAK;
        else if(point >= 500) return CHERRY;
        else if(point >= 200) return LIME;
        else if (point >= 100) return MINT;
        else return PLAIN;
    }
}

