package com.seetaface;

import android.util.Log;

import com.config.FaceConfig;
import com.seetaface.constant.SeetafaceConst;
import com.seetaface.constant.SeetafaceModelConst;
import com.seetaface.v6.FaceAntiSpoofing;
import com.seetaface.v6.FaceDetector;
import com.seetaface.v6.FaceLandMarker;
import com.seetaface.v6.FaceRecognizer;


public class FaceEngine {

    private static final String TAG = "FaceEngine";

    private static FaceEngine faceEngine;

    private FaceDetector faceDetector;

    private FaceLandMarker faceLandMarker;

    private FaceRecognizer faceRecognizer;

    private FaceAntiSpoofing faceAntiSpoofing;


    public static FaceEngine getInstance() {
        if (null == faceEngine) {
            faceEngine = new FaceEngine();
            faceEngine.init();
        }
        return faceEngine;
    }


    private void init() {
        try {
            if (faceDetector == null || faceLandMarker == null || faceRecognizer == null) {
                faceDetector = new FaceDetector();
                faceLandMarker = new FaceLandMarker(SeetafaceModelConst.MODEL_LAND_MARKER_PTS5);
                faceRecognizer = new FaceRecognizer(SeetafaceModelConst.MODEL_RECOGNIZER);
                faceAntiSpoofing = new FaceAntiSpoofing(FaceConfig.ANTI_TYPE);
            }
        } catch (Exception e) {
            Log.e(TAG, "init exception:" + e);
        }
    }


    public FaceDetector getFaceDetector() {
        return faceDetector;
    }

    public FaceLandMarker getFaceLandMarker() {
        return faceLandMarker;
    }

    public FaceRecognizer getFaceRecognizer() {
        return faceRecognizer;
    }

    public FaceAntiSpoofing getFaceAntiSpoofing() {
        return faceAntiSpoofing;
    }
}
