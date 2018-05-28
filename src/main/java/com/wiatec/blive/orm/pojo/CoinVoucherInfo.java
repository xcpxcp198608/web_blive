package com.wiatec.blive.orm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author patrick
 */
public class CoinVoucherInfo extends BaseInfo {

    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_USED = 1;
    public static final int STATUS_EXPIRE = 2;

    private String voucherId;
    private int amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiresDate;
    private int status;

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CoinVoucherInfo{" +
                "voucherId='" + voucherId + '\'' +
                ", amount=" + amount +
                ", expiresDate=" + expiresDate +
                ", status=" + status +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
