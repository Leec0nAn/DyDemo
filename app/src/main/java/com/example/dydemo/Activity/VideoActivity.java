package com.example.dydemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dydemo.adapter.videoAdapter;
import com.example.dydemo.bean.TiktokBean;
import com.example.dydemo.customView.MyLayoutManager;
import com.example.dydemo.databinding.ActivityVideoBinding;
import com.example.dydemo.model.DyDataInstance;
import com.example.dydemo.until.LogUtil;
import com.example.dydemo.customView.OnViewPagerListener;
import com.example.dydemo.view.videoViewHolder;
import com.example.dydemo.fragment.CommentsBottomSheetDialogFragment;
import com.example.dydemo.viewModel.VideoActivityViewModel;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = "VideoActivity";
    ActivityVideoBinding binding;
    private int lastSelectedPosition = -1;

    private VideoActivityViewModel videoActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        videoActivityViewModel = new ViewModelProvider(this).get(VideoActivityViewModel.class);
        setContentView(binding.getRoot());
        androidx.core.view.ViewCompat.requestApplyInsets(binding.getRoot());
        Intent intent = getIntent();
        TiktokBean data = (TiktokBean)intent.getSerializableExtra("video");
        LogUtil.d(TAG, "onCreate: data = " + data.getAuthorName());
        initView(data);
    }
    private void initView(TiktokBean data) {
        //视频流布局采用RecyclerView + Exoplayer 实现
        //自定义LayoutManager
        MyLayoutManager lm = new MyLayoutManager(this, OrientationHelper.VERTICAL,false);
        binding.videoRecyclerView.setLayoutManager(lm);
        videoAdapter adapter = new videoAdapter();
        adapter.setData(DyDataInstance.getInstance().getTiktokDataList());
        binding.videoRecyclerView.setAdapter(adapter);

        adapter.setOnOpenCommentsListener(t -> {
            CommentsBottomSheetDialogFragment.newInstance(t)
                    .show(getSupportFragmentManager(), "comments");
        });
        adapter.setOnToggleActionListener(new videoAdapter.OnToggleActionListener() {
            @Override
            public void onToggleLike(TiktokBean data) {
                videoActivityViewModel.updateTikodata(data);
            }
            @Override
            public void onToggleCollect(TiktokBean data) {
                videoActivityViewModel.updateTikodata(data);
            }
        });

        //获得当前点击的index
        int index = getIntent().getIntExtra("position", -1);
        //如果传过来非法index，=根据传过来的data 去找正确的index
        if (index < 0) {
            index = DyDataInstance.getInstance().getTiktokDataList().indexOf(data);
            if (index < 0) {
                for (int i = 0; i < DyDataInstance.getInstance().getTiktokDataList().size(); i++) {
                    TiktokBean t = DyDataInstance.getInstance().getTiktokDataList().get(i);
                    if (t.getVideoDownloadUrl() != null && data.getVideoDownloadUrl() != null
                            && t.getVideoDownloadUrl().equals(data.getVideoDownloadUrl())) {
                        index = i;
                        break;
                    }
                }
            }
        }
        //当index值合法时，滑倒相应位置
        if (index >= 0) {
            binding.videoRecyclerView.scrollToPosition(index);
            tryStartPlayAt(index, 3);
        }

        lm.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {}

            @Override
            public void onPageRelease(boolean isNext, int position) {
                videoViewHolder holder = (videoViewHolder) binding.videoRecyclerView.findViewHolderForAdapterPosition(position);
                if (holder != null) {
                    holder.videoPlayer.pauseForScroll();
                }
                lastSelectedPosition = -1;
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                videoViewHolder holder = (videoViewHolder) binding.videoRecyclerView.findViewHolderForAdapterPosition(position);
                if (holder != null) {
                    holder.videoPlayer.startPlayLogic();
                    lastSelectedPosition = position;
                } else {
                    tryStartPlayAt(position, 5);
                }
                int next = position + 1;
                videoViewHolder nextHolder = (videoViewHolder) binding.videoRecyclerView.findViewHolderForAdapterPosition(next);
                if (nextHolder != null) {
                    nextHolder.videoPlayer.preload();
                }
            }
        });
        videoActivityViewModel.getTikodata().observe(this, new Observer<TiktokBean>() {
            @Override
            public void onChanged(TiktokBean tiktokBean) {
                int pos = lastSelectedPosition;
                if (pos < 0) {
                    try {
                        RecyclerView.LayoutManager lm2 = binding.videoRecyclerView.getLayoutManager();
                        if (lm2 instanceof LinearLayoutManager) {
                            pos = ((LinearLayoutManager) lm2).findFirstVisibleItemPosition();
                        }
                    } catch (Throwable ignored) {}
                }
                if (pos >= 0) {
                    adapter.notifyItemChanged(pos, "actions");
                }
            }
        });
    }


    private void tryStartPlayAt(int position, int retries) {
        videoViewHolder holder = (videoViewHolder) binding.videoRecyclerView.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            if (lastSelectedPosition != position) {
                holder.videoPlayer.startPlayLogic();
                lastSelectedPosition = position;
            }
        } else if (retries > 0) {
            binding.videoRecyclerView.postDelayed(() -> tryStartPlayAt(position, retries - 1), 80);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAllPlayers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseAllPlayers();
    }

    private void stopAllPlayers() {
        RecyclerView rv = binding != null ? binding.videoRecyclerView : null;
        if (rv == null) return;
        for (int i = 0; i < rv.getChildCount(); i++) {
            View child = rv.getChildAt(i);
            try {
                videoViewHolder holder = (videoViewHolder) rv.getChildViewHolder(child);
                if (holder != null && holder.videoPlayer != null) {
                    holder.videoPlayer.pause();
                }
            } catch (Throwable ignored) {}
        }
    }

    private void releaseAllPlayers() {
        RecyclerView rv = binding != null ? binding.videoRecyclerView : null;
        if (rv == null) return;
        for (int i = 0; i < rv.getChildCount(); i++) {
            View child = rv.getChildAt(i);
            try {
                videoViewHolder holder = (videoViewHolder) rv.getChildViewHolder(child);
                if (holder != null && holder.videoPlayer != null) {
                    holder.videoPlayer.releasePlayer();
                }
            } catch (Throwable ignored) {}
        }
        lastSelectedPosition = -1;
    }
}
