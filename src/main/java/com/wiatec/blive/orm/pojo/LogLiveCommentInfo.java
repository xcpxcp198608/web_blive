package com.wiatec.blive.orm.pojo;


/**
 * @author patrick
 */
public class LogLiveCommentInfo extends BaseInfo {

    private int channelId;
    private int groupId;
    private int watchUserId;
    private String comment;


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

    @Override
    public String toString() {
        return "LogLiveCommentInfo{" +
                "channelId=" + channelId +
                ", groupId=" + groupId +
                ", watchUserId=" + watchUserId +
                ", comment='" + comment + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
