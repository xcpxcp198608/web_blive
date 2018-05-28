package com.wiatec.blive.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 显示在pro center的用户pro信息
 * @author patrick
 */
public class ProDetailInfo {

    private int userId;
    private int level;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expiresDate;
    private String leftDays;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
    }

    public String getLeftDays() {
        return leftDays;
    }

    public void setLeftDays(String leftDays) {
        this.leftDays = leftDays;
    }

    @Override
    public String toString() {
        return "ProDetailInfo{" +
                "userId=" + userId +
                ", level=" + level +
                ", expiresDate=" + expiresDate +
                ", leftDays='" + leftDays + '\'' +
                '}';
    }
}
