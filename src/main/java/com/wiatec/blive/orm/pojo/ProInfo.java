package com.wiatec.blive.orm.pojo;


/**
 * @author patrick
 */
public class ProInfo extends BaseInfo {

    private int level;
    private int months;
    private int price;
    private boolean onSale;
    private int onSalePrice;
    private String description;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public int getOnSalePrice() {
        return onSalePrice;
    }

    public void setOnSalePrice(int onSalePrice) {
        this.onSalePrice = onSalePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProInfo{" +
                "level=" + level +
                ", months=" + months +
                ", price=" + price +
                ", onSale=" + onSale +
                ", onSalePrice=" + onSalePrice +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
