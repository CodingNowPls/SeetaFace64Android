package com.seetaface.v6;

import android.util.Log;

import com.seetaface.constant.SeetafaceConst;
import com.seetaface.constant.SeetafaceModelConst;

/**
 * 人脸检测器。
 */
public class FaceDetector {
    private static final String TAG = "FaceDetector";


    static {
        System.loadLibrary("FaceDetector");
    }

    public FaceDetector() {
        String detectorModel = SeetaUtils.getSeetaFaceModelPath(SeetafaceModelConst.DETECTOR_FACE_MODEL);
        int result = nativeCreateEngine(detectorModel);
        Log.d(TAG, "-------gu--- createEngine result:" + result);

        // set default property
        setProperty(SeetafaceConst.PROPERTY_MIN_FACE_SIZE, 50);
        setProperty(SeetafaceConst.PROPERTY_THRESHOLD, 0.9f);
        setProperty(SeetafaceConst.PROPERTY_NUMBER_THREADS, 1);

        Log.d(TAG, "-------gu--- default_max_width:" + getDetectorProperty(SeetafaceConst.PROPERTY_MAX_IMAGE_WIDTH));
        Log.d(TAG, "-------gu--- default_max_height:" + getDetectorProperty(SeetafaceConst.PROPERTY_MAX_IMAGE_HEIGHT));
    }

    public void destroy() {
        int result = nativeDestroyEngine();
        Log.d(TAG, "-------gu--- destroyEngine result:" + result);
    }

    /**
     * 输入彩色图像，检测其中的人脸
     *
     * @param image 输入的图像数据
     * @return 人脸信息数组
     */
    public SeetaFaceInfo[] detect(SeetaImageData image) {
        return nativeDetectFaces(image);
    }

    /**
     * 设置人脸检测器相关属性值。其中
     * PROPERTY_MIN_FACE_SIZE: 表示人脸检测器可以检测到的最小人脸，该值越小，支持检测到的人脸尺寸越小，检测速度越慢，默认值为20；
     * PROPERTY_THRESHOLD:
     * 表示人脸检测器过滤阈值，默认为 0.90；
     * PROPERTY_MAX_IMAGE_WIDTH  和 PROPERTY_MAX_IMAGE_HEIGHT:
     * 分别表示支持输入的图像的最大宽度和高度；
     * PROPERTY_NUMBER_THREADS:
     * 表示人脸检测器计算线程数，默认为 4.
     *
     * @param property 人脸检测器属性类别
     * @param value    设置的属性值
     */
    public void setProperty(int property, double value) {
        nativeSetProperty(property, value);
    }

    /**
     * 获取人脸检测器相关属性值。
     *
     * @param type
     * @return
     */
    public double getDetectorProperty(int type) {
        return nativeGetProperty(type);
    }


//-------------------------------------------------native 方法------------------------------------------------------------------------------


    private native int nativeCreateEngine(String detectorModel);

    private native int nativeDestroyEngine();

    /**
     * 输入彩色图像，检测其中的人脸
     * sdk: detect
     *
     * @param image 输入的图像数据
     * @return 人脸信息数组
     */
    private native SeetaFaceInfo[] nativeDetectFaces(SeetaImageData image);

    /**
     * 设置人脸检测器相关属性值。其中
     * PROPERTY_MIN_FACE_SIZE: 表示人脸检测器可以检测到的最小人脸，该值越小，支持检测到的人脸尺寸越小，检测速度越慢，默认值为20；
     * PROPERTY_THRESHOLD:
     * 表示人脸检测器过滤阈值，默认为 0.90；
     * PROPERTY_MAX_IMAGE_WIDTH  和 PROPERTY_MAX_IMAGE_HEIGHT:
     * 分别表示支持输入的图像的最大宽度和高度；
     * PROPERTY_NUMBER_THREADS:
     * 表示人脸检测器计算线程数，默认为 4.
     *
     * @param type  人脸检测器属性类别
     * @param value 设置的属性值
     */
    private native void nativeSetProperty(int type, double value);

    /**
     * 获取人脸检测器相关属性值。
     *
     * @param type
     * @return
     */
    private native double nativeGetProperty(int type);


}
