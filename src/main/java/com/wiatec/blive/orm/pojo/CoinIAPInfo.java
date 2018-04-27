package com.wiatec.blive.orm.pojo;


/**
 * @author patrick
 */
public class CoinIAPInfo extends BaseInfo {

    private String name;
    private String identifier;
    private int number;
    private Float amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CoinIAPInfo{" +
                "name='" + name + '\'' +
                ", identifier='" + identifier + '\'' +
                ", number=" + number +
                ", amount=" + amount +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
