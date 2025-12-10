package com.example.dydemo.Video.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dydemo.R;
import com.example.dydemo.bean.TiktokBean;
import com.example.dydemo.Video.Adapter.videoAdapter;
import com.example.dydemo.Video.CustomView.MyVideoPlayer;
import com.example.dydemo.until.LogUtil;
import com.example.dydemo.until.NumberFormatUtil;

public class videoViewHolder extends RecyclerView.ViewHolder {
    static final String TAG = "VideoActivity";
    TextView videoTitle;
    TextView videoAuthor;
    TextView videoLikeNum;
    TextView videoCommentNum;
    TextView videoShareNum;
    TextView videoFavoriteNum;
    ImageView videoCommentImg;
    ImageView videoLikeImg;
    ImageView videoFavoriteImg;

    ImageView videoUserImg;

    public MyVideoPlayer videoPlayer;
    private String boundUrl;

    public videoViewHolder(@NonNull View itemView) {
        super(itemView);
        videoAuthor = itemView.findViewById(R.id.video_author);
        videoTitle = itemView.findViewById(R.id.video_title);
        videoLikeNum = itemView.findViewById(R.id.video_like_num);
        videoAuthor = itemView.findViewById(R.id.video_author);
        videoCommentNum = itemView.findViewById(R.id.video_comment_num);
        videoShareNum = itemView.findViewById(R.id.video_share_num);
        videoFavoriteNum = itemView.findViewById(R.id.video_favorite_num);
        videoPlayer = itemView.findViewById(R.id.video_player);
        videoCommentImg = itemView.findViewById(R.id.video_comment_img);
        videoUserImg = itemView.findViewById(R.id.video_user_img);
    }
    public void bindData(TiktokBean data, videoAdapter.OnOpenCommentsListener commentListener, videoAdapter.OnToggleActionListener toggleListener) {
        videoTitle.setText(data.getTitle());
        videoAuthor.setText(data.getAuthorName());
        Glide.with(videoUserImg.getContext())
                .load(data.getAuthorImgUrl())
                .error(R.mipmap.default_image)
                .circleCrop()
                .into(videoUserImg);
        if (videoLikeImg == null) videoLikeImg = itemView.findViewById(R.id.video_like_img);
        if (videoFavoriteImg == null) videoFavoriteImg = itemView.findViewById(R.id.video_favorite_img);

        videoLikeImg.setImageResource(data.isLiked() ? R.mipmap.liked : R.mipmap.like);
        videoFavoriteImg.setImageResource(data.isCollected() ? R.mipmap.collected : R.mipmap.collect);

        if (data.getLikeCount() == 0) videoLikeNum.setText("点赞");
        else videoLikeNum.setText(NumberFormatUtil.formatCountCn(data.getLikeCount()));
        if (data.getCommentCount() == 0)
            videoCommentNum.setText("评论");
        else
            videoCommentNum.setText(NumberFormatUtil.formatCountCn(data.getCommentCount()));
        if (data.getCollectCount() == 0) videoFavoriteNum.setText("收藏");
        else videoFavoriteNum.setText(NumberFormatUtil.formatCountCn(data.getCollectCount()));
        if (data.getShareCount() == 0)
            videoShareNum.setText("分享");
        else
            videoShareNum.setText(NumberFormatUtil.formatCountCn(data.getShareCount()));
        String url = data.getVideoDownloadUrl();
        if (boundUrl == null || (url != null && !url.equals(boundUrl))) {
            ImageView view = new ImageView(itemView.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(itemView.getContext())
                    .load(data.getCoverImgUrl())
                    .error(R.mipmap.default_image)
                    .into(view);
            videoPlayer.setThumbImageView(view);
            videoPlayer.setUp(url, true, null, null, "");
            boundUrl = url;
        }
        LogUtil.d(TAG, "getVideoPlayUrl: " + data.getVideoDownloadUrl());
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        videoPlayer.getBackButton().setVisibility(View.GONE);;
        videoPlayer.getFullscreenButton().setVisibility(View.GONE);
        videoPlayer.setPlayTag(TAG);
        videoPlayer.getStartButton().setVisibility(View.INVISIBLE);
        videoPlayer.setLooping(true);
        videoPlayer.setAutoFullWithSize(false);
        videoPlayer.setReleaseWhenLossAudio(false);
        if (videoCommentImg != null) {
            videoCommentImg.setOnClickListener(v -> {
                if (commentListener != null) commentListener.onOpenComments(data);
            });
        }

        if (videoLikeImg != null) {
            videoLikeImg.setOnClickListener(v -> {
                data.toggleLike();
                videoLikeImg.setImageResource(data.isLiked() ? R.mipmap.liked : R.mipmap.like);
                if (data.getLikeCount() == 0) videoLikeNum.setText("点赞");
                else videoLikeNum.setText(NumberFormatUtil.formatCountCn(data.getLikeCount()));
                if (toggleListener != null) toggleListener.onToggleLike(data);
            });
        }
        if (videoFavoriteImg != null) {
            videoFavoriteImg.setOnClickListener(v -> {
                data.toggleCollect();
                videoFavoriteImg.setImageResource(data.isCollected() ? R.mipmap.collected : R.mipmap.collect);
                if (data.getCollectCount() == 0) videoFavoriteNum.setText("收藏");
                else videoFavoriteNum.setText(NumberFormatUtil.formatCountCn(data.getCollectCount()));
                if (toggleListener != null) toggleListener.onToggleCollect(data);
            });
        }
    }

    public void bindActionsOnly(TiktokBean data) {
        if (videoLikeImg == null) videoLikeImg = itemView.findViewById(R.id.video_like_img);
        if (videoFavoriteImg == null) videoFavoriteImg = itemView.findViewById(R.id.video_favorite_img);
        if (videoLikeNum == null) videoLikeNum = itemView.findViewById(R.id.video_like_num);
        if (videoFavoriteNum == null) videoFavoriteNum = itemView.findViewById(R.id.video_favorite_num);
        videoLikeImg.setImageResource(data.isLiked() ? R.mipmap.liked : R.mipmap.like);
        videoFavoriteImg.setImageResource(data.isCollected() ? R.mipmap.collected : R.mipmap.collect);
        if (data.getLikeCount() == 0) videoLikeNum.setText("点赞");
        else videoLikeNum.setText(NumberFormatUtil.formatCountCn(data.getLikeCount()));
        if (data.getCollectCount() == 0) videoFavoriteNum.setText("收藏");
        else videoFavoriteNum.setText(NumberFormatUtil.formatCountCn(data.getCollectCount()));
    }
}
