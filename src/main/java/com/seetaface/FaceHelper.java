package com.seetaface;

import com.database.FaceDao;
import com.database.FaceDatabase;
import com.database.FaceEntity;
import com.database.FaceRepository;
import com.config.FaceConfig;
import com.seetaface.v6.FaceAntiSpoofing;
import com.seetaface.v6.FaceDetector;
import com.seetaface.v6.FaceLandMarker;
import com.seetaface.v6.FaceRecognizer;
import com.utils.ContextUtil;

import java.util.List;


public class FaceHelper {
    private static final int PAGE_SIZE = 20;

    private static FaceHelper faceHelper;

    private FaceDatabase database;
    private FaceDao faceDao;
    private FaceRepository faceRepository;

    private FaceEngine faceEngine;

    private FaceDetector faceDetector;
    private FaceLandMarker faceLandMarker;
    private FaceRecognizer faceRecognizer;
    private FaceAntiSpoofing faceAntiSpoofing;

    private FaceHelper() {
    }

    public static FaceHelper getInstance() {
        if (null == faceHelper) {
            faceHelper = new FaceHelper();
            faceHelper.init();
        }

        return faceHelper;
    }


    private void init() {
        database = FaceDatabase.getInstance(ContextUtil.context);
        faceDao = database.faceDao();
        faceRepository = new FaceRepository(PAGE_SIZE, faceDao);

        faceEngine = FaceEngine.getInstance();
        faceDetector = faceEngine.getFaceDetector();
        faceLandMarker = faceEngine.getFaceLandMarker();
        faceRecognizer = faceEngine.getFaceRecognizer();

    }


    public FaceEntity registerFace(float[] faceFeature, String name) {
        FaceEntity faceEntity = faceDao.queryByUserName(name);
        if (null == faceEntity) {
            faceEntity = new FaceEntity(name, null, faceFeature);
            faceEntity.setRegisterTime(System.currentTimeMillis());
            faceDao.insert(faceEntity);
        } else {
            faceEntity = new FaceEntity(name, null, faceFeature);
            faceDao.updateFaceEntity(faceEntity);
        }
        return faceEntity;
    }

    /**
     * 查找人脸信息
     *
     * @param faceFeature
     * @return
     */

    public FaceEntity searchFace(float[] faceFeature) {
        int totalFaceCount = faceRepository.getTotalFaceCount();
        if (totalFaceCount == 0) {
            return null;
        }

        List<FaceEntity> faceEntityList = faceRepository.reload();
        if (faceEntityList.size() > 0) {
            for (FaceEntity faceEntity : faceEntityList) {
                float[] featureData = faceEntity.getFeatureData();
                //与传进来的人脸数据做比对，找到就返回
                if (faceCompare(faceFeature, featureData)) {
                    return faceEntity;
                }

            }
        }

        //找不到就继续查下一组
        faceEntityList = faceRepository.loadMore();
        while (faceEntityList.size() > 0) {
            for (FaceEntity faceEntity : faceEntityList) {
                float[] featureData = faceEntity.getFeatureData();
                //与传进来的人脸数据做比对，找到就返回
                if (faceCompare(faceFeature, featureData)) {
                    return faceEntity;
                }
            }
        }

        return null;
    }

    /**
     * 人脸相似度比对
     *
     * @param fd1
     * @param fd2
     * @return
     */
    private boolean faceCompare(float[] fd1, float[] fd2) {
        float sim = faceRecognizer.compareFace(fd1, fd2);
        if (sim >= FaceConfig.FACE_SIMILARITY_DEGREE) {
            return true;
        } else {
            return false;
        }

    }


    public FaceEntity deleteFace(String userName) {
        FaceEntity faceEntity = faceDao.queryByUserName(userName);
        if (null != faceEntity) {
            faceDao.deleteFace(faceEntity);
            return faceEntity;
        }
        return null;
    }


    public FaceDatabase getDatabase() {
        return database;
    }

    public FaceDao getFaceDao() {
        return faceDao;
    }

    public FaceRepository getFaceRepository() {
        return faceRepository;
    }

    public FaceEngine getFaceEngine() {
        return faceEngine;
    }
}
