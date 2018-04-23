package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.BlackListInfo;
import com.wiatec.blive.orm.pojo.FeedbackInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 */
@Repository
public interface BlackListDao {

    int insertOne(@Param("userId") int userId, @Param("blackId") int blackId);
    int deleteOne(@Param("userId") int userId, @Param("blackId") int blackId);
}
