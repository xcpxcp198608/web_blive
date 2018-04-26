package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.LogCoinInfo;

import java.util.List;

/**
 * @author patrick
 */
public interface LogCoinDao {

    int insertOne(LogCoinInfo logCoinInfo);
    List<LogCoinInfo> selectAll();
    List<LogCoinInfo> selectByUserId(int userId);

}
