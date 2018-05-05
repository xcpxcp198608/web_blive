package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.LiveViewInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author patrick
 */
@Repository
public interface LiveViewDao {

    long insertOne(LiveViewInfo liveViewInfo);

    int updateOne(@Param("id") long id);

    List<LiveViewInfo> selectBy2Id(@Param("playerId") int playerId, @Param("viewerId") int viewerId);

}
