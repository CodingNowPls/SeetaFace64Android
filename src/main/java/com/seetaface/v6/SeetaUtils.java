package com.seetaface.v6;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 工具类
 */
public final class SeetaUtils {
    private static final String TAG = "SeetaUtils";

    public static final String SEETA_FACE_MODEL_PATH = "/SeetaFace6/models/";

    public static String getSeetaFaceModelPath(String model) {
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        return rootPath + SEETA_FACE_MODEL_PATH + model;
    }

    public static void copyFilesToPath(InputStream in, File destFile) {
        try {
            BufferedInputStream bis = new BufferedInputStream(in);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            bis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] getAssetsModels(Context context) {
        AssetManager assetManager = context.getAssets();
        String[] models = null;
        try {
            models = assetManager.list("models");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return models;
    }

    public static void copyModelsToExternalStorage(Context context) {
        // have assets models
        String[] assetsModels = getAssetsModels(context);
        if (assetsModels == null || assetsModels.length == 0) {
            return;
        }

        // create root directory
        String modelPath = Environment.getExternalStorageDirectory().getAbsolutePath() + SEETA_FACE_MODEL_PATH;
        File modelPathFile = new File(modelPath);
        if (!modelPathFile.exists()) {
            modelPathFile.mkdirs();
        }

        if (assetsModels != null && assetsModels.length > 0) {
            try {
                AssetManager assetManager = context.getAssets();
                for (String model : assetsModels) {
                    File destModelFile = new File(modelPath + model);
                    Log.d(TAG, "-------gu--- shouldCopyAssetsModels model:" + model + ", destModelFile:" + destModelFile);
                    if (!destModelFile.exists()) {
                        String fileName = "models/" + model;
                        Log.d(TAG, "-------gu--- copyModelsToExternalStorage fileName:" + fileName);
                        copyFilesToPath(assetManager.open(fileName), destModelFile);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean shouldCopyAssetsModels(Context context) {
        String modelPath = Environment.getExternalStorageDirectory().getAbsolutePath() + SEETA_FACE_MODEL_PATH;
        File modelPathFile = new File(modelPath);
        if (modelPathFile.exists()) {
            String[] assetsModels = getAssetsModels(context);
            if (assetsModels == null || assetsModels.length == 0) {
                // never copy models
                return false;
            }

            /*for (String assetsModel : assetsModels) {
                Log.d(TAG, "-------gu--- shouldCopyAssetsModels assetsModel:" + assetsModel);
            }*/

            String[] models = modelPathFile.list();
            if (models == null || models.length != assetsModels.length) {
                return true;
            }

            /*for (String model : models) {
                Log.d(TAG, "-------gu--- shouldCopyAssetsModels model:" + model);
            }*/

            // sort and compare array
            Arrays.sort(assetsModels);
            Arrays.sort(models);
            if (Arrays.equals(assetsModels, models)) {
                return false;
            }
        }
        return true;
    }
}
