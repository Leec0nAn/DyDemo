package com.example.dydemo.Recommend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.dydemo.Recommend.Activity.MainActivity;
import com.example.dydemo.Video.Activity.VideoActivity;
import com.example.dydemo.Recommend.Adapter.rvFlowAdapter;
import com.example.dydemo.bean.TiktokBean;
import com.example.dydemo.Recommend.CustomView.AdvancedWaterfallItemDecoration;
import com.example.dydemo.databinding.FragmentMainActivityMainBinding;
import com.example.dydemo.DataManager.DyDataInstance;
import com.example.dydemo.until.LogUtil;
import com.example.dydemo.until.viewUntil;

public class MainActivity_MainFragment extends Fragment implements rvFlowAdapter.OnItemClickListener {
    //定义推荐双列瀑布流的item间隔: 垂直方向的间隔,水平方向间隔
    private static final int HORIZONTAL_SPACING_DP = 5;
    private static final int VERTICAL_SPACING_DP = 5;
    private rvFlowAdapter adapter;

    FragmentMainActivityMainBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d(MainActivity.TAG,"MainActivity_MainFragment onCreateView!!!");
        binding = FragmentMainActivityMainBinding.inflate(inflater,container,false);
        binding.rvFlowLayout.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        int horizontalSpacingPx = viewUntil.dpToPx(HORIZONTAL_SPACING_DP, requireContext());
        int verticalSpacingPx = viewUntil.dpToPx(VERTICAL_SPACING_DP, requireContext());
        //使用自定义的ItemDecoration
        AdvancedWaterfallItemDecoration decoration = new AdvancedWaterfallItemDecoration(
                2,
                horizontalSpacingPx,
                verticalSpacingPx
        );
        adapter = new rvFlowAdapter(DyDataInstance.getInstance().getTiktokDataList());
        adapter.setOnItemClickListener(this);
        binding.rvFlowLayout.addItemDecoration(decoration);
        binding.rvFlowLayout.setAdapter(adapter);
        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        //当从视频内流返回时，有可能数据发生了变化，比如点赞数。要对item进行ui更新的操作
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    /**
     * 双列推荐流点击item的回调方法 当点击时，跳转相应的视频内流
     * @param view
     * @param data
     * @param position
     */
    @Override
    public void onItemClick(View view, TiktokBean data, int position) {
        Intent intent = new Intent(requireContext(), VideoActivity.class);
        intent.putExtra("video", data);
        //将位置传输过去，到内流的RecyclerView 直接划过去
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
