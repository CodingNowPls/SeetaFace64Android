package com.seetaface.enums;

public enum EnumGender {

    MALE(0, "男"),
    FEMALE(1, "女");


    private int type;

    private String desc;

    EnumGender(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
