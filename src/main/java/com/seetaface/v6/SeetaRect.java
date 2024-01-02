package com.seetaface.v6;

/**
 * 人脸位置
 */
public class SeetaRect {
    /**
     * 人脸区域左上角横坐标
     */
    public int x;
    /**
     * 人脸区域左上角纵坐标
     */
    public int y;
    /**
     * 人脸区域宽度
     */
    public int width;
    /**
     * 人脸区域宽度
     */
    public int height;

    @Override
    public String toString() {
        return "SeetaRect{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
