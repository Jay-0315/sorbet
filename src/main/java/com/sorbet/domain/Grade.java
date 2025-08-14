package com.sorbet.domain;

public enum Grade {
    SSR(5),  // 5%
    SR(15),  // 15%
    R(80);   // 80%

    private final int rate;

    Grade(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}