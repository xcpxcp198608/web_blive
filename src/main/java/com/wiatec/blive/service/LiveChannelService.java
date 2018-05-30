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
import com.wiatec.blive.orm.dao.*;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import com.wiatec.blive.txcloud.LiveChannelMaster;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class LiveChannelService {

    @Resource
    private LiveChannelDao liveChannelDao;
    @Resource
    private VodChannelDao vodChannelDao;
    @Resource
    private LiveViewDao liveViewDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource
    private RelationFollowDao relationFollowDao;


    public ResultInfo<PageInfo<ChannelInfo>> selectAllAvailableWithUser(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<ChannelInfo> channelInfoList = liveChannelDao.selectAllAvailableWithUserInfo();
        if(channelInfoList == null || channelInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        for(ChannelInfo channelInfo: channelInfoList){
            channelInfo.setPlayUrl(AESUtil.encrypt(channelInfo.getPlayUrl(), AESUtil.KEY));
        }
        PageInfo<ChannelInfo> pageInfo = new PageInfo<>(channelInfoList);
        return ResultMaster.success(pageInfo);
    }

    public ResultInfo<ChannelInfo> selectAllAvailableForBtv(){
        List<ChannelInfo> channelInfoList = liveChannelDao.selectAllAvailableWithUserInfo();
        List<ChannelInfo> channelInfos = vodChannelDao.selectAllAvailableWithUserInfo();
        if(channelInfos != null && channelInfos.size() > 0){
            channelInfos.forEach(channelInfo -> {
                channelInfo.setLiveTime(channelInfo.getCreateTime());
            });
            channelInfoList.addAll(channelInfos);
        }

        for(ChannelInfo channelInfo: channelInfoList){
            channelInfo.setPlayUrl(AESUtil.encrypt(channelInfo.getPlayUrl(), AESUtil.KEY));
        }
        Collections.sort(channelInfoList);
        return ResultMaster.success(channelInfoList);
    }


    @Transactional(rollbackFor = Exception.class)
    public ResultInfo<ChannelInfo> create(int userId, String username){
        ChannelInfo channelInfo = LiveChannelMaster.create(userId);
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
    public List<ChannelInfo> searchByLikeTitle(String key){
        return liveChannelDao.searchByLikeTitle(key);
    }

    /**
     * select channel info by user id
     * @param userId user id
     * @return ChannelInfo
     */
    public ResultInfo<ChannelInfo> selectOneByUserId(int userId){
        ChannelInfo channelInfo = liveChannelDao.selectOneByUserId(userId);
        if(channelInfo == null){
            String username = authRegisterUserDao.selectOneById(userId).getUsername();
            channelInfo =  create(userId, username).getData();
        }
        if(TextUtil.isEmpty(channelInfo.getStreamId()) || !channelInfo.getStreamId().startsWith("24467")){
            return updateChannelUrl(channelInfo);
        }
        if (TextUtil.isEmpty(channelInfo.getPushUrl())) {
            return updateChannelUrl(channelInfo);
        }
        String hexTime = channelInfo.getPushUrl().substring(channelInfo.getPushUrl().length() - 8);
        try {
            long time = Long.valueOf(hexTime, 16);
            // 如果防盗链验证日期减除当前时间小于一天，更换url
            if (time - System.currentTimeMillis() / 1000 < 86400) {
                return updateChannelUrl(channelInfo);
            }
        } catch (Exception e) {
            return updateChannelUrl(channelInfo);
        }
        channelInfo.setPlayUrl(AESUtil.encrypt(channelInfo.getPlayUrl(), AESUtil.KEY));
        return ResultMaster.success(channelInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo<ChannelInfo> updateChannel(int action, ChannelInfo channelInfo){
        if(liveChannelDao.countByUserId(channelInfo.getUserId()) != 1){
            throw new XException("user channel does not exists");
        }
        int result = 0;
        switch (action){
            case 0:
                result = liveChannelDao.updateAllSettingByUserId(channelInfo);
                break;
            case 1:
                result = liveChannelDao.updateTitleAndMessageByUserId(channelInfo);
                break;
            case 2:
                result = liveChannelDao.updateTitleByUserId(channelInfo);
                break;
            case 3:
                result = liveChannelDao.updateMessageByUserId(channelInfo);
                break;
            case 4:
                result = liveChannelDao.updatePriceByUserId(channelInfo);
                break;
            case 5:
                result = liveChannelDao.updateLinkByUserId(channelInfo);
                break;
            default:
                break;
        }
        if(result != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        if(action == 0) {
            ChannelInfo info = liveChannelDao.selectOneByUserId(channelInfo.getUserId());
            if (info == null) {
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
            if(TextUtil.isEmpty(info.getStreamId()) || !info.getStreamId().startsWith("24467")){
                return updateChannelUrl(channelInfo);
            }
            if (TextUtil.isEmpty(info.getPushUrl())) {
                return updateChannelUrl(channelInfo);
            }
            String hexTime = info.getPushUrl().substring(info.getPushUrl().length() - 8);
            try {
                long time = Long.valueOf(hexTime, 16);
                // 如果防盗链验证日期减除当前时间小于一天，更换url
                if (time - System.currentTimeMillis() / 1000 < 86400) {
                    return updateChannelUrl(channelInfo);
                }
            } catch (Exception e) {
                return updateChannelUrl(channelInfo);
            }
        }
        ChannelInfo info = liveChannelDao.selectOneByUserId(channelInfo.getUserId());
        if (info == null) {
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        info.setPlayUrl(AESUtil.encrypt(info.getPlayUrl(), AESUtil.KEY));
        return ResultMaster.success(info);
    }


    private ResultInfo<ChannelInfo> updateChannelUrl(ChannelInfo channelInfo){
        ChannelInfo info = LiveChannelMaster.create(channelInfo.getUserId());
        if(info == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        if(liveChannelDao.updateChannel(info) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        ChannelInfo newInfo = liveChannelDao.selectOneByUserId(channelInfo.getUserId());
        if(newInfo == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        newInfo.setPlayUrl(AESUtil.encrypt(newInfo.getPlayUrl(), AESUtil.KEY));
        return ResultMaster.success(newInfo);
    }


    /**
     * update channel status
     * @param action 1 -> activate, 0 -> deactivate
     * @param userId user id
     * @return ResultInfo
     */
    public ResultInfo updateChannelStatus(int action, int userId){
        ChannelInfo channelInfo;
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
    public ResultInfo<ChannelInfo> updatePreview(ChannelInfo channelInfo){
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
