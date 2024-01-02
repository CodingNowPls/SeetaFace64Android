package com.seetaface.v6;

import com.seetaface.constant.SeetafaceConst;

/**
 * 人脸识别器。
 * <p>
 * 人脸识别器要求输入原始图像数据和人脸特征点（或者裁剪好的人脸数据），
 * 对输入的人脸提取特征值数组，根据提取的特征值数组对人脸进行相似度比较。
 */
public class FaceRecognizer {

    static {
        System.loadLibrary("FaceRecognizer");
    }

    public FaceRecognizer(String model) {
        String recognizerModel = SeetaUtils.getSeetaFaceModelPath(model);
        nativeCreateEngine(recognizerModel);

        nativeSetProperty(SeetafaceConst.PROPERTY_NUMBER_THREADS, 1);
    }

    public void destroy() {
        nativeDestroyEngine();
    }

    /**
     * 获取裁剪人脸的宽度。
     *
     * @return 返回的人脸宽度
     */
    public int getCropFaceWidth() {
        return nativeGetCropFaceWidth();
    }


    /**
     * 获取裁剪的人脸高度。
     *
     * @return 返回的人脸高度
     */
    public int getCropFaceHeight() {
        return nativeGetCropFaceHeight();
    }

    /**
     * 获取裁剪的人脸数据通道数。
     *
     * @return 返回的人脸数据通道数
     */
    public int getCropFaceChannels() {
        return nativeGetCropFaceChannels();
    }

    /**
     * 裁剪人脸。
     *
     * @param image  原始图像数据
     * @param pointF 人脸特征点数组
     * @param crop   返回的裁剪人脸
     * @return true 表示人脸裁剪成功
     */
    private boolean cropFace(SeetaImageData image, SeetaPointF[] pointF, SeetaImageData crop) {
        return nativeCropFace(image, pointF, crop);
    }

    /**
     * 裁剪人脸。
     *
     * @param image  原始图像数据
     * @param pointF 人脸特征点数组
     * @return 返回的裁剪人脸
     */
    public SeetaImageData cropFace(SeetaImageData image, SeetaPointF[] pointF) {
        return nativeCropFace(image, pointF);
    }

    /**
     * 获取裁剪人脸的宽度。
     *
     * @return 返回的人脸宽度
     */
    public int getCropFaceWidthV2() {
        return nativeGetCropFaceWidthV2();
    }

    /**
     * 获取裁剪的人脸高度。
     *
     * @return 返回的人脸高度
     */
    public int getCropFaceHeightV2() {
        return nativeGetCropFaceHeightV2();
    }

    /**
     * 获取裁剪的人脸数据通道数。
     *
     * @return 返回的人脸数据通道数
     */
    public int getCropFaceChannelsV2() {
        return nativeGetCropFaceChannelsV2();
    }

    /**
     * 裁剪人脸。
     *
     * @param image        原始图像数据
     * @param pointF       人脸特征点数组
     * @param croppedImage 返回的裁剪人脸
     * @return true 表示人脸裁剪成功
     */
    public boolean cropFaceV2(SeetaImageData image, SeetaPointF[] pointF, SeetaImageData croppedImage) {
        return nativeCropFaceV2(image, pointF, croppedImage);
    }

    /**
     * 裁剪人脸。
     *
     * @param image  原始图像数据
     * @param pointF 人脸特征点数组
     * @return 返回的裁剪人脸
     */
    public SeetaImageData cropFaceV2(SeetaImageData image, SeetaPointF[] pointF) {
        return nativeCropFaceV2(image, pointF);
    }

    /**
     * 获取特征值数组的长度。
     *
     * @return 特征值数组的长度
     */
    public int getExtractFeatureSize() {
        return nativeGetExtractFeatureSize();
    }

    /**
     * 输入原始图像数据和人脸特征点数组，提取人脸的特征值数组。
     *
     * @param image    原始的人脸图像数据
     * @param pointF   人脸的特征点数组
     * @param features 返回的人脸特征值数组
     * @return true 表示提取特征成功
     */
    public boolean extract(SeetaImageData image, SeetaPointF[] pointF, float[] features) {
        return nativeExtractFace(image, pointF, features);
    }

    /**
     * 输入裁剪后的人脸图像，提取人脸的特征值数组。
     *
     * @param croppedImage 裁剪后的人脸图像数据
     * @param features     返回的人脸特征值数组
     * @return true 表示提取特征成功 |
     */
    public boolean extractCropFace(SeetaImageData croppedImage, float[] features) {
        return nativeExtractCropFace(croppedImage, features);
    }

