package com.seetaface.enums;

/**
 * 活体检测结果
 */
public enum EnumFaceAntiStatus {
    /**
     * 真实人脸
     */
    STATUS_REAL(0, "real", "真实人脸"),
    /**
     * 攻击人脸（假人脸）
     */
    STATUS_SPOOF(1, "spoof", "假人脸"),
    /**
     * 无法判断（人脸成像质量不好）
     */
    STATUS_FUZZY(2, "fuzzy", "无法判断"),
    /**
     * 正在检测
     */
    STATUS_DETECTING(3, "detecting", "正在检测"),
    ;


    private int status;

    private String statusEn;


    private String desc;

    EnumFaceAntiStatus(int status, String statusEn, String desc) {
        this.status = status;
        this.statusEn = statusEn;
        this.desc = desc;
    }

    /**
     * 获取
     *
     * @param status
     * @return
     */
    public static EnumFaceAntiStatus getFaceAntiStatus(Integer status) {
        if (null == status) {
            return null;
        }
        EnumFaceAntiStatus[] faceAntiStatuses = EnumFaceAntiStatus.values();
        for (EnumFaceAntiStatus faceAntiStatus : faceAntiStatuses) {
            if (faceAntiStatus.status == status.intValue()) {
                return faceAntiStatus;
            }
        }

        return null;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusEn() {
        return statusEn;
    }


    public String getDesc() {
        return desc;
    }

}
