package com.example.dydemo.Comment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dydemo.R;
import com.example.dydemo.bean.CommentBean;
import com.example.dydemo.Comment.view.commentViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<commentViewHolder> {
    private List<CommentBean> data = new ArrayList<>();

    public void setData(List<CommentBean> list) {
        data.clear();
        if (list != null) data.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 用于添加评论到评论列表顶部
     * @param c
     */
    public void addToTop(CommentBean c) {
        if (c == null) return;
        data.add(0, c);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public commentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new commentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull commentViewHolder holder, int position) {
        CommentBean c = data.get(position);
        holder.bind(c);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
