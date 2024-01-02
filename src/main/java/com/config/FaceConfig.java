package com.config;

import com.seetaface.enums.EnumFaceAntiType;

public class FaceConfig {


    /**
     * 是否是debug
     */
    public static final boolean DEBUG_MODE = false;
    /**
     * 画追踪框
     */
    public static final boolean DRAW_FACE_TRACK_MODE = false;
    /**
     * 活体检测模式
     */
    public static final boolean FACE_ANTI_SPOOFING_MODE = false;


    /**
     * 线程池总线程数
     */
    public static final int threadTotalNum = 20;
    /**
     * 每个线程处理的图片任务数
     */
    public static final int threadDealTaskNum = 30;


    /**
     * 人脸相似度
     */
    public static final float FACE_SIMILARITY_DEGREE = 0.8f;

    /**
     * 检测器阈值默认值是0.9，合理范围为[0, 1]。这个值一般不进行调整，
     * 除了用来处理一些极端情况。这个值设置的越小，漏检的概率越小，同时误检的概率会提高；
     */
    public static final float PROPERTY_THRESHOLD_VALUE = 0.9f;
    /**
     * 最小人脸是人脸检测器常用的一个概念，默认值为20，单位像素。它表示了在一个输入图片上可以检测到的最小人脸尺度，
     * 注意这个尺度并非严格的像素值，例如设置最小人脸80，检测到了宽度为75的人脸是正常的，这个值是给出检测能力的下限。
     * <p>
     * 最小人脸和检测器性能息息相关。主要方面是速度，使用建议上，我们建议在应用范围内，这个值设定的越大越好。
     * SeetaFace采用的是BindingBox Regresion的方式训练的检测器。如果最小人脸参数设置为80的话，
     * 从检测能力上，可以将原图缩小的原来的1/4，这样从计算复杂度上，能够比最小人脸设置为20时，提速到16倍。
     */
    public static final int PROPERTY_MIN_FACE_SIZE_VALUE = 100;


    public static final int CAMERA_PREVIEW_WIDTH = 640;
    public static final int CAMERA_PREVIEW_HEIGHT = 480;

    public static final int IMAGE_WIDTH = 480;
    public static final int IMAGE_HEIGHT = 640;
    /**
     * 1是前置 0是后置  枚举类  Camera.CameraInfo.CAMERA_FACING_FRONT
     * Camera.CameraInfo.CAMERA_FACING_FRONT   1
     * <p>
     * Camera.CameraInfo.CAMERA_FACING_BACK   0
     */

    public static final EnumCamera CAMERA_ID = EnumCamera.MOBILE_FACE;
    /**
     * 活体检测类型    ANTI_TYPE_PARTIAL  0:局部活体检测   ANTI_TYPE_TOTALLY  1:全局活体检测
     */
    public static final EnumFaceAntiType ANTI_TYPE = EnumFaceAntiType.ANTI_TYPE_TOTALLY;


}
