package com.seetaface.v6;

import android.util.Log;

import com.seetaface.constant.SeetafaceModelConst;

/**
 * 人脸跟踪器。
 * <p>
 * <p>
 * 人脸跟踪器会对输入的彩色图像或者灰度图像中的人脸进行跟踪，并返回所有跟踪到的人脸信息。
 */
public class FaceTracker {

    private static final String TAG = "FaceTracker";


    static {
        System.loadLibrary("FaceAntiSpoofing");
    }

    /**
     * 构造函数
     *
     * @param width  视频的宽度
     * @param height 视频的高度
     */
    public FaceTracker(int width, int height) {
        String trackerModel = SeetaUtils.getSeetaFaceModelPath(SeetafaceModelConst.MODEL_TRACKER);
        int result = nativeCreateEngine(trackerModel, width, height);
        Log.d(TAG, "-------gu--- createEngine result:" + result);
    }

    public void destroy() {
        int result = nativeDestroyEngine();
        Log.d(TAG, "-------gu--- destroyEngine result:" + result);
    }


    /**
     * 设置底层的计算线程数量。
     * sdk: SetSingleCalculationThreads
     *
     * @param num 线程数量
     */
    public void setSingleCalculationThreads(int num) {
        nativeSetSingleCalculationThreads(num);
    }

    /**
     * 设置检测器的最小人脸大小。
     * sdk : SetMinFaceSize
     *
     * @param size 最小人脸大小
     *             说明：size 的大小保证大于等于 20，size的值越小，能够检测到的人脸的尺寸越小，
     *             检测速度越慢。
     */
    public void setMinFaceSize(int size) {
        nativeSetMinFaceSize(size);
    }

    /**
     * 获取最小人脸的大小。
     * sdk : GetMinFaceSize
     *
     * @return 最小人脸大小
     */
    public int getMinFaceSize() {
        return nativeGetMinFaceSize();
    }

    /**
     * 设置检测器的检测阈值。
     * <p>
     * sdk: SetThreshold
     *
     * @param thresh 检测阈值
     */
    public void setThreshold(float thresh) {
        nativeSetThreshold(thresh);
    }

    /**
     * 获取检测器检测阈值。
     * sdk: GetScoreThreshold
     *
     * @return 检测阈值
     */
    public float getScoreThreshold() {
        return nativeGetScoreThreshold();
    }

    /**
     * 设置以稳定模式输出人脸跟踪结果。
     *
     * @param stable 是否是稳定模式
     *               说明：只有在视频中连续跟踪时，才使用此方法。
     */
    public void setVideoStable(boolean stable) {
        nativeSetVideoStable(stable);
    }

    /**
     * 获取当前是否是稳定工作模式。
     * sdk : GetVideoStable
     *
     * @return 是否是稳定模式
     */
    public boolean getVideoStable() {
        return nativeGetVideoStable();
    }

    /**
     * 对视频帧中的人脸进行跟踪。
     * sdk : Track
     *
     * @param image 原始图像数据
     * @return 跟踪到的人脸信息数组
     */
    public SeetaTrackingFaceInfo[] track(SeetaImageData image) {
        return nativeTrack(image);
    }

    /**
     * 对视频帧中的人脸进行跟踪。
     * <p>
     * sdk : Track
     *
     * @param image      原始图像数据
     * @param frameIndex 视频帧索引
     * @return 跟踪到的人脸信息数组
     */
    public SeetaTrackingFaceInfo[] track(SeetaImageData image, int frameIndex) {
        return nativeTrack(image, frameIndex);
    }

//-------------------------------------------------native 方法------------------------------------------------------------------------------

    /**
     * @param trackerModel
     * @param width
     * @param height
     * @return
     */
    private native int nativeCreateEngine(String trackerModel, int width, int height);

    private native int nativeDestroyEngine();

    /**
     * 设置底层的计算线程数量。
     * sdk: SetSingleCalculationThreads
     *
     * @param num 线程数量
     */
    private native void nativeSetSingleCalculationThreads(int num);

    /**
     * 设置检测器的最小人脸大小。
     * sdk : SetMinFaceSize
     *
     * @param size 最小人脸大小
     *             说明：size 的大小保证大于等于 20，size的值越小，能够检测到的人脸的尺寸越小，
     *             检测速度越慢。
     */
    private native void nativeSetMinFaceSize(int size);

    /**
     * 获取最小人脸的大小。
     * sdk : GetMinFaceSize
     *
     * @return 最小人脸大小
     */
    private native int nativeGetMinFaceSize();

    /**
     * 设置检测器的检测阈值。
     * <p>
     * sdk: SetThreshold
     *
     * @param thresh 检测阈值
     */
    private native void nativeSetThreshold(float thresh);

    /**
     * 获取检测器检测阈值。
     * sdk: GetScoreThreshold
     *
     * @return 检测阈值
     */
    private native float nativeGetScoreThreshold();

    /**
     * 设置以稳定模式输出人脸跟踪结果。
     *
     * @param stable 是否是稳定模式
     *               说明：只有在视频中连续跟踪时，才使用此方法。
     */
    private native void nativeSetVideoStable(boolean stable);

    /**
     * 获取当前是否是稳定工作模式。
     * sdk : GetVideoStable
     *
     * @return 是否是稳定模式
     */
    private native boolean nativeGetVideoStable();

    /**
     * 对视频帧中的人脸进行跟踪。
     * sdk : Track
     *
     * @param image 原始图像数据
     * @return 跟踪到的人脸信息数组
     */
    private native SeetaTrackingFaceInfo[] nativeTrack(SeetaImageData image);

    /**
     * 对视频帧中的人脸进行跟踪。
     * <p>
     * sdk : Track
     *
     * @param image      原始图像数据
     * @param frameIndex 视频帧索引
     * @return 跟踪到的人脸信息数组
     */
    private native SeetaTrackingFaceInfo[] nativeTrack(SeetaImageData image, int frameIndex);
}
