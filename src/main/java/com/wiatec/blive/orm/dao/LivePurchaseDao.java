package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.ChannelPurchaseInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LivePurchaseDao {

    int countOne(@Param("channelId") int channelId, @Param("viewerId") int viewerId);
    int insertOne(@Param("channelId") int channelId, @Param("viewerId") int viewerId,
                  @Param("expiresTime") Date expiresTime);
    int updateOne(@Param("channelId") int channelId, @Param("viewerId") int viewerId,
                  @Param("expiresTime") Date expiresTime);
    ChannelPurchaseInfo selectOne(@Param("channelId") int channelId, @Param("viewerId") int viewerId);
    List<Integer> selectAllChannelByViewId(int viewerId);
}
