package com.seetaface.v6;

import android.util.Log;

import com.seetaface.enums.EnumFaceAntiType;
import com.seetaface.constant.SeetafaceConst;
import com.seetaface.constant.SeetafaceModelConst;

/**
 * 活体识别器。
 * <p>
 * 说明：活体对象创建可以出入一个模型文件（局部活体模型）
 * 和两个模型文件（局部活体模型和全局活体模型，顺序不可颠倒），
 * 传入一个模型文件时活体识别速度快于传入两个模型文件的识别速度，
 * 传入一个模型文件时活体识别精度低于传入两个模型文件的识别精度。
 */
public class FaceAntiSpoofing {

    private static final String TAG = "FaceAntiSpoofing";


    static {
        System.loadLibrary("FaceAntiSpoofing");
    }

    /**
     * @param antiType EnumFaceAntiType 活体检测类型
     */
    public FaceAntiSpoofing(EnumFaceAntiType antiType) {
        String firstModel = null;
        String secondModel = null;
        if (antiType == EnumFaceAntiType.ANTI_TYPE_TOTALLY) {
            firstModel = SeetaUtils.getSeetaFaceModelPath(SeetafaceModelConst.MODEL_FIRST);
            secondModel = SeetaUtils.getSeetaFaceModelPath(SeetafaceModelConst.MODEL_SECOND);
        } else {
            firstModel = SeetaUtils.getSeetaFaceModelPath(SeetafaceModelConst.MODEL_FIRST);
        }
        int result = nativeCreateEngine(firstModel, secondModel);
        Log.d(TAG, "-------gu--- createEngine result:" + result);
        nativeSetProperty(SeetafaceConst.PROPERTY_NUMBER_THREADS, 1);
    }

    public void destroy() {
        int result = nativeDestroyEngine();
        Log.d(TAG, "-------活体gu--- destroyEngine result:" + result);
    }

    /**
     * 基于单帧图像对人脸是否为活体进行判断。
     *
     * @param imageData 原始图像数据
     * @param rect      人脸位置
     * @param pointFs   人脸特征点数组
     * @return EnumFaceAntiStatus
     * 说明：Status 活体状态可取值为
     * REAL(真人)、
     * SPOOF(假体)、
     * FUZZY（由于图像质量问题造成的无法判断）
     * 和 DETECTING（正在检测），
     * DETECTING 状态针对于 PredicVideo 模式。
     */
    public int predict(SeetaImageData imageData, SeetaRect rect, SeetaPointF[] pointFs) {
        return nativePredict(imageData, rect, pointFs);
    }


    /**
     * 基于连续视频序列对人脸是否为活体进行判断。
     *
     * @param imageData 原始图像数据
     * @param rect      人脸位置
     * @param pointFs   人脸特征点数组
     * @return 人脸活体的状态
     * <p>
     * 说明：Status 活体状态可取值为REAL(真人)、SPOOF(假体)、
     * FUZZY（由于图像质量问题造成的无法判断）和 DETECTING（正在检测），
     * DETECTING 状态针对于 PredicVideo 模式。
     */
    public int predictVideo(SeetaImageData imageData, SeetaRect rect, SeetaPointF[] pointFs) {
        return nativePredictVideo(imageData, rect, pointFs);
    }

    /**
     * 重置活体识别结果，开始下一次 PredictVideo 识别过程。
     */
    public void resetVideo() {
        nativeResetVideo();
    }

    /**
     * 获取活体检测内部分数。
     *
     * @param clarity 人脸清晰度分数
     * @param reality 人脸活体分数
     */
    public void getPreFrameScore(float[] clarity, float[] reality) {
        nativeGetPreFrameScore(clarity, reality);
    }


    /**
     * 获取video模式下活体需求帧数。
     *
     * @return
     */
    public int getVideoFrameCount() {
        return nativeGetVideoFrameCount();
    }

    /**
     * 设置 Video 模式中识别视频帧数，当输入帧数为该值以后才会有活体的
     * 真假结果。
     *
     * @param number
     */
    public void setVideoFrameCount(int number) {
        nativeSetVideoFrameCount(number);
    }

