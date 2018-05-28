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
import com.wiatec.blive.common.utils.TimeUtil;
import com.wiatec.blive.dto.LiveDaysDistributionInfo;
import com.wiatec.blive.dto.LiveTimeDistributionInfo;
import com.wiatec.blive.dto.LiveViewersInfo;
import com.wiatec.blive.orm.dao.*;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import com.wiatec.blive.orm.pojo.VodChannelInfo;
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
public class VodChannelService {

    @Resource
    private VodChannelDao vodChannelDao;
    @Resource
    private LiveViewDao liveViewDao;
    @Resource
    private RelationFollowDao relationFollowDao;

    public ResultInfo<PageInfo<VodChannelInfo>> selectAllAvailableWithUser(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<VodChannelInfo> vodChannelInfoList = vodChannelDao.selectAllAvailableWithUserInfo();
        if(vodChannelInfoList == null || vodChannelInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        for(VodChannelInfo vodChannelInfo: vodChannelInfoList){
            if(vodChannelInfo.getPlayUrl() != null) {
                vodChannelInfo.setPlayUrl(AESUtil.encrypt(vodChannelInfo.getPlayUrl(), AESUtil.KEY));
            }
        }
        PageInfo<VodChannelInfo> pageInfo = new PageInfo<>(vodChannelInfoList);
        return ResultMaster.success(pageInfo);
    }


    /**
     * select channel info by user id
     * @param userId user id
     * @return ChannelInfo
     */
    public ResultInfo<VodChannelInfo> selectByUserId(int userId){
        List<VodChannelInfo> vodChannelInfoList = vodChannelDao.selectByUserId(userId);
        if(vodChannelInfoList == null || vodChannelInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        for(VodChannelInfo vodChannelInfo: vodChannelInfoList){
            if(vodChannelInfo.getPlayUrl() != null) {
                vodChannelInfo.setPlayUrl(AESUtil.encrypt(vodChannelInfo.getPlayUrl(), AESUtil.KEY));
            }
        }
        return ResultMaster.success(vodChannelInfoList);
    }


    public ResultInfo<VodChannelInfo> selectByUserAndVideoId(int userId, String videoId){
        VodChannelInfo vodChannelInfo = vodChannelDao.selectByUserAndVideoId(userId, videoId);
        if(vodChannelInfo == null){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        if(vodChannelInfo.getPlayUrl() != null) {
            vodChannelInfo.setPlayUrl(AESUtil.encrypt(vodChannelInfo.getPlayUrl(), AESUtil.KEY));
        }
        return ResultMaster.success(vodChannelInfo);
    }


    public ResultInfo<VodChannelInfo> updateChannelSetting(int action, VodChannelInfo channelInfo){
        if(vodChannelDao.countByVideoId(channelInfo.getVideoId()) != 1){
            throw new XException("channel does not exists");
        }
        int result = 0;
        switch (action){
            case 0:
                result = vodChannelDao.updateAllSettingByVideoId(channelInfo);
                break;
            case 1:
                result = vodChannelDao.updateTitleAndMessageByVideoId(channelInfo);
                break;
            case 2:
                result = vodChannelDao.updateTitleByVideoId(channelInfo);
                break;
            case 3:
                result = vodChannelDao.updateMessageByVideoId(channelInfo);
                break;
            case 4:
                result = vodChannelDao.updatePriceByVideoId(channelInfo);
                break;
            case 5:
                result = vodChannelDao.updateLinkByVideoId(channelInfo);
                break;
            default:
                break;
        }
        if(result != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        VodChannelInfo info = vodChannelDao.selectOneByVideoId(channelInfo.getVideoId());
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
    public ResultInfo updateChannelStatus(int action, int userId, String videoId){
        VodChannelInfo channelInfo;
        if(action == 1){
            vodChannelDao.updateAvailableByVideoId(videoId);
            channelInfo = vodChannelDao.selectOneByVideoId(videoId);
//            if(channelInfo != null) {
//                List<Integer> integerList = relationFollowDao.selectFollowersIdByUserId(userId);
//                APNsMaster.batchSend(userId, integerList, APNsMaster.ACTION_LIVE_START, channelInfo.getTitle());
//                //PushMaster.push(PushPayloadBuilder.buildForIos(userInfo.getUsername() + " start live: " + channelInfo.getTitle()));
//            }
        }else {
            vodChannelDao.updateUnavailableByVideoId(videoId);
        }
        return ResultMaster.success();
    }

    /**
     * update channel preview image
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<VodChannelInfo> updatePreview(VodChannelInfo channelInfo){
        vodChannelDao.updatePreviewByVideoId(channelInfo);
        return ResultMaster.success(vodChannelDao.selectOneByVideoId(channelInfo.getVideoId()));
    }

}
