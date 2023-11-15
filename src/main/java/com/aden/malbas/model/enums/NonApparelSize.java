package com.aden.malbas.model.enums;

import lombok.Getter;

@Getter
public enum NonApparelSize {

    ONE_SIZE,
    SMALL,
    MEDIUM,
    LARGE,
    CUSTOM_SIZE;

    private final int width;
    private final int height;

    NonApparelSize() {
        this.width = 0;
        this.height = 0;
    }

    NonApparelSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

}