    /**
     * 说明：人脸清晰度阈值默认为0.3，人脸活体阈值为0.8。
     *
     * @param clarity 人脸清晰度阈值
     * @param reality 人脸活体阈值
     */
    public void setThreshold(float clarity, float reality) {
        nativeSetThreshold(clarity, reality);
    }

    /**
     * 获取阈值。
     *
     * @param clarity 人脸清晰度阈值
     * @param reality 人脸活体阈值
     */
    public void getThreshold(Float clarity, Float reality) {
        nativeGetThreshold(clarity, reality);
    }

    /**
     * 设置相关属性值。其中 PROPERTY_NUMBER_THREADS :
     * 表示计算线程数，默认为 4.
     *
     * @param property 属性类别
     * @param value    设置的属性值
     */
    public void setProperty(int property, double value) {
        nativeSetProperty(property, value);
    }

    /**
     * 获取相关属性值。
     *
     * @param type 属性类别
     * @return 对应的属性值
     */
    public double getProperty(int type) {
        return nativeGetProperty(type);
    }


//-------------------------------------------------native 方法------------------------------------------------------------------------------


    private native int nativeCreateEngine(String firstModel, String secondModel);

    private native int nativeDestroyEngine();

    /**
     * 基于单帧图像对人脸是否为活体进行判断。
     * sdk: Predict
     *
     * @param imageData 原始图像数据
     * @param rect      人脸位置
     * @param pointFs   人脸特征点数组
     * @return 人脸活体的状态
     * <p>
     * 说明：Status 活体状态可取值为
     * REAL(真人)、
     * SPOOF(假体)、
     * FUZZY（由于图像质量问题造成的无法判断）
     * 和 DETECTING（正在检测），DETECTING 状态针对于 PredicVideo 模式。
     */
    private native int nativePredict(SeetaImageData imageData, SeetaRect rect, SeetaPointF[] pointFs);

    /**
     * 基于连续视频序列对人脸是否为活体进行判断。
     * sdk: PredictVideo
     *
     * @param imageData 原始图像数据
     * @param rect      人脸位置
     * @param pointFs   人脸特征点数组
     * @return 人脸活体的状态
     * <p>
     * 说明：Status 活体状态可取值为REAL(真人)、SPOOF(假体)、
     * FUZZY（由于图像质量问题造成的无法判断）和 DETECTING（正在检测），
     * DETECTING 状态针对于 PredicVideo 模式。
     */
    private native int nativePredictVideo(SeetaImageData imageData, SeetaRect rect, SeetaPointF[] pointFs);

    /**
     * 重置活体识别结果，开始下一次 PredictVideo 识别过程。
     * sdk: ResetVideo
     */
    private native void nativeResetVideo();

    /**
     * 获取活体检测内部分数。
     * sdk: GetPreFrameScore
     *
     * @param clarity 人脸清晰度分数
     * @param reality 人脸活体分数
     */
    private native void nativeGetPreFrameScore(float[] clarity, float[] reality);

    /**
     * 设置 Video 模式中识别视频帧数，当输入帧数为该值以后才会有活体的
     * 真假结果。
     * sdk:SetVideoFrameCount
     *
     * @param number
     */
    private native void nativeSetVideoFrameCount(int number);


    /**
     * 获取video模式下活体需求帧数。
     * sdk: GetVideoFrameCount
     *
     * @return
     */
    private native int nativeGetVideoFrameCount();

    /**
     * 说明：人脸清晰度阈值默认为0.3，人脸活体阈值为0.8。
     *
     * @param clarity 人脸清晰度阈值
     * @param reality 人脸活体阈值
     */
    private native void nativeSetThreshold(float clarity, float reality);

    /**
     * 获取阈值。
     *
     * @param clarity 人脸清晰度阈值
     * @param reality 人脸活体阈值
     */
    private native void nativeGetThreshold(Float clarity, Float reality);

    /**
     * 设置相关属性值。其中 PROPERTY_NUMBER_THREADS :
     * 表示计算线程数，默认为 4.
     *
     * @param type  属性类别
     * @param value 设置的属性值
     */
    private native void nativeSetProperty(int type, double value);

    /**
     * 获取相关属性值。
     *
     * @param type 属性类别
     * @return 对应的属性值
     */
    private native double nativeGetProperty(int type);
}
