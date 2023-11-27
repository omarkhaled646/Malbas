package com.aden.malbas.model.enums;

import lombok.Getter;

@Getter
public enum NonApparelSize {

    ONE_SIZE,
    SMALL,
    MEDIUM,
    LARGE,
    CUSTOM_SIZE;

    private Integer width;
    private Integer height;

    public void setCustomSize(Integer width, Integer height){
        this.width = width;
        this.height = height;
    }

}
