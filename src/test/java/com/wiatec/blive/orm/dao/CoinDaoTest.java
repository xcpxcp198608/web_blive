package com.wiatec.blive.orm.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class CoinDaoTest {

    @Resource
    private CoinDao coinDao;

    @Test
    public void countOne() {
        int i = coinDao.countOne(1);
        System.out.println(i);
    }

    @Test
    public void countCoins() {
        int i = coinDao.countCoins(41);
        System.out.println(i);
    }

    @Test
    public void insertOne() {
        int i = coinDao.insertOne(41, 100);
        System.out.println(i);
    }

    @Test
    public void updateOne() {
        int i = coinDao.updateOne(41, 0, 200);
        System.out.println(i);
    }
}