package com.example.dydemo.Video.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dydemo.Video.Adapter.videoAdapter;
import com.example.dydemo.bean.TiktokBean;
import com.example.dydemo.Video.CustomView.MyLayoutManager;
import com.example.dydemo.databinding.ActivityVideoBinding;
import com.example.dydemo.DataManager.DyDataInstance;
import com.example.dydemo.until.LogUtil;
import com.example.dydemo.Video.CustomView.OnViewPagerListener;
import com.example.dydemo.Video.view.videoViewHolder;
import com.example.dydemo.Comment.fragment.CommentsBottomSheetDialogFragment;
import com.example.dydemo.Video.viewModel.VideoActivityViewModel;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = "VideoActivity";
    ActivityVideoBinding binding;

    //记录当前“选中/正在播放”的列表条目索引
    private int lastSelectedPosition = -1;

    private VideoActivityViewModel videoActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        //获得viewModel 用于获取livedata 进行数据感知
        videoActivityViewModel = new ViewModelProvider(this).get(VideoActivityViewModel.class);
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        //从之前发的intent获得数据
        TiktokBean data = (TiktokBean)intent.getSerializableExtra("video");
        LogUtil.d(TAG, "onCreate: data = " + data.getAuthorName());
        initView(data);
    }

    /**
     * 初始化相关UI
     * @param data
     */
    private void initView(TiktokBean data) {
        //视频流布局采用RecyclerView + Exoplayer 实现
        //自定义LayoutManager
        MyLayoutManager lm = new MyLayoutManager(this, OrientationHelper.VERTICAL,false);
        binding.videoRecyclerView.setLayoutManager(lm);
        videoAdapter adapter = new videoAdapter();
        adapter.setData(DyDataInstance.getInstance().getTiktokDataList());
        binding.videoRecyclerView.setAdapter(adapter);

        //当item中的评论图标被点击，弹出评论框
        adapter.setOnOpenCommentsListener(t -> {
            CommentsBottomSheetDialogFragment.newInstance(t)
                    .show(getSupportFragmentManager(), "comments");
        });
        //设置点赞/收藏图标被点击的点击事件
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

        //获得之前点击的位置
        int index = getIntent().getIntExtra("position", -1);
        //如果传过来非法位置，根据传过来的data 去找正确的位置
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

            /**
             * 当滑动，要离开当前的item时，触发回调
             * @param isNext
             * @param position
             */
            @Override
            public void onPageRelease(boolean isNext, int position) {
                videoViewHolder holder = (videoViewHolder) binding.videoRecyclerView.findViewHolderForAdapterPosition(position);
                if (holder != null) {
                    //调用自定义视频控件的暂停播放方法
                    holder.videoPlayer.pauseForScroll();
                }
                //将其重置为-1，因为还没到下一个item
                lastSelectedPosition = -1;
            }

            /**
             * 当滑动对应页的90%时触发，即选中相应item
             * @param position
             * @param isBottom
             */
            @Override
            public void onPageSelected(int position, boolean isBottom) {
                videoViewHolder holder = (videoViewHolder) binding.videoRecyclerView.findViewHolderForAdapterPosition(position);
                if (holder != null) {
                    //开始播放
                    holder.videoPlayer.startPlayLogic();
                    //将其记录为对应的数据位置
                    lastSelectedPosition = position;
                } else {
                    //如果holder没有找到，即没完成绑定或者没创建。尝试延迟、多次播放
                    tryStartPlayAt(position, 5);
                }
                //寻找下一个item 用于预加载
                int next = position + 1;
                videoViewHolder nextHolder = (videoViewHolder) binding.videoRecyclerView.findViewHolderForAdapterPosition(next);
                if (nextHolder != null) {
                    //调用自定义视频组件的预加载方法
                    nextHolder.videoPlayer.preload();
                }
            }
        });
        //当发布评论\点赞\收藏 数据发生改变。通过livedata进行数据感知，更新ui
        videoActivityViewModel.getTikodata().observe(this, new Observer<TiktokBean>() {
            @Override
            public void onChanged(TiktokBean tiktokBean) {
                //获得更新的item位置，一般就是之前滑动时所获得的选中位置
                int pos = lastSelectedPosition;
                if (pos < 0) {
                    //如果pos小于0了 就说明lastSelectedPosition赋值出现点问题了 得重新找一下
                    try {
                        RecyclerView.LayoutManager lm2 = binding.videoRecyclerView.getLayoutManager();
                        if (lm2 instanceof LinearLayoutManager) {
                            pos = ((LinearLayoutManager) lm2).findFirstVisibleItemPosition();
                        }
                    } catch (Throwable ignored) {}
                }
                //pos为正常值 触发刷新ui操作
                if (pos >= 0) {
                    adapter.notifyItemChanged(pos, "actions");
                }
            }
        });
    }

    /**
     * 当viewholder没有找到的时候 调用这个方法，用于多次延迟等待，等待ui层把viewholder bind完毕 或者 creat出来
     * @param position
     * @param retries
     */
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
        //当activity停止时 暂停所有播放器播放
        stopAllPlayers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当activity销毁时，释放所有播放器实例
        releaseAllPlayers();
    }

    /**
     * 暂停RecyclerView中 每个viewholder的播放器
     */
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

    /**
     * 释放RecyclerView中 每个viewholder的播放器实例
     */
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
