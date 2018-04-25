package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.BlackListInfo;
import com.wiatec.blive.orm.pojo.FeedbackInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface BlackListDao {

    int insertOne(@Param("userId") int userId, @Param("blackId") int blackId,
                  @Param("blackUsername") String blackUsername);
    int deleteOne(@Param("userId") int userId, @Param("blackId") int blackId);
    int countOne(@Param("userId") int userId, @Param("blackId") int blackId);
    List<BlackListInfo> selectAllByUserId(int userId);
}
