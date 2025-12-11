package com.example.dydemo.Recommend.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.dydemo.R;
import com.example.dydemo.bean.TiktokBean;
import com.example.dydemo.Recommend.view.rvFlowViewHolder;

import java.util.List;

    public class rvFlowAdapter extends RecyclerView.Adapter<rvFlowViewHolder> {
    private List<TiktokBean> mData;

    public rvFlowAdapter(List<TiktokBean> data) {
        this.mData = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, TiktokBean data, int position);
    }
    private OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
    @NonNull
    @Override
    public rvFlowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_flow, parent, false);
        return new rvFlowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rvFlowViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //设置其item 不占有所有高度，如果最后一个item单独一行显示，不设置这个就占整行了
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams)holder.itemView.getLayoutParams();
        params.setFullSpan(false);
        holder.itemView.setLayoutParams(params);
        //设置点击时间的回调
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, mData.get(position), position);
                }
            }
        });
        TiktokBean data = mData.get(position);
        //调用其viewholder的bind方法更新ui
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
