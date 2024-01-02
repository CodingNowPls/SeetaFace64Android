package com.seetaface.v6;

import android.util.Log;

import com.seetaface.constant.SeetafaceModelConst;

/**
 * 口罩检测器
 * <p>
 * 口罩检测器根据输入的图像数据、人脸位置，返回是否佩戴口罩的检测结果。
 */
public class MaskDetector {

    private static final String TAG = "MaskDetector";


    static {
        System.loadLibrary("MaskDetector");
    }

    public MaskDetector() {
        String maskDetectorModel = SeetaUtils.getSeetaFaceModelPath(SeetafaceModelConst.MASK_DETECTOR_MODEL);
        int result = nativeCreateEngine(maskDetectorModel);
        Log.d(TAG, "-------gu--- createEngine result:" + result);
    }

    public void destroy() {
        int result = nativeDestroyEngine();
        Log.d(TAG, "-------gu--- destroyEngine result:" + result);
    }

    /**
     * 输入图像数据、人脸位置，返回是否佩戴口罩的检测结果。
     * sdk : detect
     *
     * @param imageData 原始图像数据
     * @param faceRect  人脸位置
     * @param scores    戴口罩的置信度
     * @return true 为佩戴了口罩
     */
    public boolean detect(SeetaImageData imageData, SeetaRect faceRect, float[] scores) {
        return nativeDetectMask(imageData, faceRect, scores);
    }


//-------------------------------------------------native 方法------------------------------------------------------------------------------


    private native int nativeCreateEngine(String detectorModel);

    private native int nativeDestroyEngine();

    /**
     * 输入图像数据、人脸位置，返回是否佩戴口罩的检测结果。
     * sdk : detect
     *
     * @param imageData 原始图像数据
     * @param faceRect  人脸位置
     * @param scores    戴口罩的置信度
     * @return true 为佩戴了口罩
     */
    private native boolean nativeDetectMask(SeetaImageData imageData, SeetaRect faceRect, float[] scores);
}
