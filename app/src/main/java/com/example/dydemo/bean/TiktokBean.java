package com.example.dydemo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TiktokBean implements Serializable {

    /**
     * authorImgUrl : https://p3-dy.byteimg.com/aweme/100x100/1e19c00014b66991215ba.jpeg
     * authorName : 相相啊
     * authorSex : 0
     * coverImgUrl : http://p9-dy.byteimg.com/large/tos-cn-p-0015/13f8180ea2bd44779449b82702b4e47b.jpeg
     * createTime : 1571047431000
     * dynamicCover : https://p9-dy.byteimg.com/obj/tos-cn-p-0015/9991ae92508340ae9b71f132b52a6c70
     * filterMusicNameStr : 青柠（手机摄影）@卡点音乐模版08
     * filterTitleStr : 昨天刷到抖音今天就马不停蹄去买了好多人排队#南洋大师...
     * filterUserNameStr : 相相啊
     * formatLikeCountStr :
     * formatPlayCountStr : 82
     * formatTimeStr : 2019.10.14
     * likeCount : 301
     * musicAuthorName : 青柠（手机摄影）
     * musicImgUrl : https://p3-dy.byteimg.com/aweme/100x100/2dce4000796963dec1a63.jpeg
     * musicName : 卡点音乐模版08
     * playCount : 82
     * title : 昨天刷到抖音今天就马不停蹄去买了  好多人排队  #南洋大师傅 @抖音小助手
     * type : 0
     * videoDownloadUrl : https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fd00000bmi4fdabr0flkdiivurg&line=0&ratio=540p&watermark=1&media_type=4&vr_type=0&improve_bitrate=0&logo_name=aweme
     * videoHeight : 720
     * videoPlayUrl : http://v1-dy.ixigua.com/4413f7d529ce8e0d0a60ce24e84ab535/5da4a52a/video/m/220b506942d480c47ada07abbc83b49a4581163dc7510000041d7a9a627d/?a=1128&br=1570&cr=0&cs=0&dr=0&ds=6&er=&l=20191014234102010155051013879290&lr=&rc=anhsZ28zZWc3cDMzM2kzM0ApZ2YzOjo5NDszNzY6ZDo5O2dvc2ZoY29eY2ZfLS1hLTBzcy4xM2IuMi1jLy8zMC8wMzA6Yw%3D%3D
     * videoWidth : 720
     * collectCount : 1234
     * shareCount : 567
     * commentCount : 890
     * isLiked : false
     * isCollected : false
     *
     */

    private String authorImgUrl;
    private String authorName;
    private int authorSex;
    private String coverImgUrl;
    private long createTime;
    private String dynamicCover;
    private String filterMusicNameStr;
    private String filterTitleStr;
    private String filterUserNameStr;
    private String formatLikeCountStr;
    private String formatPlayCountStr;
    private String formatTimeStr;
    private int likeCount;
    private String musicAuthorName;
    private String musicImgUrl;
    private String musicName;
    private int playCount;
    private String title;
    private int type;
    private String videoDownloadUrl;
    private int videoHeight;
    private String videoPlayUrl;
    private int videoWidth;

    // 新增的属性
    private int collectCount;     // 收藏数
    private int shareCount;       // 转发数
    private int commentCount;     // 评论数
    private boolean isLiked;      // 是否已点赞
    private boolean isCollected;  // 是否已收藏
    private List<CommentBean> comments;

    public String getAuthorImgUrl() {
        return authorImgUrl;
    }

    public void setAuthorImgUrl(String authorImgUrl) {
        this.authorImgUrl = authorImgUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getAuthorSex() {
        return authorSex;
    }

    public void setAuthorSex(int authorSex) {
        this.authorSex = authorSex;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDynamicCover() {
        return dynamicCover;
    }

    public void setDynamicCover(String dynamicCover) {
        this.dynamicCover = dynamicCover;
    }

    public String getFilterMusicNameStr() {
        return filterMusicNameStr;
    }

    public void setFilterMusicNameStr(String filterMusicNameStr) {
        this.filterMusicNameStr = filterMusicNameStr;
    }

    public String getFilterTitleStr() {
        return filterTitleStr;
    }

    public void setFilterTitleStr(String filterTitleStr) {
        this.filterTitleStr = filterTitleStr;
    }

    public String getFilterUserNameStr() {
        return filterUserNameStr;
    }

    public void setFilterUserNameStr(String filterUserNameStr) {
        this.filterUserNameStr = filterUserNameStr;
    }

    public String getFormatLikeCountStr() {
        return formatLikeCountStr;
    }

    public void setFormatLikeCountStr(String formatLikeCountStr) {
        this.formatLikeCountStr = formatLikeCountStr;
    }

    public String getFormatPlayCountStr() {
        return formatPlayCountStr;
    }

    public void setFormatPlayCountStr(String formatPlayCountStr) {
        this.formatPlayCountStr = formatPlayCountStr;
    }

    public String getFormatTimeStr() {
        return formatTimeStr;
    }

    public void setFormatTimeStr(String formatTimeStr) {
        this.formatTimeStr = formatTimeStr;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getMusicAuthorName() {
        return musicAuthorName;
    }

    public void setMusicAuthorName(String musicAuthorName) {
        this.musicAuthorName = musicAuthorName;
    }

    public String getMusicImgUrl() {
        return musicImgUrl;
    }

    public void setMusicImgUrl(String musicImgUrl) {
        this.musicImgUrl = musicImgUrl;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVideoDownloadUrl() {
        return videoDownloadUrl;
    }

    public void setVideoDownloadUrl(String videoDownloadUrl) {
        this.videoDownloadUrl = videoDownloadUrl;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getVideoPlayUrl() {
        return videoPlayUrl;
    }

    public void setVideoPlayUrl(String videoPlayUrl) {
        this.videoPlayUrl = videoPlayUrl;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public List<CommentBean> getComments() {
        if (comments == null) comments = new ArrayList<>();
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

    public void addComment(CommentBean comment) {
        getComments().add(comment);
        increaseCommentCount();
    }

    // 获取格式化后的点赞数
    public String getFormattedLikeCount() {
        return formatCount(likeCount);
    }

    // 获取格式化后的收藏数
    public String getFormattedCollectCount() {
        return formatCount(collectCount);
    }

    // 获取格式化后的转发数
    public String getFormattedShareCount() {
        return formatCount(shareCount);
    }

    // 获取格式化后的评论数
    public String getFormattedCommentCount() {
        return formatCount(commentCount);
    }

    // 获取格式化后的播放量
    public String getFormattedPlayCount() {
        return formatCount(playCount);
    }

    // 数字格式化方法
    private String formatCount(int count) {
        if (count < 1000) {
            return String.valueOf(count);
        } else if (count < 10000) {
            return String.format("%.1fk", count / 1000.0);
        } else if (count < 1000000) {
            return String.format("%.1fw", count / 10000.0);
        } else {
            return String.format("%.1fm", count / 1000000.0);
        }
    }

    // 获取视频ID（从URL中提取）
    public String getVideoId() {
        if (videoDownloadUrl != null && videoDownloadUrl.contains("video_id=")) {
            String[] parts = videoDownloadUrl.split("video_id=");
            if (parts.length > 1) {
                String videoId = parts[1];
                if (videoId.contains("&")) {
                    videoId = videoId.substring(0, videoId.indexOf("&"));
                }
                return videoId;
            }
        }
        return null;
    }

    // 获取视频时长（这里可以自行添加属性或计算方法）
    public String getVideoDuration() {
        // 可以根据需要添加videoDuration属性，这里返回示例值
        return "0:15";
    }

    // 切换点赞状态
    public void toggleLike() {
        isLiked = !isLiked;
        if (isLiked) {
            likeCount++;
        } else {
            likeCount--;
        }
    }

    // 切换收藏状态
    public void toggleCollect() {
        isCollected = !isCollected;
        if (isCollected) {
            collectCount++;
        } else {
            collectCount--;
        }
    }

    // 增加分享数
    public void increaseShareCount() {
        shareCount++;
    }

    // 增加评论数
    public void increaseCommentCount() {
        commentCount++;
    }


}
