package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.ChannelInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelDao {

    List<ChannelInfo> selectAllAvailable();
    List<ChannelInfo> searchByLikeTitle(String key);
    ChannelInfo selectOneByUserId(int userId);
    int countByUserId(ChannelInfo channelInfo);
    int insertChannel(ChannelInfo channelInfo);
    int updateChannel(ChannelInfo channelInfo);
    void updateChannelTitleAndMessage(ChannelInfo channelInfo);
    void updateChannelTitle(ChannelInfo channelInfo);
    void updateChannelMessage(ChannelInfo channelInfo);
    void updateChannelPrice(ChannelInfo channelInfo);
    void updateChannelAllSetting(ChannelInfo channelInfo);
    void updateChannelAvailable(int userId);
    void updateChannelUnavailable(int userId);
    void updatePreview(ChannelInfo channelInfo);
}
