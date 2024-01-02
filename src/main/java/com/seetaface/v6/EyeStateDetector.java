package com.seetaface.v6;

import android.util.Log;

import com.seetaface.constant.SeetafaceConst;
import com.seetaface.constant.SeetafaceModelConst;

/**
 * 眼睛状态检测器
 * <p>
 * <p>
 * 眼睛检测器要求输入原始图像数据和人脸特征点，返回左眼和右眼的状态。
 */
public class EyeStateDetector {

    private static final String TAG = "EyeStateDetector";


    static {
        System.loadLibrary("EyeStateDetector");
    }

    public EyeStateDetector() {
        String eyeStateModel = SeetaUtils.getSeetaFaceModelPath(SeetafaceModelConst.MODEL_EYE_STATE);
        int result = nativeCreateEngine(eyeStateModel);
        Log.d(TAG, "-------gu--- createEngine result:" + result);
        nativeSetProperty(SeetafaceConst.PROPERTY_NUMBER_THREADS, 1);
    }

    public void destroy() {
        int result = nativeDestroyEngine();
        Log.d(TAG, "-------gu--- destroyEngine result:" + result);
    }

    /**
     * 输入原始图像数据和人脸特征点，返回左眼和右眼的状态。
     * sdk : Detect
     *
     * @param imageData  原始图像数据
     * @param pointFs    人脸特征点数组
     * @param leftState  返回的左眼状态
     * @param rightState 返回的右眼状态
     *                   <p>
     *                   说明：EYE_STATE可取值为
     *                   EYE_CLOSE（闭眼）、
     *                   EYE_OPEN（睁眼）、
     *                   EYE_RANDOM（非眼部区域）、
     *                   EYE_UNKNOWN（未知状态）。
     *                   枚举 EnumEyeState
     */
    public void detect(SeetaImageData imageData, SeetaPointF[] pointFs,
                       Integer leftState, Integer rightState) {
        nativeEyeStateDetect(imageData, pointFs, leftState, rightState);
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

    private native int nativeCreateEngine(String eyeStateModel);

    private native int nativeDestroyEngine();

    /**
     * 输入原始图像数据和人脸特征点，返回左眼和右眼的状态。
     * sdk : Detect
     *
     * @param imageData  原始图像数据
     * @param pointFs    人脸特征点数组
     * @param leftState  返回的左眼状态
     * @param rightState 返回的右眼状态
     *                   <p>
     *                   说明：EYE_STATE可取值为
     *                   EYE_CLOSE（闭眼）、
     *                   EYE_OPEN（睁眼）、
     *                   EYE_RANDOM（非眼部区域）、
     *                   EYE_UNKNOWN（未知状态）。
     *                   EnumEyeState
     */
    private native void nativeEyeStateDetect(SeetaImageData imageData, SeetaPointF[] pointFs,
                                             Integer leftState, Integer rightState);

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
