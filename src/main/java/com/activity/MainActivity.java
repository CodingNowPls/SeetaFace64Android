package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.seetaface.v6.SeetaUtils;
import com.seetatech.seetaverify.R;
import com.utils.CachedImage;
import com.utils.ContextUtil;

public class MainActivity extends PermissionActivity {

    private static final String TAG = "MainActivity";
    private ImageView imageView;
    private TextView tvSilentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化Context
        ContextUtil.context = this;
        imageView = (ImageView) findViewById(R.id.best_image);
        tvSilentScore = (TextView) findViewById(R.id.silentScore);

        this.setFinishOnTouchOutside(false);

    }

    public void onClick(View view) {
        imageView.setImageBitmap(null);
        tvSilentScore.setText("");
        startActivityForResult(new Intent(this, CameraActivity.class), 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void notifyAllPermissionsRequested() {
        if (SeetaUtils.shouldCopyAssetsModels(MainActivity.this)) {
            Log.d(TAG, "-------gu--- notifyAllPermissionsRequested model not exist should copy");
            SeetaUtils.copyModelsToExternalStorage(MainActivity.this);
        } else {
            Log.d(TAG, "-------gu--- notifyAllPermissionsRequested");

        }
        //打开相机
        startActivityForResult(new Intent(this, CameraActivity.class), 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
