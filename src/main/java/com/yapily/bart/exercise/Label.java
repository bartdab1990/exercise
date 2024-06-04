package com.yapily.bart.exercise;

public enum Label {
    DRINK("drink"),
    FOOD("food"),
    CLOTHES("clothes"),
    LIMITED("limited");
    private final String value;
    Label(String value) {
        this.value = value;
    }
}
