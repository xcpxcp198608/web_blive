package com.wiatec.blive.orm.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author patrick
 */
public class LogLiveCommentInfo {

    private int id;
    private int channelId;
    private int groupId;
    private int watchUserId;
    private String comment;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getWatchUserId() {
        return watchUserId;
    }

    public void setWatchUserId(int watchUserId) {
        this.watchUserId = watchUserId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LogLiveCommentInfo{" +
                "id=" + id +
                ", channelId=" + channelId +
                ", groupId=" + groupId +
                ", watchUserId=" + watchUserId +
                ", comment='" + comment + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
