package com.seetaface.v6;

import android.util.Log;

/**
 * 特征点检测器
 * <p>
 * 人脸特征点检测器要求输入原始图像数据和人脸位置，
 * 返回人脸 5 个或者其他数量的的特征点的坐标（特征点的数量和加载的模型有关）
 */
public class FaceLandMarker {

    private static final String TAG = "FaceLandMarker";


    static {
        System.loadLibrary("FaceLandMarker");
    }

    /**
     * @param model 模型名称全称  如：
     *              MODEL_LAND_MARKER_PTS5
     *              MODEL_LAND_MARKER_PTS68
     *              MODEL_LAND_MARKER_MASK_PTS5
     *              <p>
     *              pts5 有5个检测点      pts68有68个检测点
     */
    public FaceLandMarker(String model) {
        String landMarksModel = SeetaUtils.getSeetaFaceModelPath(model);
        int result = nativeCreateEngine(landMarksModel);
        Log.d(TAG, "-------gu--- createEngine result:" + result);
    }

    public void destroy() {
        int result = nativeDestroyEngine();
        Log.d(TAG, "-------gu--- destroyEngine result:" + result);
    }

    /**
     * 获取模型对应的特征点数组长度。
     *
     * @return
     */
    public int number() {
        return nativeGetPointsLength();
    }

    /**
     * 获取人脸特征点。
     *
     * @param image 图像原始数据
     * @param rect  人脸位置
     * @return 获取的人脸特征点数组
     */
    public SeetaPointF[] mark(SeetaImageData image, SeetaRect rect) {
        return nativeGetFacePoints(image, rect);
    }

    /**
     * 获取人脸特征点。
     *
     * @param image  图像原始数据
     * @param rect   人脸位置
     * @param points 获取的人脸特征点数组(需预分配好数组长度，长度为number()返回的值)
     */
    public void mark(SeetaImageData image, SeetaRect rect, SeetaPointF[] points) {
        nativeGetFacePoints(image, rect, points);
    }

    /**
     * 获取人脸特征点和遮挡信息。
     *
     * @param image 图像原始数据
     * @param rect  人脸位置
     * @return
     */
    public SeetaPointsWithMask[] markMask(SeetaImageData image, SeetaRect rect) {
        return nativeGetFacePointsWithMask(image, rect);
    }

    /**
     * 获取人脸特征点和遮挡信息。
     *
     * @param image         图像原始数据
     * @param rect          人脸位置
     * @param pointWithMask 获取带的口罩人脸特征点数组(需预分配好数组长度，长度为number()返回的值)
     */
    public void markMask(SeetaImageData image, SeetaRect rect, SeetaPointsWithMask[] pointWithMask) {
        nativeGetFacePointsWithMask(image, rect, pointWithMask);
    }


//-------------------------------------------------native 方法------------------------------------------------------------------------------

    private native int nativeCreateEngine(String landMarksModel);

    private native int nativeDestroyEngine();

    /**
     * 获取脸部特征点长度
     *
     * @return
     */
    private native int nativeGetPointsLength();

    /**
     * 获取人脸特征点。
     * sdk: mark
     *
     * @param image 图像原始数据
     * @param rect  人脸位置
     * @return 获取的人脸特征点数组
     */
    private native SeetaPointF[] nativeGetFacePoints(SeetaImageData image, SeetaRect rect);

    /**
     * 获取人脸特征点。
     * sdk: mark
     *
     * @param image   图像原始数据
     * @param rect    人脸位置
     * @param pointFS 获取的人脸特征点数组(需预分配好数组长度，长度为number()返回的值)
     */
    private native void nativeGetFacePoints(SeetaImageData image, SeetaRect rect, SeetaPointF[] pointFS);

    /**
     * 获取人脸特征点和遮挡信息。
     * sdk:  mark_v2
     *
     * @param image 图像原始数据
     * @param rect  人脸位置
     * @return
     */
    private native SeetaPointsWithMask[] nativeGetFacePointsWithMask(SeetaImageData image, SeetaRect rect);

    /**
     * 获取人脸特征点和遮挡信息。
     * sdk: mark_v2
     *
     * @param image         图像原始数据
     * @param rect          人脸位置
     * @param pointWithMask 获取带的口罩人脸特征点数组(需预分配好数组长度，长度为number()返回的值)
     */
    private native void nativeGetFacePointsWithMask(SeetaImageData image, SeetaRect rect, SeetaPointsWithMask[] pointWithMask);
}
