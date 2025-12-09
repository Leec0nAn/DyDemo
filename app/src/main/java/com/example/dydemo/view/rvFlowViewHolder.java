package com.example.dydemo.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dydemo.R;
import com.example.dydemo.bean.TiktokBean;
import com.example.dydemo.until.LogUtil;

public class rvFlowViewHolder extends RecyclerView.ViewHolder {
    static final String TAG = "MainActivity";
    ImageView rv_item_image;
    ImageView rv_item_user;
    TextView rv_item_title;
    TextView rv_item_username;
    TextView rv_item_like;
    public rvFlowViewHolder(@NonNull View itemView) {
        super(itemView);
        rv_item_image = itemView.findViewById(R.id.rv_item_image);
        rv_item_user = itemView.findViewById(R.id.rv_item_user_image);
        rv_item_title = itemView.findViewById(R.id.rv_item_title);
        rv_item_username = itemView.findViewById(R.id.rv_item_user_name);
        rv_item_like = itemView.findViewById(R.id.rv_item_like_tv);
    }

    public void bind(TiktokBean data) {
        if (data == null) return;
        LogUtil.d(TAG,data.getCoverImgUrl());
        Glide.with(rv_item_image.getContext())
                        .load(data.getCoverImgUrl())
                        .error(R.mipmap.default_image)
                        .into(rv_item_image);
        rv_item_title.setText(data.getTitle());
        rv_item_username.setText(data.getAuthorName());
        rv_item_like.setText(String.valueOf(data.getLikeCount()));
        Glide.with(rv_item_user.getContext())
                .load(data.getAuthorImgUrl())
                .circleCrop()
                .into(rv_item_user);
    }
}
