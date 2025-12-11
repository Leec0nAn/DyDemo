package com.example.dydemo.Video.CustomView;
import android.content.Context;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyLayoutManager extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener {
    // 滚动位移，用于判断滚动方向（>=0 下滑，<0 上滑）
    private int mDrift;

    // 页面吸附辅助类，实现滚动停止后的整页吸附
    private PagerSnapHelper mPagerSnapHelper;
    // 翻页事件回调接口，通知业务层选中/释放等事件
    private OnViewPagerListener mOnViewPagerListener;
    // 当前绑定的 RecyclerView，用于获取父容器高度与状态
    private RecyclerView mRecyclerView;
    // 是否处于滚动中，配合离屏释放逻辑使用
    private boolean isScrolling;

    public MyLayoutManager(Context context) {
        super(context);
    }

    public MyLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        // 启用分页吸附
        mPagerSnapHelper = new PagerSnapHelper();
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        // 绑定子视图附着/分离监听
        view.addOnChildAttachStateChangeListener(this);
        //绑定吸附辅助
        mPagerSnapHelper.attachToRecyclerView(view);
        mRecyclerView = view;
        super.onAttachedToWindow(view);
    }


    @Override
    public void onChildViewAttachedToWindow(@NonNull View view) {
    }

    public void setOnViewPagerListener(OnViewPagerListener mOnViewPagerListener) {
        // 注册翻页事件监听
        this.mOnViewPagerListener = mOnViewPagerListener;
    }

    @Override
    public void onScrollStateChanged(int state) {
        // 标记当前是否在滑动，用于控制离屏释放
        isScrolling = state != RecyclerView.SCROLL_STATE_IDLE;
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                // 计算可见比例最大的子项作为当前页
                if (mOnViewPagerListener != null) {
                    int parentHeight = mRecyclerView != null ? mRecyclerView.getHeight() : 0;
                    float maxRatio = 0f;
                    int targetPosition = -1;
                    for (int i = 0; i < getChildCount(); i++) {
                        View child = getChildAt(i);
                        int top = getDecoratedTop(child);
                        int bottom = getDecoratedBottom(child);
                        int visibleTop = Math.max(top, 0);
                        int visibleBottom = Math.min(bottom, parentHeight);
                        int visibleHeight = Math.max(0, visibleBottom - visibleTop);
                        float ratio = parentHeight > 0 ? (float) visibleHeight / (float) parentHeight : 0f;
                        if (ratio > maxRatio) {
                            maxRatio = ratio;
                            targetPosition = getPosition(child);
                        }
                    }
                    // 屏幕可见占比大于90%时 认为是选中页，触发回调
                    if (targetPosition >= 0 && maxRatio >= 0.90f) {
                        mOnViewPagerListener.onPageSelected(targetPosition, targetPosition == getItemCount() - 1);
                    }
                }
                break;
        }
        super.onScrollStateChanged(state);
    }

    @Override
    public void onChildViewDetachedFromWindow(@NonNull View view) {
        // 在滚动过程中触发释放回调，用于暂停离开的item
        if (!isScrolling) return;
        if (mDrift >= 0) {
            if (mOnViewPagerListener != null)
                mOnViewPagerListener.onPageRelease(true, getPosition(view));
        } else {
            if (mOnViewPagerListener != null)
                mOnViewPagerListener.onPageRelease(false, getPosition(view));
        }
    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 记录本次竖向位移，供方向判断
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public boolean canScrollVertically() {
        // 开启竖向滚动能力
        return true;
    }
}

