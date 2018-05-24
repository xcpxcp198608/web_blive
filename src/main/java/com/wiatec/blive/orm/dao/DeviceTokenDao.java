package com.wiatec.blive.orm.dao;


import com.wiatec.blive.orm.pojo.DeviceTokenInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface DeviceTokenDao {

    int countOne(int userId);
    int insertOne(@Param("userId") int userId, @Param("deviceToken") String deviceToken);
    int updateOne(@Param("userId") int userId, @Param("deviceToken") String deviceToken);
    String selectOne(int userId);
    List<DeviceTokenInfo> batchSelect(@Param("userIds") List<Integer> userIds);
}
