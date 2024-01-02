package com.seetaface.enums;

import android.graphics.Bitmap;

/**
 * 图片类型
 */
public enum EnumImgType {
    JPG("jpg", ".jpg", Bitmap.CompressFormat.JPEG),
    PNG("png", ".png", Bitmap.CompressFormat.PNG),

    ;

    private String type;
    private String pointType;
    private Bitmap.CompressFormat compressFormat;


    EnumImgType(String type, String pointType, Bitmap.CompressFormat compressFormat) {
        this.type = type;
        this.pointType = pointType;
        this.compressFormat = compressFormat;
    }

    public String getType() {
        return type;
    }

    public Bitmap.CompressFormat getCompressFormat() {
        return compressFormat;
    }

    public String getPointType() {
        return pointType;
    }
}