    /**
     * 比较两人脸的特征值数据，获取人脸的相似度值。
     *
     * @param features1 特征数组一
     * @param features2 特征数组二
     * @return
     */
    public float compareFace(float[] features1, float[] features2) {
        return nativeCompareFace(features1, features2);
    }

    /**
     * 设置相关属性值。其中PROPERTY_NUMBER_THREADS: 表示计算线程数，默认为 4.
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


    private native int nativeCreateEngine(String recognizeModel);

    private native int nativeDestroyEngine();

    /**
     * 获取裁剪人脸的宽度。
     * sdk: GetCropFaceWidth
     *
     * @return 返回的人脸宽度
     */
    private native int nativeGetCropFaceWidth();

    /**
     * 获取裁剪的人脸高度。
     * sdk:   GetCropFaceHeight
     *
     * @return 返回的人脸高度
     */
    private native int nativeGetCropFaceHeight();

    /**
     * 获取裁剪的人脸数据通道数。
     * sdk:   GetCropFaceChannels
     *
     * @return 返回的人脸数据通道数
     */
    private native int nativeGetCropFaceChannels();

    /**
     * 裁剪人脸。
     * sdk: CropFace
     *
     * @param image  原始图像数据
     * @param pointF 人脸特征点数组
     * @param crop   返回的裁剪人脸
     * @return true 表示人脸裁剪成功
     */
    private native boolean nativeCropFace(SeetaImageData image, SeetaPointF[] pointF, SeetaImageData crop);

    /**
     * 裁剪人脸。
     * sdk: CropFace
     *
     * @param image  原始图像数据
     * @param pointF 人脸特征点数组
     * @return 返回的裁剪人脸
     */
    private native SeetaImageData nativeCropFace(SeetaImageData image, SeetaPointF[] pointF);

    /**
     * 获取裁剪人脸的宽度。
     * sdk: GetCropFaceWidthV2
     *
     * @return 返回的人脸宽度
     */
    private native int nativeGetCropFaceWidthV2();

    /**
     * 获取裁剪的人脸高度。
     * sdk: GetCropFaceHeightV2
     *
     * @return 返回的人脸高度
     */
    private native int nativeGetCropFaceHeightV2();

    /**
     * 获取裁剪的人脸数据通道数。
     * sdk: GetCropFaceChannelsV2
     *
     * @return 返回的人脸数据通道数
     */
    private native int nativeGetCropFaceChannelsV2();

    /**
     * 裁剪人脸。
     * sdk: CropFaceV2
     *
     * @param image        原始图像数据
     * @param pointF       人脸特征点数组
     * @param croppedImage 返回的裁剪人脸
     * @return true 表示人脸裁剪成功
     */
    private native boolean nativeCropFaceV2(SeetaImageData image, SeetaPointF[] pointF, SeetaImageData croppedImage);

    /**
     * 裁剪人脸。
     * sdk: CropFaceV2
     *
     * @param image  原始图像数据
     * @param pointF 人脸特征点数组
     * @return 返回的裁剪人脸
     */
    private native SeetaImageData nativeCropFaceV2(SeetaImageData image, SeetaPointF[] pointF);

    /**
     * 获取特征值数组的长度。
     * sdk: GetExtractFeatureSize
     *
     * @return 特征值数组的长度
     */
    private native int nativeGetExtractFeatureSize();

    /**
     * 输入原始图像数据和人脸特征点数组，提取人脸的特征值数组。
     * sdk:  Extract
     *
     * @param image    原始的人脸图像数据
     * @param pointF   人脸的特征点数组
     * @param features 返回的人脸特征值数组
     * @return true 表示提取特征成功
     */
    private native boolean nativeExtractFace(SeetaImageData image, SeetaPointF[] pointF, float[] features);

    /**
     * 输入裁剪后的人脸图像，提取人脸的特征值数组。
     * sdk: ExtractCroppedFace
     *
     * @param croppedImage 裁剪后的人脸图像数据
     * @param features     返回的人脸特征值数组
     * @return true 表示提取特征成功 |
     */
    private native boolean nativeExtractCropFace(SeetaImageData croppedImage, float[] features);

    /**
     * 比较两人脸的特征值数据，获取人脸的相似度值。
     * sdk: CalculateSimilarity
     *
     * @param features1 特征数组一
     * @param features2 特征数组二
     * @return
     */
    private native float nativeCompareFace(float[] features1, float[] features2);

    /**
     * 设置相关属性值。其中PROPERTY_NUMBER_THREADS: 表示计算线程数，默认为 4.
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
