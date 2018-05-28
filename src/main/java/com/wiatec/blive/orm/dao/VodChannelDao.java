package com.wiatec.blive.orm.dao;

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

    List<VodChannelInfo> selectAllAvailableWithUserInfo();
    List<VodChannelInfo> selectByUserId(int userId);
    VodChannelInfo selectByUserAndVideoId(@Param("userId") int userId, @Param("videoId") String videoId);

    VodChannelInfo selectOneByVideoId(String videoId);
    int countByVideoId(String videoId);
    int countByFileId(String fileId);
    int insertChannel(VodChannelInfo channelInfo);

    int updateTitleAndMessageByVideoId(VodChannelInfo channelInfo);
    int updateTitleByVideoId(VodChannelInfo channelInfo);
    int updateMessageByVideoId(VodChannelInfo channelInfo);
    int updatePriceByVideoId(VodChannelInfo channelInfo);
    int updateLinkByVideoId(VodChannelInfo channelInfo);
    int updateAllSettingByVideoId(VodChannelInfo channelInfo);
    int updateAvailableByVideoId(String videoId);
    int updateUnavailableByVideoId(String videoId);
    int updatePreviewByVideoId(VodChannelInfo channelInfo);
    int updatePreviewByFileId(@Param("fileId") String fileId, @Param("preview") String preview);
}
