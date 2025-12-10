package com.example.dydemo.Comment.view;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dydemo.R;
import com.example.dydemo.bean.CommentBean;
import com.example.dydemo.bean.CommentUser;
import com.example.dydemo.until.NumberFormatUtil;

public class commentViewHolder extends RecyclerView.ViewHolder {
    static final String TAG = "VideoActivity";
    TextView comment_user_name;
    TextView comment_content;
    TextView comment_ip_date;
    ImageView comment_user_image;

    public commentViewHolder(@NonNull View itemView) {
        super(itemView);
        comment_user_name = itemView.findViewById(R.id.comment_user_name);
        comment_content = itemView.findViewById(R.id.comment_cotent);
        comment_ip_date = itemView.findViewById(R.id.comment_date_ip);
        comment_user_image = itemView.findViewById(R.id.comment_user_image);
    }
    public void bind(CommentBean data){
        CommentUser user = data.getUser();
        comment_user_name.setText(user.getUserName());
        String timeStr = NumberFormatUtil.formatRelativeTime(data.getPublishTime());
        String region = NumberFormatUtil.ipToRegion(data.getPublishIp());
        comment_ip_date.setText(timeStr + " · IP 属地 " + region);
        comment_content.setText(data.getContent());
        Glide.with(comment_user_image.getContext())
                .load(user.getAvatarUrl())
                .circleCrop()
                .into(comment_user_image);
    }
}
