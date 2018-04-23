package com.wiatec.blive.orm.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 */
@Repository
public interface LogQRCodeScanDao {

    int insertOne(@Param("userId") int userId, @Param("ip") String ip);
}
