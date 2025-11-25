package com.example.dydemo.model;

import java.io.Serializable;

public class DyVideo implements Serializable {
    private String title;
    private int video_url;//使用本地的视频资源

    private int img_url;//使用本地的图片资源

    private String user_name;
    private int user_img;//使用本地的图片资源
    private int like_count;
    private int comment_count;

    private int share_count;

    private int favorite_count;

    public DyVideo(String title, int video_url, int img_url, String user_name, int user_img, int like_count, int comment_count, int share_count, int favorite_count) {
        this.title = title;
        this.video_url = video_url;
        this.img_url = img_url;
        this.user_name = user_name;
        this.user_img = user_img;
        this.like_count = like_count;
        this.comment_count = comment_count;
        this.share_count = share_count;
        this.favorite_count = favorite_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVideo_url() {
        return video_url;
    }

    public void setVideo_url(int video_url) {
        this.video_url = video_url;
    }

    public int getImg_url() {
        return img_url;
    }

    public void setImg_url(int img_url) {
        this.img_url = img_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_img() {
        return user_img;
    }

    public void setUser_img(int user_img) {
        this.user_img = user_img;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }
}
