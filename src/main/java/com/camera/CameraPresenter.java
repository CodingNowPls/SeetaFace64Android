package com.camera;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.database.FaceEntity;
import com.seetaface.FaceEngine;

import com.seetaface.enums.EnumFaceAntiStatus;
import com.seetaface.v6.FaceAntiSpoofing;
import com.seetaface.v6.FaceDetector;
import com.seetaface.v6.FaceLandMarker;
import com.seetaface.v6.FaceRecognizer;
import com.seetaface.v6.SeetaFaceInfo;
import com.seetaface.v6.SeetaImageData;
import com.seetaface.v6.SeetaPointF;
import com.config.FaceConfig;
import com.seetaface.FaceHelper;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


public class CameraPresenter implements VerificationContract.Presenter {

    private static final String TAG = "CameraPresenter";

    static {
        System.loadLibrary("opencv_java3");
    }


    private VerificationContract.View mView;


    private static int WIDTH = FaceConfig.IMAGE_WIDTH;
    private static int HEIGHT = FaceConfig.IMAGE_HEIGHT;
    public SeetaImageData imageData = new SeetaImageData(WIDTH, HEIGHT, 3);

    private float thresh = 0.70f;


    private FaceHelper faceHelper;
    private FaceEngine faceEngine;
    private FaceDetector faceDetector;
    private FaceLandMarker faceLandMarker;
    private FaceRecognizer faceRecognizer;
    private FaceAntiSpoofing faceAntiSpoofing;


    private HandlerThread faceTrackThread;
    private HandlerThread faceRecognizerThread;

    {
        faceTrackThread = new HandlerThread("faceTrackThread", Process.THREAD_PRIORITY_MORE_FAVORABLE);
        faceRecognizerThread = new HandlerThread("faceRecognizerThread", Process.THREAD_PRIORITY_MORE_FAVORABLE);
        faceTrackThread.start();
        faceRecognizerThread.start();

    }

    private Mat matNv21 = new Mat(FaceConfig.CAMERA_PREVIEW_HEIGHT + FaceConfig.CAMERA_PREVIEW_HEIGHT / 2,
            FaceConfig.CAMERA_PREVIEW_WIDTH, CvType.CV_8UC1);

