package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface ChannelDao {

    List<ChannelInfo> selectAllAvailable();
    List<LiveChannelInfo> selectAllAvailableWithUserInfo();
    List<ChannelInfo> searchByLikeTitle(String title);
    ChannelInfo selectOneByUserId(int userId);
    int countByUserId(int userId);
    int insertChannel(ChannelInfo channelInfo);
    int updateChannel(ChannelInfo channelInfo);
    int updateTitleAndMessageByUserId(ChannelInfo channelInfo);
    int updateTitleByUserId(ChannelInfo channelInfo);
    int updateMessageByUserId(ChannelInfo channelInfo);
    int updatePriceByUserId(ChannelInfo channelInfo);
    int updateLinkByUserId(ChannelInfo channelInfo);
    int updateAllSettingByUserId(ChannelInfo channelInfo);
    int updateAvailableByUserId(int userId);
    int updateUnavailableByUserId(int userId);
    int updatePreviewByUserId(ChannelInfo channelInfo);
}
