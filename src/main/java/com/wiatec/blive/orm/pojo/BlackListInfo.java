package com.wiatec.blive.orm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author patrick
 */
public class BlackListInfo extends BaseInfo {

    private int userId;
    private int blackId;
    private String blackUsername;


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

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }


    @Override
    public String toString() {
        return "BlackListInfo{" +
                "userId=" + userId +
                ", blackId=" + blackId +
                ", blackUsername='" + blackUsername + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
