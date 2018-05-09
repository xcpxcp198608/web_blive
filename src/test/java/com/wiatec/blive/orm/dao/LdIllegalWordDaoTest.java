package com.wiatec.blive.orm.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class LdIllegalWordDaoTest {

    @Resource
    private LdIllegalWordDao ldIllegalWordDao;

    @Test
    public void selectAll() {
        List<String> strings = ldIllegalWordDao.selectAll();
        System.out.println(strings);
    }
}