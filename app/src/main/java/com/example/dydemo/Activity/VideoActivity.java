package com.example.dydemo.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dydemo.R;
import com.example.dydemo.model.DyVideo;
import com.example.dydemo.until.LogUtil;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = "VideoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        DyVideo data = (DyVideo) getIntent().getSerializableExtra("video");
        LogUtil.d(TAG, "onCreate: data = " + data.getUser_name());
    }
}
