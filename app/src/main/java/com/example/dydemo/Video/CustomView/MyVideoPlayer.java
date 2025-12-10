package com.example.dydemo.Video.CustomView;

/**
 * 轻量视频播放组件，基于 ExoPlayer 封装。
 * 功能要点：
 * - 懒初始化 PlayerView/ExoPlayer，避免不必要的资源占用
 * - startPlayLogic：绑定 MediaItem 并启动播放；支持循环播放
 * - pause/pauseForScroll：区分用户手动暂停与滚动导致的暂停
 * - preload：预加载下一条媒体，提升翻页后的启动速度
 * - releasePlayer：及时释放以避免泄漏；在 onDetachedFromWindow 做安全释放
 * - 叠加层：缩略图与开始按钮的显示/隐藏与播放状态联动
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;

public class MyVideoPlayer extends FrameLayout {
    /** 播放器视图，承载视频画面 */
    private PlayerView playerView;
    /** ExoPlayer 实例 */
    private ExoPlayer exoPlayer;
    /** 封面缩略图视图（暂停/未就绪时可见） */
    private ImageView thumbView;
    /** 中心开始按钮（暂停时可见） */
    private ImageView startButton;
    /** 预留标题视图（兼容外部接口） */
    private View titleView;
    /** 预留返回按钮视图（兼容外部接口） */
    private View backButton;
    /** 预留全屏按钮视图（兼容外部接口） */
    private View fullscreenButton;
    /** 待播放的媒体地址 */
    private String mUrl;
    /** 是否循环播放 */
    private boolean looping;
    /** 当前已绑定的媒体地址，用于去重判断 */
    private String currentUrl;
    /** 是否由用户主动触发的暂停（与滚动暂停区分） */
    private boolean pausedByUser;

    public MyVideoPlayer(Context context) {
        super(context);
    }

    public MyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initIfNeeded() {
        // 懒初始化：只有首次使用时才创建子视图与监听，降低开销
        if (playerView != null) return;
        try {
            playerView = new PlayerView(getContext());
            playerView.setUseController(false);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView(playerView, lp);

            startButton = new ImageView(getContext());
            startButton.setImageResource(android.R.drawable.ic_media_play);
            LayoutParams slp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            slp.gravity = Gravity.CENTER;
            addView(startButton, slp);
            // 开始按钮：点击恢复播放，隐藏按钮与缩略图
            startButton.setOnClickListener(v -> {
                if (exoPlayer != null) {
                    exoPlayer.setPlayWhenReady(true);
                    startButton.setVisibility(View.INVISIBLE);
                    if (thumbView != null) thumbView.setVisibility(View.INVISIBLE);
                    pausedByUser = false;
                }
            });

            titleView = new View(getContext());
            backButton = new View(getContext());
            fullscreenButton = new View(getContext());

            // 点击播放器区域：在播放/暂停间切换（无控制条）
            setOnClickListener(v -> {
                if (exoPlayer == null) return;
                if (exoPlayer.isPlaying()) {
                    pause();
                } else {
                    exoPlayer.setPlayWhenReady(true);
                    if (startButton != null) startButton.setVisibility(View.INVISIBLE);
                    if (thumbView != null) thumbView.setVisibility(View.INVISIBLE);
                }
            });
        } catch (Throwable t) {
            // 兜底：避免构造期抛出异常导致崩溃
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initIfNeeded();
    }

    public void setThumbImageView(ImageView view) {
        // 设置/替换封面缩略图，叠加在播放器之上
        initIfNeeded();
        if (thumbView != null) {
            removeView(thumbView);
        }
        thumbView = view;
        LayoutParams tlp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(thumbView, tlp);
        if (startButton != null) {
            // 保证开始按钮位于最上层
            startButton.bringToFront();
        }
    }

    public void setUp(String url, boolean cacheWithPlay, Object cachePath, Object header, String title) {
        // 记录待播放的媒体地址（其余参数保留兼容）
        mUrl = url;
    }

    public void startPlayLogic() {
        // 启动播放逻辑：创建/复用 ExoPlayer，绑定 MediaItem 并开始播放
        initIfNeeded();
        if (mUrl == null || mUrl.isEmpty()) return;
        if (playerView == null) {
            playerView = new PlayerView(getContext());
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView(playerView, lp);
        }
        if (exoPlayer == null) {
            exoPlayer = new ExoPlayer.Builder(getContext()).build();
            playerView.setPlayer(exoPlayer);
            exoPlayer.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    // 就绪且正在播放：隐藏覆盖层
                    if (state == Player.STATE_READY && exoPlayer.getPlayWhenReady()) {
                        startButton.setVisibility(View.INVISIBLE);
                        if (thumbView != null) thumbView.setVisibility(View.INVISIBLE);
                    }
                    // 播放结束：如需循环则重头播放
                    if (state == Player.STATE_ENDED) {
                        if (looping) {
                            exoPlayer.seekTo(0);
                            exoPlayer.play();
                        }
                    }
                }
            });
        } else {
            // 复用场景：同 URL 且非用户暂停，直接恢复播放并隐藏覆盖层
            if (currentUrl != null && currentUrl.equals(mUrl)) {
                if (pausedByUser) {
                    return;
                }
                exoPlayer.setPlayWhenReady(true);
                if (startButton != null) startButton.setVisibility(View.INVISIBLE);
                if (thumbView != null) thumbView.setVisibility(View.INVISIBLE);
                return;
            }
        }
        // 绑定媒体并开始播放
        MediaItem item = MediaItem.fromUri(mUrl);
        exoPlayer.setMediaItem(item);
        exoPlayer.setRepeatMode(looping ? Player.REPEAT_MODE_ONE : Player.REPEAT_MODE_OFF);
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.prepare();
        currentUrl = mUrl;
    }

    public void pause() {
        // 用户主动暂停：记录标记并显示开始按钮
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
            startButton.setVisibility(View.VISIBLE);
            pausedByUser = true;
        }
    }

    public void releasePlayer() {
        // 释放播放器与解绑视图，防止泄漏
        if (exoPlayer != null) {
            playerView.setPlayer(null);
            exoPlayer.release();
            exoPlayer = null;
        }
        pausedByUser = false;
    }

    public void pauseForScroll() {
        // 列表滚动导致的暂停：不记录为用户暂停，保持后续可自动恢复
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
            if (startButton != null) startButton.setVisibility(View.VISIBLE);
            if (thumbView != null) thumbView.setVisibility(View.VISIBLE);
            pausedByUser = false;
        }
    }

    public void preload() {
        // 预加载媒体：prepare 但不播放，用于下一页提前就绪
        initIfNeeded();
        if (mUrl == null || mUrl.isEmpty()) return;
        if (playerView == null) {
            playerView = new PlayerView(getContext());
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView(playerView, lp);
        }
        if (exoPlayer == null) {
            exoPlayer = new ExoPlayer.Builder(getContext()).build();
            playerView.setPlayer(exoPlayer);
        } else {
            if (currentUrl != null && currentUrl.equals(mUrl)) {
                exoPlayer.setPlayWhenReady(false);
                if (startButton != null) startButton.setVisibility(View.VISIBLE);
                return;
            }
        }
        // 绑定媒体但不播放
        MediaItem item = MediaItem.fromUri(mUrl);
        exoPlayer.setMediaItem(item);
        exoPlayer.setRepeatMode(looping ? Player.REPEAT_MODE_ONE : Player.REPEAT_MODE_OFF);
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.prepare();
        startButton.setVisibility(View.VISIBLE);
        currentUrl = mUrl;
    }

    public View getTitleTextView() {
        // 兼容接口：如未设置则创建占位视图
        initIfNeeded();
        if (titleView == null) {
            titleView = new View(getContext());
        }
        return titleView;
    }

    public View getBackButton() {
        // 兼容接口：如未设置则创建占位视图
        initIfNeeded();
        if (backButton == null) {
            backButton = new View(getContext());
        }
        return backButton;
    }

    public View getFullscreenButton() {
        // 兼容接口：如未设置则创建占位视图
        initIfNeeded();
        if (fullscreenButton == null) {
            fullscreenButton = new View(getContext());
        }
        return fullscreenButton;
    }

    public ImageView getStartButton() {
        // 兼容接口：如未设置则创建占位视图
        initIfNeeded();
        if (startButton == null) {
            startButton = new ImageView(getContext());
        }
        return startButton;
    }

    public void setLooping(boolean loop) {
        // 设置循环播放并同步到播放器
        looping = loop;
        if (exoPlayer != null) {
            exoPlayer.setRepeatMode(looping ? Player.REPEAT_MODE_ONE : Player.REPEAT_MODE_OFF);
        }
    }

    public void setAutoFullWithSize(boolean b) {}

    public void setReleaseWhenLossAudio(boolean b) {}

    public void setPlayTag(String tag) {}

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 安全释放策略：当父 RecyclerView 正在滚动或 Activity 正在结束/销毁时释放播放器
        boolean shouldRelease = false;
        try {
            android.view.ViewParent parent = getParent();
            if (parent instanceof androidx.recyclerview.widget.RecyclerView) {
                int state = ((androidx.recyclerview.widget.RecyclerView) parent).getScrollState();
                shouldRelease = state != androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
            }
            android.content.Context ctx = getContext();
            if (ctx instanceof android.app.Activity) {
                android.app.Activity a = (android.app.Activity) ctx;
                boolean finishing = a.isFinishing();
                boolean destroyed = false;
                try {
                    if (android.os.Build.VERSION.SDK_INT >= 17) destroyed = a.isDestroyed();
                } catch (Throwable ignored2) {}
                if (finishing || destroyed) shouldRelease = true;
            }
        } catch (Throwable ignored) {}
        if (shouldRelease) {
            releasePlayer();
        }
    }
}
