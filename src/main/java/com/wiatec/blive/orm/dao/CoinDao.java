package com.wiatec.blive.orm.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author patrick
 */
public interface CoinDao {

    int countOne(int userId);
    int countCoins(int userId);
    int insertOne(@Param("userId") int userId, @Param("coins") int coins);
    int updateOne(@Param("userId") int userId, @Param("action") int action,
                  @Param("coins") int coins);

}
