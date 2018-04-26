package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.LogCoinIInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author patrick
 */
public interface LogCoinDao {

    int insertOne(LogCoinIInfo logCoinIInfo);
    List<LogCoinIInfo> selectAll();
    List<LogCoinIInfo> selectByUserId(int userId);

}
