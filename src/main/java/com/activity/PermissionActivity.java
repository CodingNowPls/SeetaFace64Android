package com.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.WindowManager;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.utils.ContextUtil;

import java.util.ArrayList;


public class PermissionActivity extends AppCompatActivity {

    private static final String TAG = "PermissionActivity";

    public static int PERMISSION_REQUEST_CODE = 0x123456;

    private String[] NEED_REQUEST_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public boolean mAllPermissionsGranted = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ArrayList<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : NEED_REQUEST_PERMISSIONS) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "-------gu--- onCreate not granted permission:" + permission);
                needRequestPermissionList.add(permission);
            }
        }
        if (needRequestPermissionList.size() > 0) {
            String[] needRequestPermissions = needRequestPermissionList.toArray(new String[0]);
            requestPermissions(needRequestPermissions, PERMISSION_REQUEST_CODE);
        } else {
            mAllPermissionsGranted = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAllPermissionsGranted) {
            notifyAllPermissionsRequested();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsRequested = true;
            for (String permission : NEED_REQUEST_PERMISSIONS) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "-------gu--- onRequestPermissionsResult not granted permission:" + permission);
                    allPermissionsRequested = false;
                }
            }
            if (allPermissionsRequested) {
                mAllPermissionsGranted = true;
                notifyAllPermissionsRequested();
            }
        }
    }

    protected void notifyAllPermissionsRequested() {
    }

}