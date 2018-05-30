package com.wiatec.blive.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.AESUtil;
import com.wiatec.blive.orm.dao.*;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class VodChannelService {

    @Resource
    private VodChannelDao vodChannelDao;

    public ResultInfo<PageInfo<ChannelInfo>> selectAllAvailableWithUser(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<ChannelInfo> channelInfoList = getAllAvailableWithUser();
        PageInfo<ChannelInfo> pageInfo = new PageInfo<>(channelInfoList);
        return ResultMaster.success(pageInfo);
    }

    public List<ChannelInfo> getAllAvailableWithUser(){
        List<ChannelInfo> channelInfoList = vodChannelDao.selectAllAvailableWithUserInfo();
        if(channelInfoList == null || channelInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        for(ChannelInfo channelInfo: channelInfoList){
            if(channelInfo.getPlayUrl() != null) {
                channelInfo.setPlayUrl(AESUtil.encrypt(channelInfo.getPlayUrl(), AESUtil.KEY));
            }
        }
        return channelInfoList;
    }


    /**
     * select channel info by user id
     * @param userId user id
     * @return ChannelInfo
     */
    public ResultInfo<ChannelInfo> selectByUserId(int userId){
        List<ChannelInfo> channelInfoList = vodChannelDao.selectByUserId(userId);
        if(channelInfoList == null || channelInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        for(ChannelInfo channelInfo: channelInfoList){
            if(channelInfo.getPlayUrl() != null) {
                channelInfo.setPlayUrl(AESUtil.encrypt(channelInfo.getPlayUrl(), AESUtil.KEY));
            }
        }
        return ResultMaster.success(channelInfoList);
    }


    public ResultInfo<ChannelInfo> selectByUserAndVideoId(int userId, String videoId){
        ChannelInfo channelInfo = vodChannelDao.selectByUserAndVideoId(userId, videoId);
        if(channelInfo == null){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        if(channelInfo.getPlayUrl() != null) {
            channelInfo.setPlayUrl(AESUtil.encrypt(channelInfo.getPlayUrl(), AESUtil.KEY));
        }
        return ResultMaster.success(channelInfo);
    }


    public ResultInfo<ChannelInfo> updateChannelSetting(int action, ChannelInfo channelInfo){
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
                result = vodChannelDao.updateTitleByVideoId(channelInfo.getVideoId(), channelInfo.getTitle());
                break;
            case 3:
                result = vodChannelDao.updateMessageByVideoId(channelInfo.getVideoId(), channelInfo.getMessage());
                break;
            case 4:
                result = vodChannelDao.updatePriceByVideoId(channelInfo.getVideoId(), channelInfo.getPrice());
                break;
            case 5:
                result = vodChannelDao.updateLinkByVideoId(channelInfo.getVideoId(), channelInfo.getLink());
                break;
            default:
                break;
        }
        if(result != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        ChannelInfo info = vodChannelDao.selectOneByVideoId(channelInfo.getVideoId());
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
    public ResultInfo updateChannelStatus(int action, int userId, String[] videoIds){
        int result = 0;
        if(action == 1){
            result = vodChannelDao.updateAvailableByVideoIds(videoIds);
//            if(channelInfo != null) {
//                List<Integer> integerList = relationFollowDao.selectFollowersIdByUserId(userId);
//                APNsMaster.batchSend(userId, integerList, APNsMaster.ACTION_LIVE_START, channelInfo.getTitle());
//                //PushMaster.push(PushPayloadBuilder.buildForIos(userInfo.getUsername() + " start live: " + channelInfo.getTitle()));
//            }
        }else {
            result = vodChannelDao.updateUnavailableByVideoIds(videoIds);
        }
        if(result != videoIds.length){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }

    /**
     * update channel preview image
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updatePreview(String videoId, String preview){
        if(vodChannelDao.updatePreviewByVideoId(videoId, preview) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        ChannelInfo info = vodChannelDao.selectOneByVideoId(videoId);
        if(info == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success(info);
    }

}
