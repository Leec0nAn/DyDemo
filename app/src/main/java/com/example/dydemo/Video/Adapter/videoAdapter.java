package com.example.dydemo.Video.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dydemo.R;
import com.example.dydemo.bean.TiktokBean;
import com.example.dydemo.Video.view.videoViewHolder;

import java.util.List;

public class videoAdapter extends RecyclerView.Adapter<videoViewHolder> {
    public interface OnOpenCommentsListener {
        void onOpenComments(TiktokBean data);
    }
    public interface OnToggleActionListener {
        void onToggleLike(TiktokBean data);
        void onToggleCollect(TiktokBean data);
    }
    private List<TiktokBean> data;
    private OnOpenCommentsListener onOpenCommentsListener;
    private OnToggleActionListener onToggleActionListener;

    public void setData(List<TiktokBean> data) {
        this.data = data;
    }

    public void setOnToggleActionListener(OnToggleActionListener l) {
        this.onToggleActionListener = l;
    }

    @NonNull
    @Override
    public videoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new videoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull videoViewHolder holder, int position) {
        holder.bindData(data.get(position), onOpenCommentsListener, onToggleActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull videoViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads != null && !payloads.isEmpty()) {
            holder.bindActionsOnly(data.get(position));
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnOpenCommentsListener(OnOpenCommentsListener listener) {
        this.onOpenCommentsListener = listener;
    }
}
