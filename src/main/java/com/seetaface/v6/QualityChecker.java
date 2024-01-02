package com.seetaface.v6;

/**
 * 质量评估器
 * <p>
 * <p>
 * 质量评估器包含不同的质量评估模块，包括人脸亮度、人脸清晰度（非深度方法）、
 * 人脸清晰度（深度方法）、人脸姿态（非深度方法）、人脸姿态（深度方法）、人脸分辨率和人脸完整度评估模块。
 */
public class QualityChecker {
    /**
     * 非深度学习的人脸亮度评估器
     */
    public static final int QUALITY_BRIGHTNESS = 0;
    /**
     * 非深度学习的人脸清晰度评估器
     */
    public static final int QUALITY_CLARITY = 1;
    /**
     * 深度学习的人脸清晰度评估器
     */
    public static final int QUALITY_LBN = 2;
    /**
     * 非深度学习的人脸姿态评估器
     */
    public static final int QUALITY_POSE = 3;
    /**
     * 深度学习的人脸姿态评估器
     */
    public static final int QUALITY_POSE_EX = 4;
    /**
     * 非深度学习的人脸尺寸评估器
     */
    public static final int QUALITY_RESOLUTION = 5;

    // LBN

    public static final int LBN_PROPERTY_NUMBER_THREADS = 4;
    public static final int LBN_PROPERTY_ARM_CPU_MODE = 5;
    public static final int LBN_PROPERTY_LIGHT_THRESH = 10;
    public static final int LBN_PROPERTY_BLUR_THRESH = 11;
    public static final int LBN_PROPERTY_NOISE_THRESH = 12;

    public static final int LBN_LIGHT_BRIGHT = 0;
    public static final int LBN_LIGHT_DARK = 1;
    public static final int LBN_BLUR_CLEAR = 0;
    public static final int LBN_BLUR_BLUR = 1;
    public static final int LBN_NOISE_HAVE = 0;
    public static final int LBN_NOISE_NO = 1;

    // pose ex

    public static final int POSE_EX_PROPERTY_YAW_LOW_THRESHOLD = 0;
    public static final int POSE_EX_PROPERTY_YAW_HIGH_THRESHOLD = 1;
    public static final int POSE_EX_PROPERTY_PITCH_LOW_THRESHOLD = 2;
    public static final int POSE_EX_PROPERTY_PITCH_HIGH_THRESHOLD = 3;
    public static final int POSE_EX_PROPERTY_ROLL_LOW_THRESHOLD = 4;
    public static final int POSE_EX_PROPERTY_ROLL_HIGH_THRESHOLD = 5;

    static {
        System.loadLibrary("QualityChecker");
    }

    public QualityChecker() {
    }


//-------------------------------------------------native 方法------------------------------------------------------------------------------

    /**
     * 亮度评估
     *
     * @return
     */
    private native int nativeStartBrightnessChecker();

    /**
     * 开始亮度评估
     *
     * @param v0
     * @param v1
     * @param v2
     * @param v3
     * @return
     */
    private native int nativeStartBrightnessChecker(float v0, float v1, float v2, float v3);

    /**
     * 亮度评估
     *
     * @param imageData
     * @param rect
     * @param pointFs
     * @param featureSize
     * @return
     */
    private native QualityResult nativeBrightnessCheck(SeetaImageData imageData, SeetaRect rect,
                                                       SeetaPointF[] pointFs, int featureSize);

    /**
     * 结束亮度评估
     *
     * @return
     */
    private native int nativeStopBrightnessChecker();

    /**
     * 开始 非深度学习的清晰度评估
     *
     * @return
     */
    private native int nativeStartClarityChecker();

    /**
     * 开始 非深度学习的清晰度评估
     *
     * @param low
     * @param high
     * @return
     */
    private native int nativeStartClarityChecker(float low, float high);

    private native QualityResult nativeClarityCheck(SeetaImageData imageData, SeetaRect rect,
                                                    SeetaPointF[] pointFs, int featureSize);

    /**
     * 结束非深度学习的清晰度评估
     *
     * @return
     */
    private native int nativeStopClarityChecker();


    /**
     * 开始 深度学习的人脸清晰度评估
     *
     * @param model
     * @return
     */
    private native int nativeStartLBNChecker(String model);

    /**
     * 深度学习的人脸清晰度检测
     *
     * @param imageData
     * @param pointFs
     * @param light
     * @param blur
     * @param noise
     */
    private native void nativeLBNDetect(SeetaImageData imageData, SeetaPointF[] pointFs,
                                        int[] light, int[] blur, int[] noise);

    /**
     * @param type
     * @param value
     */
    private native void nativeLBNSetProperty(int type, double value);

    /**
     * @param type
     * @return
     */
    private native double nativeLBNGetProperty(int type);

    /**
     * @return
     */
    private native int nativeStopLBNChecker();


    /**
     * 非深度学习的人脸姿态评估
     *
     * @param imageData
     * @param rect
     * @param pointFs
     * @param featureSize
     * @return
     */
    private native QualityResult nativePoseCheck(SeetaImageData imageData, SeetaRect rect,
                                                 SeetaPointF[] pointFs, int featureSize);


    /**
     * 深度学习的人脸姿态评估
     *
     * @param model
     * @return
     */
    private native int nativeStartPoseExChecker(String model);

    /**
     * 深度学习的人脸姿态评估
     *
     * @param imageData
     * @param rect
     * @param pointFs
     * @param featureSize
     * @return
     */
    private native QualityResult nativePoseExCheck(SeetaImageData imageData, SeetaRect rect,
                                                   SeetaPointF[] pointFs, int featureSize);

    /**
     * @param type
     * @param value
     */
    private native void nativePoseExSetProperty(int type, float value);

    /**
     * @param type
     * @return
     */
    private native float nativePoseExGetProperty(int type);

    /**
     * @return
     */
    private native int nativeStopPoseExChecker();


    /**
     * 非深度学习的人脸尺寸评估器
     *
     * @return
     */
    private native int nativeStartResolutionChecker();

    /**
     * 非深度学习的人脸尺寸评估器
     *
     * @param low
     * @param high
     * @return
     */
    private native int nativeStartResolutionChecker(float low, float high);

    /**
     * 非深度学习的人脸尺寸评估器
     *
     * @param imageData
     * @param rect
     * @param pointFs
     * @param featureSize
     * @return
     */
    private native QualityResult nativeResolutionCheck(SeetaImageData imageData, SeetaRect rect,
                                                       SeetaPointF[] pointFs, int featureSize);

    /**
     * 非深度学习的人脸尺寸评估器
     *
     * @return
     */
    private native int nativeStopResolutionChecker();


    /**
     * 完整性检测
     *
     * @return
     */
    private native int nativeStartIntegrityChecker();

    /**
     * 完整性检测
     *
     * @param low
     * @param high
     * @return
     */
    private native int nativeStartIntegrityChecker(float low, float high);

    /**
     * 完整性检测
     *
     * @param imageData
     * @param rect
     * @param pointFs
     * @param featureSize
     * @return
     */
    private native QualityResult nativeIntegrityCheck(SeetaImageData imageData, SeetaRect rect,
                                                      SeetaPointF[] pointFs, int featureSize);

    /**
     * 完整性检测
     *
     * @return
     */
    private native int nativeStopIntegrityChecker();
}
