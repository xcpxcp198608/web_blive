package com.wiatec.blive.orm.dao;

import com.wiatec.blive.dto.CoinBillDaysInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class CoinBillDaoTest{

    @Resource
    private CoinBillDao coinBillDao;

    @Test
    public void insertOne() {
    }

    @Test
    public void selectOne() {
    }

    @Test
    public void selectByUserId() {
    }

    @Test
    public void selectByTypeAndUserId() {
    }

    @Test
    public void selectMonthBillByUserId() {
    }

    @Test
    public void selectDaysBillByUserId() {
        List<CoinBillDaysInfo> coinBillDaysInfos = coinBillDao.selectDaysBillByUserId(41, "2018-05-01", "2018-06-01");
        System.out.println(coinBillDaysInfos);
    }
}