package com.example.dydemo.Recommend.CustomView;


import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.dydemo.R;
import com.example.dydemo.until.LogUtil;

public class AdvancedWaterfallItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "AdvancedWaterfallItemDecoration";
    // 列数 - 控制瀑布流的列数
    private int spanCount;
    // 水平间距 - item之间的水平间距（像素）
    private int horizontalSpacing;
    // 垂直间距 - item之间的垂直间距（像素）
    private int verticalSpacing;


    public AdvancedWaterfallItemDecoration(int spanCount, int horizontalSpacing, int verticalSpacing) {
        this.spanCount = spanCount;
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // 获取当前item的位置
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams)view.getLayoutParams();
        int position = params.getSpanIndex();

        // 计算当前item所在的列
        int column = position % spanCount;

        // outRect.left: 左边距
        // outRect.right: 右边距

        if (column == 0) {
            LogUtil.d(TAG, "getItemOffsets: position = " + position);
            LogUtil.d(TAG, "getItemOffsets: column = " + column);

            TextView textView = view.findViewById(R.id.rv_item_user_name);
            LogUtil.d(TAG, "getItemOffsets: name = " + textView.getText().toString());
            // 第一列：左边无间距，右边有半间距
            outRect.left = 0;
            outRect.right = horizontalSpacing / 2;
        } else if (column == spanCount - 1) {
            // 最后一列：左边有半间距，右边无间距
            LogUtil.d(TAG, "getItemOffsets: position = " + position);
            LogUtil.d(TAG, "getItemOffsets: column = " + column);
            outRect.left = horizontalSpacing / 2;
            outRect.right = 0;
        } else {
            // 中间列：左右都有半间距
            outRect.left = horizontalSpacing / 2;
            outRect.right = horizontalSpacing / 2;
        }

        // outRect.top: 上边距
        // outRect.bottom: 下边距

        // 第一行：顶部无间距
        if (position < spanCount) {
            outRect.top = 0;
        } else {
            // 其他行：顶部有间距
            outRect.top = verticalSpacing;
        }

        // 所有item底部都有间距
        outRect.bottom = verticalSpacing;
    }
}
