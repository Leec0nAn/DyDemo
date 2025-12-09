package com.example.dydemo.bean;

import java.io.Serializable;

public class CommentBean implements Serializable {
    private String id;
    private String content;
    private long publishTime;
    private String publishIp;
    private CommentUser user;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getPublishTime() { return publishTime; }
    public void setPublishTime(long publishTime) { this.publishTime = publishTime; }

    public String getPublishIp() { return publishIp; }
    public void setPublishIp(String publishIp) { this.publishIp = publishIp; }

    public CommentUser getUser() { return user; }
    public void setUser(CommentUser user) { this.user = user; }
}
