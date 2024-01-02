package com.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FaceDao {
    /**
     * 获取库中所有已注册人脸
     *
     * @return 所有已注册人脸
     */
    @Query("SELECT * FROM face")
    List<FaceEntity> getAllFaces();

    /**
     * 分页获取库中的人脸
     *
     * @param start 起始下标
     * @param size  单次获取的长度
     * @return 从下标为start开始的size个已注册人脸
     */
    @Query("SELECT * FROM face order by faceId desc limit :start,:size ")
    List<FaceEntity> getFaces(int start, int size);

    /**
     * 更新已注册的人脸信息
     *
     * @param faceEntity 已注册的人脸信息
     * @return
     */
    @Update
    int updateFaceEntity(FaceEntity faceEntity);

    /**
     * 删除人脸
     *
     * @param faceEntity 已注册的人脸信息
     * @return
     */
    @Delete
    int deleteFace(FaceEntity faceEntity);

    /**
     * 删除所有已注册的人脸
     *
     * @return
     */
    @Query("DELETE from face")
    int deleteAll();

    /**
     * 插入一个人脸入库
     *
     * @param faceEntity
     * @return
     */
    @Insert
    long insert(FaceEntity faceEntity);

    /**
     * 获取已注册的人脸数
     *
     * @return
     */
    @Query("SELECT COUNT(1) from face")
    int getFaceCount();

    @Query("SELECT * FROM face WHERE faceId = :faceId limit 1")
    FaceEntity queryByFaceId(int faceId);

    @Query("SELECT * FROM face WHERE user_name = :userName limit 1")
    FaceEntity queryByUserName(String userName);
}
