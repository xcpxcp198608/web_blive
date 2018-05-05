package com.wiatec.blive.dto;


/**
 * @author patrick
 */
public class CoinBillDaysInfo {

    private int day;

    private int type;

    private int coins;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "CoinBillDaysInfo{" +
                "day=" + day +
                ", type=" + type +
                ", coins=" + coins +
                '}';
    }
}
