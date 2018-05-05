package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.LiveViewInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class LiveViewDaoTest {

    @Resource
    private LiveViewDao liveViewDao;

    @Test
    public void insertOne() {
        LiveViewInfo liveViewInfo = new LiveViewInfo(1,3);
        long l = liveViewDao.insertOne(liveViewInfo);
        System.out.println(l);
        System.out.println(liveViewInfo.getId());
    }

    @Test
    public void updateOne() {
    }

    @Test
    public void selectBy2Id() {
    }
}