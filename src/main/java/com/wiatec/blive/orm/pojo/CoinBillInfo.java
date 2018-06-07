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


    public static String getConsumeDescription(AuthRegisterUserInfo producerInfo, int coins,
                                               int category, String comment){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("consume ")
                .append(coins)
                .append(" coins to");
        switch (category){
            case CATEGORY_CONSUME_PRO:
                stringBuilder.append(" purchase PRO ").append(comment);
                break;
            case CATEGORY_CONSUME_VIEW:
                stringBuilder.append(" purchase ")
                        .append(producerInfo.getUsername())
                        .append("'s channel ")
                        .append(comment);
                break;
            case CATEGORY_CONSUME_GIFT:
                stringBuilder.append(" send gift to")
                        .append(producerInfo.getUsername())
                        .append(comment);
                break;
            default:
                 break;
        }
        return stringBuilder.toString();
    }


    public static String getProducerDescription( AuthRegisterUserInfo consumerInfo, int coins,
                                                 int category, String comment){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("get ")
                .append(coins)
                .append(" coins from ");
        switch (category){
            case CATEGORY_CONSUME_PRO:
                stringBuilder.append(consumerInfo.getUsername())
                        .append(" purchase PRO ")
                        .append(comment);
                break;
            case CATEGORY_CONSUME_VIEW:
                stringBuilder.append(consumerInfo.getUsername())
                        .append(" purchase channel view ")
                        .append(comment);
                break;
            case CATEGORY_CONSUME_GIFT:
                stringBuilder.append(consumerInfo.getUsername())
                        .append(" send gift ")
                        .append(comment);
                break;
            default:
                break;
        }
        return stringBuilder.toString();
    }

    public static String getSystemDescription( AuthRegisterUserInfo consumerInfo, AuthRegisterUserInfo producerInfo,
                                               int coins, int category, String comment){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("get ")
                .append(coins)
                .append(" coins from ");
        switch (category){
            case CATEGORY_CONSUME_PRO:
                stringBuilder.append(consumerInfo.getUsername())
                        .append(" purchase PRO ")
                        .append(comment);
                break;
            case CATEGORY_CONSUME_VIEW:
                stringBuilder.append(consumerInfo.getUsername())
                        .append(" purchase ")
                        .append(producerInfo.getUsername())
                        .append("'s channel ")
                        .append(comment);
                break;
            case CATEGORY_CONSUME_GIFT:
                stringBuilder.append(consumerInfo.getUsername())
                        .append(" send gift to ")
                        .append(producerInfo.getUsername())
                        .append(comment);
                break;
            default:
                break;
        }
        return stringBuilder.toString();
    }
}
