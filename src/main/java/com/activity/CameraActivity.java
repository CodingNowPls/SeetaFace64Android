package com.activity;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.camera.CameraFragment;
import com.camera.CameraPresenter;
import com.seetatech.seetaverify.R;
import com.seetaface.FaceHelper;

public class CameraActivity extends AppCompatActivity {

    public static final String TAG = "CameraActivity";
    private Fragment fragment = FragmentFactory.create();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_camera);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment)
                .commitNow();
        this.setFinishOnTouchOutside(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        View decorView = getWindow().getDecorView();
        int uiOptions = decorView.getSystemUiVisibility()
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged");
    }




    private static class FragmentFactory {
        public static Fragment create() {
            CameraFragment fragment = new CameraFragment();
            CameraPresenter cameraPresenter = new CameraPresenter(fragment);
            FaceHelper faceHelper = FaceHelper.getInstance();
            cameraPresenter.setFaceHelper(faceHelper);
            return fragment;

        }
    }
}
