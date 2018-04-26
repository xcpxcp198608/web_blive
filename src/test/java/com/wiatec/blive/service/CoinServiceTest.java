package com.wiatec.blive.service;

import com.wiatec.blive.common.result.ResultInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class CoinServiceTest {

    @Resource
    private CoinService coinService;

    @Test
    public void getCoins() {
        ResultInfo coins = coinService.getCoins(41);
        System.out.println(coins);
    }

    @Test
    public void consumeCoin() {
    }

    @Test
    public void iapVerify() {
    }
}