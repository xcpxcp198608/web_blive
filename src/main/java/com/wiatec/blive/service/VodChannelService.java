package com.wiatec.blive.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.AESUtil;
import com.wiatec.blive.common.utils.TimeUtil;
import com.wiatec.blive.orm.dao.*;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.ChannelPurchaseInfo;
import com.wiatec.blive.orm.pojo.CoinBillInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class VodChannelService {

    @Resource
    private VodChannelDao vodChannelDao;
    @Resource
    private VodPurchaseDao vodPurchaseDao;
    @Resource
    private LiveChannelService liveChannelService;
    @Resource
    private CoinService coinService;

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

    /**
     * 删除vod频道
     * @param userId user id
     * @param videoId video id
     */
    public ResultInfo deleteVodChannel(int userId, String videoId){
        if(vodChannelDao.deleteChannel(userId, videoId) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }


    /**
     * verify that viewer have permission on channel
     * @param channelId channel id
     * @param viewerId viewer id
     * @return ResultInfo
     */
    public ResultInfo checkViewPermission(int channelId, int viewerId){
        ChannelPurchaseInfo purchaseInfo = vodPurchaseDao.selectOne(channelId, viewerId);
        if(purchaseInfo == null){
            throw new XException("no permission");
        }
        if(purchaseInfo.getExpiresTime().before(new Date())){
            throw new XException("permission expires");
        }
        return ResultMaster.success(purchaseInfo);
    }

    /**
     * viewer purchase view permission on channel
     * @param channelId channel id
     * @param viewerId viewer id
     * @param duration permission duration
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo purchasePermission(int channelId, int viewerId, int duration, int coins,
                                         String platform){
        ChannelPurchaseInfo purchaseInfo = vodPurchaseDao.selectOne(channelId, viewerId);
        ChannelInfo channelInfo = vodChannelDao.selectOneByChannelId(channelId);
        if(channelInfo == null){
            throw new XException("channel not exists ");
        }
        ResultInfo resultInfo = coinService.consumeCoin(viewerId, channelInfo.getUserId(),
                CoinBillInfo.CATEGORY_CONSUME_VIEW, coins, platform);
        if(!resultInfo.isSuccess()){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        Date expiresTime = liveChannelService.getPermissionExpiresTime(duration, purchaseInfo);
        int result;
        if(purchaseInfo == null){
            result =vodPurchaseDao.insertOne(channelId, viewerId, expiresTime);
        }else{
            result =vodPurchaseDao.updateOne(channelId, viewerId, expiresTime);
        }
        if(result != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }

}
