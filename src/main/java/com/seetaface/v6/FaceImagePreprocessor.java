package com.seetaface.v6;

import android.graphics.Bitmap;

/**
 *
 */
public class FaceImagePreprocessor {

    static {
        System.loadLibrary("FacePreprocessor");
    }

    private static FaceImagePreprocessor mInstance;

    private FaceImagePreprocessor() {
    }

    public static FaceImagePreprocessor getInstance() {
        if (mInstance == null) {
            mInstance = new FaceImagePreprocessor();
        }
        return mInstance;
    }

    public SeetaImageData getImageDataFromBitmap(Bitmap bitmap) {
        return processBitmap(bitmap);
    }

    /**
     * camera1 NV21 {@link android.graphics.ImageFormat#NV21}
     *
     * @param yuv
     * @param width
     * @param height
     * @param format 0:NV21
     * @return
     */
    public SeetaImageData getImageDataFromYUVData(byte[] yuv, int width, int height, int format) {
        return processYUVData(yuv, width, height, format);
    }

    /**
     * camera2 YUV_420_888 {@link android.graphics.ImageFormat#YUV_420_888}
     *
     * @param y
     * @param u
     * @param v
     * @param width
     * @param height
     * @return
     */
    public SeetaImageData getImageDataFromYUVData(byte[] y, byte[] u, byte[] v, int width, int height) {
        return processSplitYUVData(y, u, v, width, height);
    }


//-------------------------------------------------native 方法------------------------------------------------------------------------------

    /**
     * @param originBitmap
     * @return
     */
    private native SeetaImageData processBitmap(Bitmap originBitmap);

    /**
     * YUV 是一种彩色编码系统，主要用在视频、图形处理流水线中(pipeline)。
     * 相对于 RGB 颜色空间，设计 YUV 的目的就是为了编码、传输的方便，减少带宽占用和信息出错。
     *
     * @param yuv
     * @param width
     * @param height
     * @param format
     * @return
     */
    private native SeetaImageData processYUVData(byte[] yuv, int width, int height, int format);

    /**
     * @param y
     * @param u
     * @param v
     * @param width
     * @param height
     * @return
     */
    private native SeetaImageData processSplitYUVData(byte[] y, byte[] u, byte[] v, int width, int height);
}
