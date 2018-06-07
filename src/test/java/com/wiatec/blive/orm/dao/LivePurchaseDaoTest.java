package com.wiatec.blive.orm.dao;

import com.wiatec.blive.common.utils.TimeUtil;
import com.wiatec.blive.orm.pojo.ChannelPurchaseInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class LivePurchaseDaoTest {

    @Resource private LivePurchaseDao livePurchaseDao;

    @Test
    public void countOne() {
        int one = livePurchaseDao.countOne(1, 41);
        System.out.println(one);
    }

    @Test
    public void insertOne() {
        int one = livePurchaseDao.insertOne(1, 41, TimeUtil.getExpiresTime(new Date(), 1));
        System.out.println(one);
    }

    @Test
    public void updateOne() {
        int one = livePurchaseDao.updateOne(1, 41, TimeUtil.getExpiresTime(new Date(), 2));
        System.out.println(one);
    }

    @Test
    public void selectOne() {
        ChannelPurchaseInfo channelPurchaseInfo = livePurchaseDao.selectOne(1, 41);
        System.out.println(channelPurchaseInfo);
    }

    @Test
    public void selectAllChannelByViewId() {
        List<Integer> integers = livePurchaseDao.selectAllChannelByViewId(41);
        System.out.println(integers);
    }
}