package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.ChannelInfo;

import java.util.List;

public interface ChannelDao {

    List<ChannelInfo> selectAllAvailable();
    ChannelInfo selectOne(ChannelInfo channelInfo);
    int countUserId(ChannelInfo channelInfo);
    void insertChannel(ChannelInfo channelInfo);
    void updateChannel(ChannelInfo channelInfo);
    void updateChannelTitle(ChannelInfo channelInfo);
    void updateChannelPrice(ChannelInfo channelInfo);
    void updateChannelAvailable(ChannelInfo channelInfo);
    void updateChannelUnavailable(ChannelInfo channelInfo);
    void updatePreview(ChannelInfo channelInfo);
}
