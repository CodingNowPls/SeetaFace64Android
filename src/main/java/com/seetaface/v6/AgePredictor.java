package com.seetaface.v6;

import android.util.Log;

import com.seetaface.constant.SeetafaceConst;
import com.seetaface.constant.SeetafaceModelConst;

/**
 * 年龄估计器
 * <p>
 * 年龄估计器要求输入原始图像数据和人脸特征点（或者裁剪好的人脸数据），对输入的人脸进行年龄估计。
 */
public class AgePredictor {

    private static final String TAG = "AgePredictor";


    static {
        System.loadLibrary("AgePredictor");
    }

    public AgePredictor() {
        String agePredictorModel = SeetaUtils.getSeetaFaceModelPath(SeetafaceModelConst.MODEL_AGE_PREDICTOR);
        int result = nativeCreateEngine(agePredictorModel);
        Log.d(TAG, "-------gu--- createEngine result:" + result);

        nativeSetProperty(SeetafaceConst.PROPERTY_NUMBER_THREADS, 1);
    }

    public void destroy() {
        int result = nativeDestroyEngine();
        Log.d(TAG, "-------gu--- destroyEngine result:" + result);
    }


    /**
     * 获取裁剪人脸的宽度。
     * sdk : GetCropFaceWidth
     *
     * @return 返回的人脸宽度
     */
    public int getCropFaceWidth() {
        return nativeGetCropFaceWidth();
    }

    /**
     * 获取裁剪的人脸高度。
     * sdk: GetCropFaceHeight
     *
     * @return
     */
    public int getCropFaceHeight() {
        return nativeGetCropFaceHeight();
    }

    /**
     * 获取裁剪的人脸数据通道数。
     * sdk: GetCropFaceChannels
     *
     * @return
     */
    public int getCropFaceChannels() {
        return nativeGetCropFaceChannels();
    }

    /**
     * 裁剪人脸。
     * sdk : CropFace
     *
     * @param image   原始图像数据
     * @param pointFs 人脸特征点数组
     * @param crop    返回的裁剪人脸
     * @return true 表示人脸裁剪成功
     */
    public boolean cropFace(SeetaImageData image, SeetaPointF[] pointFs, SeetaImageData crop) {
        return nativeCropFace(image, pointFs, crop);
    }

    /**
     * 输入裁剪好的人脸，返回估计的年龄。
     * sdk: PredictAge
     *
     * @param image 裁剪好的人脸数据
     * @return 估计的年龄
     */
    public int predictAge(SeetaImageData image) {
        return nativePredictAge(image);
    }

    /**
     * 输入原始图像数据和人脸特征点，返回估计的年龄。
     * sdk: PredictAgeWithCrop
     *
     * @param image   原始人脸数据
     * @param pointFs 人脸特征点
     * @return 估计的年龄
     */
    public int predictAgeWithCrop(SeetaImageData image, SeetaPointF[] pointFs) {
        return nativePredictAgeWithCrop(image, pointFs);
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

    private native int nativeCreateEngine(String agePredictorModel);

    private native int nativeDestroyEngine();

    /**
     * 获取裁剪人脸的宽度。
     * sdk : GetCropFaceWidth
     *
     * @return 返回的人脸宽度
     */
    private native int nativeGetCropFaceWidth();

    /**
     * 获取裁剪的人脸高度。
     * sdk: GetCropFaceHeight
     *
     * @return
     */
    private native int nativeGetCropFaceHeight();

    /**
     * 获取裁剪的人脸数据通道数。
     * sdk: GetCropFaceChannels
     *
     * @return
     */
    private native int nativeGetCropFaceChannels();

    /**
     * 裁剪人脸。
     * sdk : CropFace
     *
     * @param image   原始图像数据
     * @param pointFs 人脸特征点数组
     * @param crop    返回的裁剪人脸
     * @return true 表示人脸裁剪成功
     */
    private native boolean nativeCropFace(SeetaImageData image, SeetaPointF[] pointFs, SeetaImageData crop);

    /**
     * 输入裁剪好的人脸，返回估计的年龄。
     * sdk: PredictAge
     *
     * @param image 裁剪好的人脸数据
     * @return 估计的年龄
     */
    private native int nativePredictAge(SeetaImageData image);

    /**
     * 输入原始图像数据和人脸特征点，返回估计的年龄。
     * sdk: PredictAgeWithCrop
     *
     * @param image   原始人脸数据
     * @param pointFs 人脸特征点
     * @return 估计的年龄
     */
    private native int nativePredictAgeWithCrop(SeetaImageData image, SeetaPointF[] pointFs);

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
