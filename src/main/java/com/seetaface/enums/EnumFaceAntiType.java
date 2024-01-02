package com.seetaface.enums;

/**
 * 活体检测类型
 */
public enum EnumFaceAntiType {


    /**
     * 局部活体检测
     */
    ANTI_TYPE_PARTIAL(0, "partial", "局部活体检测"),
    /**
     * 全局活体检测
     */
    ANTI_TYPE_TOTALLY(1, "totally", "全局活体检测"),

    ;

    private int type;

    private String typeEn;


    private String desc;

    EnumFaceAntiType(int type, String typeEn, String desc) {
        this.type = type;
        this.typeEn = typeEn;
        this.desc = desc;
    }

    /**
     * 获取
     *
     * @param type
     * @return
     */
    public static EnumFaceAntiType getFaceAntiType(int type) {
        EnumFaceAntiType[] enumFaceAntiTypes = EnumFaceAntiType.values();
        for (EnumFaceAntiType faceAntiType : enumFaceAntiTypes) {
            if (faceAntiType.type == type) {
                return faceAntiType;
            }
        }

        return null;
    }

    public int getType() {
        return type;
    }

    public String getTypeEn() {
        return typeEn;
    }

    public String getDesc() {
        return desc;
    }
}
