package com.config;


import android.hardware.Camera;

public enum EnumCamera {

    /**
     * 手机前置
     */
    MOBILE_FACE(EnumCamera.CAMERA_FACING, 90, "手机前置"),
    /**
     * 手机后置
     */
    MOBILE_BACK(EnumCamera.CAMERA_BACK, 90, "手机后置"),
    ;


    EnumCamera(int cameraId, int rotation, String desc) {
        this.cameraId = cameraId;
        this.rotation = rotation;
        this.desc = desc;
    }

    public int getCameraId() {
        return cameraId;
    }

    public int getRotation() {
        return rotation;
    }


    /**
     * 相机前后摄像头的ID
     */
    private int cameraId;
    /**
     * 摄像机人像旋转角度
     */
    private int rotation;
    /**
     * 配置描述
     */
    private String desc;

    /**
     * 摄像头ID
     * 前置摄像头 :1
     *
     * 机器后置摄像头 :0
     */
    /**
     * 前置摄像头 :1
     */
    public static final int CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_FRONT;
    /**
     * 机器后置摄像头 :0
     */
    public static final int CAMERA_BACK = Camera.CameraInfo.CAMERA_FACING_BACK;


}
