package com.example.dydemo.Activity;

import static com.google.android.material.internal.ViewUtils.dpToPx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dydemo.adapter.rvFlowAdapter;
import com.example.dydemo.customView.AdvancedWaterfallItemDecoration;
import com.example.dydemo.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.dydemo.R;
import com.example.dydemo.model.DyVideo;
import com.example.dydemo.testData.TestData;
import com.example.dydemo.until.viewUntil;

public class MainActivity extends AppCompatActivity implements rvFlowAdapter.OnItemClickListener {
    ActivityMainBinding binding;
    private static final int HORIZONTAL_SPACING_DP = 5;         // 水平间距5dp
    private static final int VERTICAL_SPACING_DP = 5;          // 垂直间距5dp
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.rvFlowLayout.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // 将dp值转换为像素值
        int horizontalSpacingPx = viewUntil.dpToPx(HORIZONTAL_SPACING_DP,this);
        int verticalSpacingPx = viewUntil.dpToPx(VERTICAL_SPACING_DP,this);
        AdvancedWaterfallItemDecoration decoration = new AdvancedWaterfallItemDecoration(
                2,              // 列数
                horizontalSpacingPx,     // 水平间距（像素）
                verticalSpacingPx        // 垂直间距（像素）
        );
        rvFlowAdapter adapter = new rvFlowAdapter(TestData.getData());
        adapter.setOnItemClickListener(this);
        binding.rvFlowLayout.addItemDecoration(decoration);
        binding.rvFlowLayout.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, DyVideo data) {
        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
        intent.putExtra("video", data);
        startActivity(intent);
    }
}