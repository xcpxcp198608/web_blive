package com.wiatec.blive.orm.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author patrick
 */
public class ChannelPurchaseInfo extends BaseInfo {

    private Integer channelId;
    private Integer viewerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiresTime;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getViewerId() {
        return viewerId;
    }

    public void setViewerId(Integer viewerId) {
        this.viewerId = viewerId;
    }

    public Date getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
    }

    @Override
    public String toString() {
        return "ChannelPurchaseInfo{" +
                "channelId=" + channelId +
                ", viewerId=" + viewerId +
                ", expiresTime=" + expiresTime +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
