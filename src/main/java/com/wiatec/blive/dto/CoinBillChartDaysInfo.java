package com.wiatec.blive.dto;


/**
 * @author patrick
 */
public class CoinBillChartDaysInfo {

    private int day;
    private int incomeCoins;
    private int chargeCoins;
    private int consumeCoins;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getIncomeCoins() {
        return incomeCoins;
    }

    public void setIncomeCoins(int incomeCoins) {
        this.incomeCoins = incomeCoins;
    }

    public int getChargeCoins() {
        return chargeCoins;
    }

    public void setChargeCoins(int chargeCoins) {
        this.chargeCoins = chargeCoins;
    }

    public int getConsumeCoins() {
        return consumeCoins;
    }

    public void setConsumeCoins(int consumeCoins) {
        this.consumeCoins = consumeCoins;
    }

    @Override
    public String toString() {
        return "CoinBillChartDaysInfo{" +
                "day=" + day +
                ", incomeCoins=" + incomeCoins +
                ", chargeCoins=" + chargeCoins +
                ", consumeCoins=" + consumeCoins +
                '}';
    }
}
