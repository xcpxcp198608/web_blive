package com.wiatec.blive.orm.dao;

import com.wiatec.blive.dto.CoinBillDaysInfo;
import com.wiatec.blive.orm.pojo.CoinBillInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface CoinBillDao {

    int insertOne(CoinBillInfo coinBillInfo);
    CoinBillInfo selectOne(int id);
    List<CoinBillInfo> selectByUserId(int userId);
    List<CoinBillInfo> selectByTypeAndUserId(@Param("userId") int userId, @Param("type") int type);


    List<CoinBillInfo> selectMonthBillByUserId(@Param("userId") int userId, @Param("start") String start,
                                                     @Param("end") String end);

    List<CoinBillDaysInfo> selectDaysBillByUserId(@Param("userId") int userId,
                                                  @Param("start") String start, @Param("end") String end);

}
