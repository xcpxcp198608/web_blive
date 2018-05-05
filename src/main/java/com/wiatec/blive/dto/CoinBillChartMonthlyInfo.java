package com.wiatec.blive.dto;

import com.wiatec.blive.orm.pojo.BaseInfo;

/**
 * @author patrick
 */
public class CoinBillChartMonthlyInfo extends BaseInfo {

    protected int incomeCoins;
    protected int consumeCoins;
    protected int chargeCoins;

    public int getIncomeCoins() {
        return incomeCoins;
    }

    public void setIncomeCoins(int incomeCoins) {
        this.incomeCoins = incomeCoins;
    }

    public int getConsumeCoins() {
        return consumeCoins;
    }

    public void setConsumeCoins(int consumeCoins) {
        this.consumeCoins = consumeCoins;
    }

    public int getChargeCoins() {
        return chargeCoins;
    }

    public void setChargeCoins(int chargeCoins) {
        this.chargeCoins = chargeCoins;
    }

    @Override
    public String toString() {
        return "CoinBillChartMonthlyInfo{" +
                "incomeCoins=" + incomeCoins +
                ", consumeCoins=" + consumeCoins +
                ", chargeCoins=" + chargeCoins +
                '}';
    }
}
