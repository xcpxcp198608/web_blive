package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import com.wiatec.blive.orm.pojo.VodChannelInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface VodChannelDao {

    List<ChannelInfo> selectAllAvailableWithUserInfo();
    List<ChannelInfo> selectByUserId(int userId);
    ChannelInfo selectByUserAndVideoId(@Param("userId") int userId, @Param("videoId") String videoId);

    ChannelInfo selectOneByVideoId(String videoId);
    ChannelInfo selectOneByChannelId(int channelId);
    int countByVideoId(String videoId);
    int countByFileId(String fileId);
    int insertChannel(ChannelInfo channelInfo);

    int updateTitleAndMessageByVideoId(ChannelInfo channelInfo);
    int updateTitleByVideoId(@Param("videoId") String videoId, @Param("title") String title);
    int updateMessageByVideoId(@Param("videoId") String videoId, @Param("message") String message);
    int updatePriceByVideoId(@Param("videoId") String videoId, @Param("price") int price);
    int updateLinkByVideoId(@Param("videoId") String videoId, @Param("link") String link);
    int updateAllSettingByVideoId(ChannelInfo channelInfo);

    int updateAvailableByVideoIds(@Param("videoIds") String [] videoIds);
    int updateUnavailableByVideoIds(@Param("videoIds") String []videoId);

    int updatePreviewByVideoId(@Param("videoId") String videoId, @Param("preview") String preview);
    int updatePreviewByFileId(@Param("fileId") String fileId, @Param("preview") String preview);

    int deleteChannel(@Param("userId") int userId, @Param("videoId") String videoId);
}
