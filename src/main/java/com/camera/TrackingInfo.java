package com.camera;

import com.seetaface.v6.SeetaRect;

import org.opencv.core.Mat;
import org.opencv.core.Rect;


public class TrackingInfo {

    public Mat matBgr;

    public SeetaRect faceInfo = new SeetaRect();

    public Rect faceRect = new Rect();

}