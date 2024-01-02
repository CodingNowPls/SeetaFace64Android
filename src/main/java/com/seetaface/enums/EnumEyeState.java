package com.seetaface.enums;

/**
 * 眼睛状态
 */
public enum EnumEyeState {


    /**
     * 闭眼
     */
    EYE_CLOSE(0, "close", "闭眼"),
    /**
     * 睁眼
     */
    EYE_OPEN(1, "open", "睁眼"),

    /**
     * 非眼部区域
     */
    EYE_RANDOM(2, "random", "非眼部区域"),
    /**
     * 未知状态
     */
    EYE_UNKNOWN(3, "unknown", "未知状态"),

    ;

    private int state;

    private String typeEn;


    private String desc;

    EnumEyeState(int state, String typeEn, String desc) {
        this.state = state;
        this.typeEn = typeEn;
        this.desc = desc;
    }

    /**
     * 获取
     *
     * @param state
     * @return
     */
    public static EnumEyeState getEyeState(int state) {
        EnumEyeState[] enumEyeState = EnumEyeState.values();
        for (EnumEyeState eyeState : enumEyeState) {
            if (eyeState.state == state) {
                return eyeState;
            }
        }

        return null;
    }

}
