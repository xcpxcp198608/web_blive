package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.ChannelInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelDao {

    List<ChannelInfo> selectAllAvailable();
    ChannelInfo selectOneByUserId(ChannelInfo channelInfo);
    int countUserId(ChannelInfo channelInfo);
    void insertChannel(ChannelInfo channelInfo);
    void updateChannel(ChannelInfo channelInfo);
    void updateChannelTitle(ChannelInfo channelInfo);
    void updateChannelTitle1(ChannelInfo channelInfo);
    void updateChannelMessage(ChannelInfo channelInfo);
    void updateChannelPrice(ChannelInfo channelInfo);
    void updateChannelAvailable(ChannelInfo channelInfo);
    void updateChannelUnavailable(ChannelInfo channelInfo);
    void updatePreview(ChannelInfo channelInfo);
}
