package com.wiatec.blive.orm.dao;

import com.wiatec.blive.dto.LiveDaysDistributionInfo;
import com.wiatec.blive.dto.LiveTimeDistributionInfo;
import com.wiatec.blive.dto.LiveViewersInfo;
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

    List<LiveViewInfo> selectByPlayerId(int playerId);

    int countViews(int playerId);

    int countViewersByPlayerId(int playerId);



    List<LiveViewersInfo> selectViewByPlayerId(int playerId);

    List<LiveDaysDistributionInfo> selectDaysDistributionByPlayerId(int playerId);

    List<LiveTimeDistributionInfo> selectTimeDistributionByPlayerId(int playerId);

}
