package com.wiatec.blive.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.wiatec.blive.apns.APNsMaster;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.AESUtil;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.common.utils.TimeUtil;
import com.wiatec.blive.dto.LiveDaysDistributionInfo;
import com.wiatec.blive.dto.LiveTimeDistributionInfo;
import com.wiatec.blive.dto.LiveViewersInfo;
import com.wiatec.blive.orm.dao.AuthRegisterUserDao;
import com.wiatec.blive.orm.dao.LiveChannelDao;
import com.wiatec.blive.orm.dao.LiveViewDao;
import com.wiatec.blive.orm.dao.RelationFollowDao;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
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
public class LiveChannelService {

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


    public ResultInfo<PageInfo<LiveChannelInfo>> selectAllAvailableWithUser(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<LiveChannelInfo> channelInfoList = liveChannelDao.selectAllAvailableWithUserInfo();
        if(channelInfoList == null || channelInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        for(LiveChannelInfo liveChannelInfo: channelInfoList){
            liveChannelInfo.setPlayUrl(AESUtil.encrypt(liveChannelInfo.getPlayUrl(), AESUtil.KEY));
        }
        PageInfo<LiveChannelInfo> pageInfo = new PageInfo<>(channelInfoList);
        return ResultMaster.success(pageInfo);
    }


    @Transactional(rollbackFor = Exception.class)
    public ResultInfo<LiveChannelInfo> create(int userId, String username){
        LiveChannelInfo channelInfo = LiveChannelMaster.create(userId);
        channelInfo.setTitle(username);
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
            channelInfo =  create(userId, username).getData();
        }
        channelInfo.setPlayUrl(AESUtil.encrypt(channelInfo.getPlayUrl(), AESUtil.KEY));
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
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo<LiveChannelInfo> updateChannelAllSetting(LiveChannelInfo channelInfo){
        if(liveChannelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        if(liveChannelDao.updateAllSettingByUserId(channelInfo) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        LiveChannelInfo info = liveChannelDao.selectOneByUserId(channelInfo.getUserId());
        if(info == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        if(TextUtil.isEmpty(info.getUrl())){
            return updateChannelUrl(channelInfo);
        }
        String hexTime = info.getUrl().substring(info.getUrl().length() - 8);
        try {
            long time = Long.valueOf(hexTime, 16);
            // 如果防盗链验证日期减除当前时间小于一天，更换url
            if(time - System.currentTimeMillis() / 1000 < 86400){
                return updateChannelUrl(channelInfo);
            }
        }catch (Exception e){
            return updateChannelUrl(channelInfo);
        }
        return ResultMaster.success(info);
    }

    private ResultInfo<LiveChannelInfo> updateChannelUrl(LiveChannelInfo channelInfo){
        LiveChannelInfo liveChannelInfo = LiveChannelMaster.create(channelInfo.getUserId());
        if(liveChannelInfo == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        if(liveChannelDao.updateChannel(liveChannelInfo) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        LiveChannelInfo info = liveChannelDao.selectOneByUserId(channelInfo.getUserId());
        if(info == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success(info);
    }

    /**
     * update channel status
     * @param action 1 -> activate, 0 -> deactivate
     * @param userId user id
     * @return ResultInfo
     */
    public ResultInfo updateChannelStatus(int action, int userId){
        LiveChannelInfo channelInfo;
        if(action == 1){
            liveChannelDao.updateAvailableByUserId(userId);
            channelInfo = liveChannelDao.selectOneByUserId(userId);
            if(channelInfo != null) {
                List<Integer> integerList = relationFollowDao.selectFollowersIdByUserId(userId);
                APNsMaster.batchSend(userId, integerList, APNsMaster.ACTION_LIVE_START, channelInfo.getTitle());
                //PushMaster.push(PushPayloadBuilder.buildForIos(userInfo.getUsername() + " start live: " + channelInfo.getTitle()));
            }
        }else {
            liveChannelDao.updateUnavailableByUserId(userId);
        }
        return ResultMaster.success();
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
