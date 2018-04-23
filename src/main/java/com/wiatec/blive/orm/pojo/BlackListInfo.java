package com.wiatec.blive.orm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author patrick
 */
public class BlackListInfo {

    private int id;
    private int userId;
    private int blackId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBlackId() {
        return blackId;
    }

    public void setBlackId(int blackId) {
        this.blackId = blackId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BlackListInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", blackId=" + blackId +
                ", createTime=" + createTime +
                '}';
    }
}
