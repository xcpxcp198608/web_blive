package com.wiatec.blive.orm.pojo;


/**
 * @author patrick
 */
public class CoinBillInfo extends BaseInfo {

    public static final int TYPE_CHARGE = 3;
    public static final int TYPE_INCOME = 2;
    public static final int TYPE_CONSUME = 1;

    public static final int CATEGORY_CHARGE_IAP = 31;

    public static final int CATEGORY_INCOME_PRO = 20;
    public static final int CATEGORY_INCOME_VIEW = 21;
    public static final int CATEGORY_INCOME_GIFT = 22;


    public static final int CATEGORY_CONSUME_PRO = 10;
    public static final int CATEGORY_CONSUME_VIEW = 11;
    public static final int CATEGORY_CONSUME_GIFT = 12;

    private int userId;
    private int relationId;
    private int type;
    private int category;
    private int coins;
    private Double amount;
    private String platform;
    private String transactionId;
    private String description;
    private String comment;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CoinBillInfo{" +
                "userId=" + userId +
                ", relationId=" + relationId +
                ", type=" + type +
                ", category=" + category +
                ", coins=" + coins +
                ", amount=" + amount +
                ", platform='" + platform + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
