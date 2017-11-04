package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.PayResultInfo;

import java.util.List;

public interface PayResultDao {

    /**
     * query count by pay id
     * @param paymentId id
     */
    int countOne(String paymentId);
    PayResultInfo selectOne(String paymentId);

    /**
     * insert pay full info from paypal verify RESTFUL api
     * @param payResultInfo
     */
    void insertOne(PayResultInfo payResultInfo);

    /**
     * update pay info
     * @param payResultInfo
     */
    void updateOne(PayResultInfo payResultInfo);
}
