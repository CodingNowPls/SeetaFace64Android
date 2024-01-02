package com.database;

import android.util.Log;

import java.io.File;
import java.util.List;

public class FaceRepository {
    private static final String TAG = "FaceRepository";
    private FaceDao faceDao;
    private int currentIndex = 0;
    private int pageSize;

    public FaceRepository(int pageSize, FaceDao faceDao) {
        this.pageSize = pageSize;
        this.faceDao = faceDao;

    }

    public List<FaceEntity> loadMore() {
        List<FaceEntity> faceEntities = faceDao.getFaces(currentIndex, pageSize);
        currentIndex += faceEntities.size();
        return faceEntities;
    }

    public List<FaceEntity> reload() {
        currentIndex = 0;
        return loadMore();
    }

    public int clearAll() {
        // 由于涉及到文件删除操作，所以使用faceServer
        int faceCount = 0;
        return faceCount;
    }

    public int delete(FaceEntity faceEntity) {
        int index = faceDao.deleteFace(faceEntity);
        boolean delete = new File(faceEntity.getImagePath()).delete();
        if (!delete) {
            Log.w(TAG, "deleteFace: failed to delete headImageFile '" + faceEntity.getImagePath() + "'");
        }
        return index;
    }

    public FaceEntity registerJpeg(float[] bytes, String name, FaceEntity faceEntity) throws RegisterFailedException {
        return null;
    }

    public int getTotalFaceCount() {
        return faceDao.getFaceCount();
    }

}
