package com.seetaface.v6;

/**
 * 构造活体识别器需要传入的结构体参数。
 */
public class SeetaModelSetting {
    /**
     * 识别器模型
     */
    String model;
    /**
     * GPU id
     */
    int id;
    /**
     * 计算设备(CPU 或者 GPU)  ,缺省值：AUTO
     */
    SeetaDevice device;

}
