package com.wiatec.blive.orm.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author patrick
 */
public class LogUserOperationInfo {

    public static final int TYPE_INSERT = 1;
    public static final int TYPE_DELETE = 2;
    public static final int TYPE_SELECT = 3;
    public static final int TYPE_UPDATE = 4;

    private int id;
    private int userId;
    private int type;
    private String description;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LogUserOperationInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
