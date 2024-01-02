package com.seetaface.v6;

/**
 * 图像数据
 */
public class SeetaImageData {
    /**
     * 图像数据
     */
    public byte[] data;

    /**
     * 图像的宽度
     */
    public int width;
    /**
     * 图像的高度
     */
    public int height;
    /**
     * 图像的通道数
     */
    public int channels;


    public SeetaImageData() {
    }

    public SeetaImageData(int width, int height, int channels) {
        this.data = new byte[width * height * channels];
        this.width = width;
        this.height = height;
        this.channels = channels;
    }
    public SeetaImageData(int width, int height) {
        this(width, height, 1);
    }

}
