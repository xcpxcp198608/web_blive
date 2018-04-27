package com.wiatec.blive.orm.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.kohsuke.rngom.parse.host.Base;

import java.util.Date;

/**
 * @author patrick
 */
public class LogCoinInfo extends BaseInfo {

    private int userId;
    private String username;
    private int action;
    private int coin;
    private Float amount;
    private String platform;
    private String transactionId;
    private String description;
    private String remark;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public String toString() {
        return "LogCoinInfo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", action=" + action +
                ", coin=" + coin +
                ", amount=" + amount +
                ", platform='" + platform + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
