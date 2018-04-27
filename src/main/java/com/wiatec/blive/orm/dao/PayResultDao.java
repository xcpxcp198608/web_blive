package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.PayResultInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface PayResultDao {

    /**
     * query count by pay id
     * @param paymentId id
     */
    int countOne(String paymentId);
    PayResultInfo selectOne(String paymentId);
    List<PayResultInfo> selectOneByPayer(PayResultInfo payResultInfo);


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
