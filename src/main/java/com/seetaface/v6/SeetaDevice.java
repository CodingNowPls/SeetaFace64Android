package com.seetaface.v6;

/**
 * 模型运行的计算设备。
 */
public enum SeetaDevice {
    /**
     * 自动检测, 会优先使用
     */
    SEETA_DEVICE_AUTO,
    /**
     * 使用CPU计算
     */
    GPUSEETA_DEVICE_CPU,
    /**
     * 使用GPU计算
     */
    SEETA_DEVICE_GPU

}
