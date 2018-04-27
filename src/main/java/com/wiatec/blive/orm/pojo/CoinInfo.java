package com.wiatec.blive.orm.pojo;


import org.kohsuke.rngom.parse.host.Base;

import java.util.Date;

/**
 * @author patrick
 */
public class CoinInfo extends BaseInfo {

    private int userId;
    private int coins;

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
