package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LiveChannelDao {

    List<LiveChannelInfo> selectAllAvailable();
    List<LiveChannelInfo> selectAllAvailableWithUserInfo();
    List<LiveChannelInfo> searchByLikeTitle(String title);
    LiveChannelInfo selectOneByUserId(int userId);
    int countByUserId(int userId);
    int insertChannel(LiveChannelInfo channelInfo);
    int updateChannel(LiveChannelInfo channelInfo);
    int updateTitleAndMessageByUserId(LiveChannelInfo channelInfo);
    int updateTitleByUserId(LiveChannelInfo channelInfo);
    int updateMessageByUserId(LiveChannelInfo channelInfo);
    int updatePriceByUserId(LiveChannelInfo channelInfo);
    int updateLinkByUserId(LiveChannelInfo channelInfo);
    int updateAllSettingByUserId(LiveChannelInfo channelInfo);
    int updateAvailableByUserId(int userId);
    int updateUnavailableByUserId(int userId);
    int updatePreviewByUserId(LiveChannelInfo channelInfo);
}