    public CameraPresenter(VerificationContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    public void setFaceHelper(FaceHelper faceHelper) {
        this.faceHelper = faceHelper;
        faceEngine = faceHelper.getFaceEngine();
        faceDetector = faceEngine.getFaceDetector();
        faceRecognizer = faceEngine.getFaceRecognizer();
        faceLandMarker = faceEngine.getFaceLandMarker();
        faceAntiSpoofing = faceEngine.getFaceAntiSpoofing();
    }

    private Handler faceTrackingHandler = new Handler(faceTrackThread.getLooper()) {
        @Override
        public void handleMessage(Message msg) {
            final TrackingInfo trackingInfo = (TrackingInfo) msg.obj;
            trackingInfo.matBgr.get(0, 0, imageData.data);
            SeetaFaceInfo[] faces = faceDetector.detect(imageData);

            trackingInfo.faceInfo.x = 0;
            trackingInfo.faceInfo.y = 0;
            trackingInfo.faceInfo.width = 0;
            trackingInfo.faceInfo.height = 0;

            if (null != faces && faces.length != 0) {

                int maxIndex = 0;
                double maxWidth = 0;
                for (int i = 0; i < faces.length; ++i) {
                    if (faces[i].position.width > maxWidth) {
                        maxIndex = i;
                        maxWidth = faces[i].position.width;
                    }
                }

                trackingInfo.faceInfo = faces[maxIndex].position;

                trackingInfo.faceRect.x = (int) faces[maxIndex].position.x;
                trackingInfo.faceRect.y = (int) faces[maxIndex].position.y;
                trackingInfo.faceRect.width = (int) faces[maxIndex].position.width;
                trackingInfo.faceRect.height = (int) faces[maxIndex].position.height;

                int limitX = trackingInfo.faceRect.x + trackingInfo.faceRect.width;
                int limitY = trackingInfo.faceRect.y + trackingInfo.faceRect.height;
                if (limitX < WIDTH && limitY < HEIGHT) {
                    Mat faceMatBGR = new Mat(trackingInfo.matBgr, trackingInfo.faceRect);
                    Imgproc.resize(faceMatBGR, faceMatBGR, new Size(200, 240));
                    Mat faceMatBGRA = new Mat();
                    Imgproc.cvtColor(faceMatBGR, faceMatBGRA, Imgproc.COLOR_BGR2RGBA);
                    Bitmap faceBmp = Bitmap.createBitmap(faceMatBGR.width(), faceMatBGR.height(),
                            Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(faceMatBGRA, faceBmp);
                    mView.drawFaceImage(faceBmp);

                }

                faceRecognizerHandler.removeMessages(0);
                faceRecognizerHandler.obtainMessage(0, trackingInfo).sendToTarget();

            } else {
                mView.drawFaceRect(null);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        }
    };
    Handler faceRecognizerTipsHander = null;
    SeetaPointF[] pointFs = null;
    private Handler faceRecognizerHandler = new Handler(faceRecognizerThread.getLooper()) {

        @Override
        public void handleMessage(Message msg) {
            final TrackingInfo trackingInfo = (TrackingInfo) msg.obj;
            final Rect faceRect = trackingInfo.faceRect;
            trackingInfo.matBgr.get(0, 0, imageData.data);
            String targetName = "unknown";
            //注册人脸
            CameraFragment cameraFragment = (CameraFragment) mView;
            if (cameraFragment.needFaceRegister) {
                boolean canRegister = true;
                float[] feats = new float[faceRecognizer.getExtractFeatureSize()];

                if (trackingInfo.faceInfo.width != 0) {
                    //特征点检测
                    pointFs = new SeetaPointF[5];
                    faceLandMarker.mark(imageData, trackingInfo.faceInfo, pointFs);
                    //特征提取
                    faceRecognizer.extract(imageData, pointFs, feats);
                    if ("".equals(cameraFragment.registeredName)) {
                        canRegister = false;
                        final String tip = "注册名称不能为空";
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                mView.showSimpleTip(tip);
                            }
                        });
                    }

                    //活体检测
                    int predictStatus = faceAntiSpoofing.predict(imageData, trackingInfo.faceInfo, pointFs);
                    EnumFaceAntiStatus faceAntiStatus = EnumFaceAntiStatus.getFaceAntiStatus(predictStatus);
                    //真实人脸才进行匹配
                    if (EnumFaceAntiStatus.STATUS_REAL != faceAntiStatus) {
                        final String tip = "活体检测失败:" + faceAntiStatus.getDesc();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                mView.showSimpleTip(tip);
                            }
                        });
                    }


                    FaceEntity faceEntity = faceHelper.searchFace(feats);
                    if (null != faceEntity) {
                        canRegister = false;
                        final String tip = cameraFragment.registeredName + "人脸信息已被注册";
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                mView.showSimpleTip(tip);
                            }
                        });
                    }

                }

                //进行人脸的注册
                if (canRegister) {
                    //注册人脸
                    faceHelper.registerFace(feats, cameraFragment.registeredName);
                    final String tip = cameraFragment.registeredName + "名称已经注册成功";
                    faceRecognizerTipsHander = new Handler(Looper.getMainLooper());
                    faceRecognizerTipsHander.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.faceRegister(tip);
                        }
                    });
                }
            }

            //进行人脸识别
            if (trackingInfo.faceInfo.width != 0) {
                //特征点检测
                pointFs = new SeetaPointF[5];
                faceLandMarker.mark(imageData, trackingInfo.faceInfo, pointFs);

                //活体检测
                int predictStatus = faceAntiSpoofing.predict(imageData, trackingInfo.faceInfo, pointFs);
                EnumFaceAntiStatus faceAntiStatus = EnumFaceAntiStatus.getFaceAntiStatus(predictStatus);
                //真实人脸才进行匹配
                if (EnumFaceAntiStatus.STATUS_REAL != faceAntiStatus) {
                    final String tip = "活体检测失败:" + faceAntiStatus.getDesc();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mView.showSimpleTip(tip);
                        }
                    });
                }

                //特征提取
                int faceCount = faceHelper.getFaceDao().getFaceCount();
                if (faceCount > 0) {
                    //不空进行特征提取，并比对
                    float[] feats = new float[faceRecognizer.getExtractFeatureSize()];
                    faceRecognizer.extract(imageData, pointFs, feats);
                    FaceEntity faceEntity = faceHelper.searchFace(feats);
                    if (null != faceEntity) {
                        targetName = faceEntity.getUserName();
                    }
                }
            }

            final String pickedName = targetName;
            Log.e("recognized name:", pickedName);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mView.setName(pickedName, trackingInfo.matBgr, faceRect);
                }
            });
        }
    };

    @Override
    public void detect(byte[] data, int width, int height, int rotation) {
        TrackingInfo trackingInfo = new TrackingInfo();
        matNv21.put(0, 0, data);
        trackingInfo.matBgr = new Mat(FaceConfig.CAMERA_PREVIEW_HEIGHT, FaceConfig.CAMERA_PREVIEW_WIDTH, CvType.CV_8UC3);
        Imgproc.cvtColor(matNv21, trackingInfo.matBgr, Imgproc.COLOR_YUV2BGR_NV21);
        Core.transpose(trackingInfo.matBgr, trackingInfo.matBgr);
        Core.flip(trackingInfo.matBgr, trackingInfo.matBgr, 0);
        Core.flip(trackingInfo.matBgr, trackingInfo.matBgr, 1);
        faceTrackingHandler.removeMessages(1);
        faceTrackingHandler.obtainMessage(1, trackingInfo).sendToTarget();
    }


    @Override
    public void destroy() {
        faceTrackThread.quitSafely();
        faceRecognizerThread.quitSafely();
    }
}
