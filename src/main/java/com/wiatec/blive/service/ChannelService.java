package com.wiatec.blive.service;

import com.google.common.base.Splitter;
import com.wiatec.blive.apns.APNsMaster;
import com.wiatec.blive.common.jpush.PushMaster;
import com.wiatec.blive.common.jpush.PushPayloadBuilder;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.TimeUtil;
import com.wiatec.blive.dto.LiveDaysDistributionInfo;
import com.wiatec.blive.dto.LiveTimeDistributionInfo;
import com.wiatec.blive.dto.LiveViewersInfo;
import com.wiatec.blive.orm.dao.AuthRegisterUserDao;
import com.wiatec.blive.orm.dao.LiveChannelDao;
import com.wiatec.blive.orm.dao.LiveViewDao;
import com.wiatec.blive.orm.dao.RelationFollowDao;
import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import com.wiatec.blive.rtmp.RtmpInfo;
import com.wiatec.blive.rtmp.RtmpMaster;
import com.wiatec.blive.txcloud.LiveChannelMaster;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

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
    private LiveViewDao liveViewDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource
    private RelationFollowDao relationFollowDao;

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
        LiveChannelInfo channelInfo;
        if(action == 1){
            LiveChannelInfo liveChannelInfo = LiveChannelMaster.create(userId);
            if(liveChannelInfo == null){
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
            if(liveChannelDao.updateAvailableAndUrlByUserId(liveChannelInfo) != 1){
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
            channelInfo = liveChannelDao.selectOneByUserId(userId);
            if(channelInfo == null){
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
            List<Integer> integerList = relationFollowDao.selectFollowersIdByUserId(userId);
            APNsMaster.batchSend(userId, integerList, APNsMaster.ACTION_LIVE_START, channelInfo.getTitle());
            //PushMaster.push(PushPayloadBuilder.buildForIos(userInfo.getUsername() + " start live: " + channelInfo.getTitle()));
        }else {
            liveChannelDao.updateUnavailableByUserId(userId);
            channelInfo = new LiveChannelInfo();
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






    public String liveViewAnalysis(int userId, Model model){
        int views = liveViewDao.countViews(userId);
        model.addAttribute("views", views);
        int viewers = liveViewDao.countViewersByPlayerId(userId);
        model.addAttribute("viewers", viewers);

        List<LiveViewersInfo> liveViewersInfoList = liveViewDao.selectViewByPlayerId(userId);
        liveViewersInfoList.forEach(item -> item.setDuration(TimeUtil.getMediaTime(item.getSeconds())));
        model.addAttribute("liveViewersInfoList", liveViewersInfoList);
        return "channel/analysis";
    }

    public ResultInfo getLiveChannelViewDaysDistribution(int userId){
        List<LiveDaysDistributionInfo> liveDaysDistributionInfoList = liveViewDao.selectDaysDistributionByPlayerId(userId);
        liveDaysDistributionInfoList.forEach(item -> {
            item.setDay(item.getDay().replaceAll("\\-", "/"));
            List<String> strings = Splitter.on("/").splitToList(item.getDay());
            StringBuilder stringBuilder = new StringBuilder();
            strings.forEach(i -> {
                int x = Integer.parseInt(i);
                stringBuilder.append(x);
                stringBuilder.append("/");
            });
            String r = stringBuilder.toString();
            r = r.substring(0, r.length() - 1);
            item.setDay(r);
        });
        return ResultMaster.success(liveDaysDistributionInfoList);
    }

    public ResultInfo getLiveChannelViewTimeDistribution(int userId){
        List<LiveTimeDistributionInfo> liveTimeDistributionInfos = liveViewDao.selectTimeDistributionByPlayerId(userId);
        return ResultMaster.success(liveTimeDistributionInfos);
    }
}
