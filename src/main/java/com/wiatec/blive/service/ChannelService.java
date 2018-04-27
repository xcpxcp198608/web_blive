package com.wiatec.blive.service;

import com.wiatec.blive.common.jpush.PushMaster;
import com.wiatec.blive.common.jpush.PushPayloadBuilder;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.dao.AuthRegisterUserDao;
import com.wiatec.blive.orm.dao.LiveChannelDao;
import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import com.wiatec.blive.rtmp.RtmpInfo;
import com.wiatec.blive.rtmp.RtmpMaster;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class ChannelService {

    @Resource
    private LiveChannelDao liveChannelDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;

    /**
     * select channels that in living
     * @return list of ChannelInfo
     */
    public List<LiveChannelInfo> selectAllAvailable(){
        return liveChannelDao.selectAllAvailable();
    }


    public List<LiveChannelInfo> selectAllAvailableWithUser(){
        return liveChannelDao.selectAllAvailableWithUserInfo();
    }


    @Transactional(rollbackFor = Exception.class)
    public ResultInfo<LiveChannelInfo> create(int userId, String username){
        RtmpInfo rtmpInfo = new RtmpMaster().getRtmpInfo(username);
        if(rtmpInfo == null){
            throw new XException("rtmp server error");
        }
        LiveChannelInfo channelInfo = new LiveChannelInfo();
        channelInfo.setTitle(username);
        channelInfo.setUserId(userId);
        channelInfo.setUrl(rtmpInfo.getPush_full_url());
        channelInfo.setRtmpUrl(rtmpInfo.getPush_url());
        channelInfo.setRtmpKey(rtmpInfo.getPush_key());
        channelInfo.setPlayUrl(rtmpInfo.getPlay_url());
        if(liveChannelDao.insertChannel(channelInfo) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success(channelInfo);
    }

    /**
     * search channel info by the keyword in title
     * @param key keyword
     * @return list of ChannelInfo
     */
    public List<LiveChannelInfo> searchByLikeTitle(String key){
        return liveChannelDao.searchByLikeTitle(key);
    }

    /**
     * select channel info by user id
     * @param userId user id
     * @return ChannelInfo
     */
    public ResultInfo<LiveChannelInfo> selectOneByUserId(int userId){
        LiveChannelInfo channelInfo = liveChannelDao.selectOneByUserId(userId);
        if(channelInfo == null){
            String username = authRegisterUserDao.selectOneById(userId).getUsername();
            return create(userId, username);
        }
        return ResultMaster.success(channelInfo);
    }

    /**
     * update channel url info
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<LiveChannelInfo> updateChannelUrl(String username, LiveChannelInfo channelInfo){
        if(liveChannelDao.countByUserId(channelInfo.getUserId()) == 1){
            liveChannelDao.updateChannel(channelInfo);
        }else{
            channelInfo.setTitle(username);
            liveChannelDao.insertChannel(channelInfo);
        }
        return ResultMaster.success(liveChannelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel title and message
     * @param channelInfo  ChannelInfo
     * @return  ResultInfo
     */
    public ResultInfo<LiveChannelInfo> updateChannelTitleAndMessage(LiveChannelInfo channelInfo){
        if(liveChannelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        liveChannelDao.updateTitleAndMessageByUserId(channelInfo);
        return ResultMaster.success(liveChannelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel title
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<LiveChannelInfo> updateChannelTitle(LiveChannelInfo channelInfo){
        if(liveChannelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        liveChannelDao.updateTitleByUserId(channelInfo);
        return ResultMaster.success(liveChannelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel message
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<LiveChannelInfo> updateChannelMessage(LiveChannelInfo channelInfo){
        if(liveChannelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        liveChannelDao.updateMessageByUserId(channelInfo);
        return ResultMaster.success(liveChannelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel price
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<LiveChannelInfo> updateChannelPrice(LiveChannelInfo channelInfo){
        if(liveChannelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        liveChannelDao.updatePriceByUserId(channelInfo);
        return ResultMaster.success(liveChannelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel link
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<LiveChannelInfo> updateChannelLink(LiveChannelInfo channelInfo){
        if(liveChannelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        liveChannelDao.updateLinkByUserId(channelInfo);
        return ResultMaster.success(liveChannelDao.selectOneByUserId(channelInfo.getUserId()));
    }


    /**
     * update channel all setting: title, message, price
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<LiveChannelInfo> updateChannelAllSetting(LiveChannelInfo channelInfo){
        if(liveChannelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        liveChannelDao.updateAllSettingByUserId(channelInfo);
        return ResultMaster.success(liveChannelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel status
     * @param action 1 -> activate, 0 -> deactivate
     * @param userId user id
     * @return ResultInfo
     */
    public ResultInfo<LiveChannelInfo> updateChannelStatus(int action, int userId){
        AuthRegisterUserInfo userInfo = authRegisterUserDao.selectOneById(userId);
        LiveChannelInfo channelInfo = liveChannelDao.selectOneByUserId(userId);
        if(action == 1){
            liveChannelDao.updateAvailableByUserId(userId);
            PushMaster.push(PushPayloadBuilder.buildForIos(userInfo.getUsername() + " start live: " + channelInfo.getTitle()));
        }else {
            liveChannelDao.updateUnavailableByUserId(userId);
        }
        return ResultMaster.success(channelInfo);
    }

    /**
     * update channel preview image
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<LiveChannelInfo> updatePreview(LiveChannelInfo channelInfo){
        liveChannelDao.updatePreviewByUserId(channelInfo);
        return ResultMaster.success(liveChannelDao.selectOneByUserId(channelInfo.getUserId()));
    }
}
