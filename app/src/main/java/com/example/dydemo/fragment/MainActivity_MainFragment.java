package com.example.dydemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.dydemo.Activity.MainActivity;
import com.example.dydemo.R;
import com.example.dydemo.Activity.VideoActivity;
import com.example.dydemo.adapter.rvFlowAdapter;
import com.example.dydemo.bean.TiktokBean;
import com.example.dydemo.customView.AdvancedWaterfallItemDecoration;
import com.example.dydemo.databinding.FragmentMainActivityMainBinding;
import com.example.dydemo.model.DyDataInstance;
import com.example.dydemo.until.LogUtil;
import com.example.dydemo.until.viewUntil;

public class MainActivity_MainFragment extends Fragment implements rvFlowAdapter.OnItemClickListener {
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
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, TiktokBean data, int position) {
        Intent intent = new Intent(requireContext(), VideoActivity.class);
        intent.putExtra("video", data);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
