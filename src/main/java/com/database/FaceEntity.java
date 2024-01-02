package com.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.Objects;

/**
 * 人脸库中的单挑人脸记录
 * 设置user_name为唯一索引
 */
@Entity(
        tableName = "face",
        indices = {@Index(value = {"user_name"},
                unique = true)}
)
public class FaceEntity implements Parcelable {
    /**
     * 人脸id，主键
     */
    @PrimaryKey(autoGenerate = true)
    private long faceId;
    /**
     * 用户名称
     */
    @ColumnInfo(name = "user_name")
    private String userName;
    /**
     * 图片路径
     */
    @ColumnInfo(name = "image_path")
    private String imagePath;
    /**
     * 人脸特征数据
     */
    @ColumnInfo(name = "feature_data")
    private float[] featureData;
    /**
     * 注册时间
     */
    @ColumnInfo(name = "register_time")
    private long registerTime;


    public FaceEntity(String userName, String imagePath, float[] featureData) {
        this.userName = userName;
        this.imagePath = imagePath;
        this.featureData = featureData;
        registerTime = System.currentTimeMillis();
    }


    public FaceEntity(FaceEntity faceEntity) {
        this.faceId = faceEntity.faceId;
        this.userName = faceEntity.userName;
        this.imagePath = faceEntity.imagePath;
        this.featureData = faceEntity.featureData;
        this.registerTime = faceEntity.registerTime;
    }


    protected FaceEntity(Parcel in) {
        faceId = in.readLong();
        registerTime = in.readLong();
        userName = in.readString();
        imagePath = in.readString();
        featureData = in.createFloatArray();
    }

    public static final Creator<FaceEntity> CREATOR = new Creator<FaceEntity>() {
        @Override
        public FaceEntity createFromParcel(Parcel in) {
            return new FaceEntity(in);
        }

        @Override
        public FaceEntity[] newArray(int size) {
            return new FaceEntity[size];
        }
    };

    public long getFaceId() {
        return faceId;
    }

    public void setFaceId(long faceId) {
        this.faceId = faceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public float[] getFeatureData() {
        return featureData;
    }

    public void setFeatureData(float[] featureData) {
        this.featureData = featureData;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(faceId);
        dest.writeLong(registerTime);
        dest.writeString(userName);
        dest.writeString(imagePath);
        dest.writeFloatArray(featureData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FaceEntity that = (FaceEntity) o;
        return faceId == that.faceId &&
                registerTime == that.registerTime &&
                userName.equals(that.userName) &&
                imagePath.equals(that.imagePath) &&
                Arrays.equals(featureData, that.featureData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(faceId, registerTime, userName, imagePath);
        result = 31 * result + Arrays.hashCode(featureData);
        return result;
    }
}
