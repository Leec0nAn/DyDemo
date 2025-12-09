package com.example.dydemo.customView;

import android.content.Context;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyLayoutManager extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener {
    private int mDrift;//位移，用来判断移动方向

    private PagerSnapHelper mPagerSnapHelper;
    private OnViewPagerListener mOnViewPagerListener;
    private RecyclerView mRecyclerView;
    private boolean isScrolling;

    public MyLayoutManager(Context context) {
        super(context);
    }

    public MyLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        mPagerSnapHelper = new PagerSnapHelper();
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {

        view.addOnChildAttachStateChangeListener(this);
        mPagerSnapHelper.attachToRecyclerView(view);
        mRecyclerView = view;
        super.onAttachedToWindow(view);
    }
//当Item添加进来了  调用这个方法

    //
    @Override
    public void onChildViewAttachedToWindow(@NonNull View view) {
    }

    public void setOnViewPagerListener(OnViewPagerListener mOnViewPagerListener) {
        this.mOnViewPagerListener = mOnViewPagerListener;
    }

    @Override
    public void onScrollStateChanged(int state) {
        isScrolling = state != RecyclerView.SCROLL_STATE_IDLE;
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
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
//暂停播放操作
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
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }
}

