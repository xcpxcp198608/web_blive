package com.wiatec.blive.service;

import com.wiatec.blive.common.jpush.PushMaster;
import com.wiatec.blive.common.jpush.PushPayloadBuilder;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.dao.AuthRegisterUserDao;
import com.wiatec.blive.orm.dao.ChannelDao;
import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import com.wiatec.blive.orm.pojo.ChannelInfo;
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
    private ChannelDao channelDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;

    /**
     * select channels that in living
     * @return list of ChannelInfo
     */
    public List<ChannelInfo> selectAllAvailable(){
        return channelDao.selectAllAvailable();
    }


    @Transactional(rollbackFor = Exception.class)
    public ResultInfo<ChannelInfo> create(int userId, String username){
        RtmpInfo rtmpInfo = new RtmpMaster().getRtmpInfo(username);
        if(rtmpInfo == null){
            throw new XException("rtmp server error");
        }
        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setTitle(username);
        channelInfo.setUserId(userId);
        channelInfo.setUrl(rtmpInfo.getPush_full_url());
        channelInfo.setRtmpUrl(rtmpInfo.getPush_url());
        channelInfo.setRtmpKey(rtmpInfo.getPush_key());
        channelInfo.setPlayUrl(rtmpInfo.getPlay_url());
        if(channelDao.insertChannel(channelInfo) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success(channelInfo);
    }

    /**
     * search channel info by the keyword in title
     * @param key keyword
     * @return list of ChannelInfo
     */
    public List<ChannelInfo> searchByLikeTitle(String key){
        return channelDao.searchByLikeTitle(key);
    }

    /**
     * select channel info by user id
     * @param userId user id
     * @return ChannelInfo
     */
    public ResultInfo<ChannelInfo> selectOneByUserId(int userId){
        ChannelInfo channelInfo = channelDao.selectOneByUserId(userId);
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
    public ResultInfo<ChannelInfo> updateChannelUrl(String username, ChannelInfo channelInfo){
        if(channelDao.countByUserId(channelInfo.getUserId()) == 1){
            channelDao.updateChannel(channelInfo);
        }else{
            channelInfo.setTitle(username);
            channelDao.insertChannel(channelInfo);
        }
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel title and message
     * @param channelInfo  ChannelInfo
     * @return  ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelTitleAndMessage(ChannelInfo channelInfo){
        if(channelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        channelDao.updateTitleAndMessageByUserId(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel title
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelTitle(ChannelInfo channelInfo){
        if(channelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        channelDao.updateTitleByUserId(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel message
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelMessage(ChannelInfo channelInfo){
        if(channelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        channelDao.updateMessageByUserId(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel price
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelPrice(ChannelInfo channelInfo){
        if(channelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        channelDao.updatePriceByUserId(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }


    /**
     * update channel all setting: title, message, price
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelAllSetting(ChannelInfo channelInfo){
        if(channelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        channelDao.updateAllSettingByUserId(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel status
     * @param action 1 -> activate, 0 -> deactivate
     * @param userId user id
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelStatus(int action, int userId){
        AuthRegisterUserInfo userInfo = authRegisterUserDao.selectOneById(userId);
        ChannelInfo channelInfo = channelDao.selectOneByUserId(userId);
        if(action == 1){
            channelDao.updateAvailableByUserId(userId);
            PushMaster.push(PushPayloadBuilder.buildForIos(userInfo.getUsername() + " start live: " + channelInfo.getTitle()));
        }else {
            channelDao.updateUnavailableByUserId(userId);
        }
        return ResultMaster.success(channelInfo);
    }

    /**
     * update channel preview image
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updatePreview(ChannelInfo channelInfo){
        channelDao.updatePreviewByUserId(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }
}
