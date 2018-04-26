package com.wiatec.blive.orm.pojo;


import java.util.Date;

/**
 * @author patrick
 */
public class CoinInfo {

    private int id;
    private int userId;
    private int coins;
    private Date createTime;
    private Date modifyTime;

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

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "CoinInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", coins=" + coins +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
