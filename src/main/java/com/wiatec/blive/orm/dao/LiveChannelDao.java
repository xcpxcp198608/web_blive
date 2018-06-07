package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LiveChannelDao {

    List<ChannelInfo> selectAllAvailable();
    List<ChannelInfo> selectAllAvailableWithUserInfo();
    List<ChannelInfo> searchByLikeTitle(String title);
    ChannelInfo selectOneByUserId(int userId);
    ChannelInfo selectOneByChannelId(int channelId);
    int selectUserIdByStreamId(String streamId);

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
