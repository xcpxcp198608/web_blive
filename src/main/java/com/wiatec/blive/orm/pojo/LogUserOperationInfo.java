package com.wiatec.blive.orm.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author patrick
 */
public class LogUserOperationInfo extends BaseInfo {

    public static final int TYPE_INSERT = 1;
    public static final int TYPE_DELETE = 2;
    public static final int TYPE_SELECT = 3;
    public static final int TYPE_UPDATE = 4;

    private int userId;
    private int type;
    private String description;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LogUserOperationInfo{" +
                "userId=" + userId +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
