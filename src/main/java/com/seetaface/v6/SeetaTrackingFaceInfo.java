package com.seetaface.v6;

/**
 * 脸部追踪信息
 */
public class SeetaTrackingFaceInfo {
    /**
     * 人脸位置
     */
    SeetaRect pos;
    /**
     * 人脸置信分数
     */
    float score;
    /**
     * 视频帧的索引
     */
    int frame_no;
    /**
     * 跟踪的人脸标识id
     */
    int PID;

}
