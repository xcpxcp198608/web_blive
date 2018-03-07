package com.wiatec.blive.service;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.dao.ChannelDao;
import com.wiatec.blive.orm.dao.UserDao;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import org.springframework.stereotype.Service;

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
    private UserDao userDao;

    /**
     * select channels that in living
     * @return list of ChannelInfo
     */
    public List<ChannelInfo> selectAllAvailable(){
        return channelDao.selectAllAvailable();
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
    public ChannelInfo selectOne(int userId){
        return channelDao.selectOneByUserId(userId);
    }

    /**
     * update channel url info
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelUrl(ChannelInfo channelInfo){
        if(channelDao.countByUserId(channelInfo) == 1){
            channelDao.updateChannel(channelInfo);
        }else{
            channelInfo.setTitle(userDao.selectOneById(channelInfo.getUserId()).getUsername());
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
        if(channelDao.countByUserId(channelInfo) != 1){
            throw new XException("user channel does not exists");
        }
        channelDao.updateChannelTitleAndMessage(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel title
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelTitle(ChannelInfo channelInfo){
        if(channelDao.countByUserId(channelInfo) != 1){
            throw new XException("user channel does not exists");
        }
        channelDao.updateChannelTitle(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel message
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelMessage(ChannelInfo channelInfo){
        if(channelDao.countByUserId(channelInfo) != 1){
            throw new XException("user channel does not exists");
        }
        channelDao.updateChannelMessage(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel price
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelPrice(ChannelInfo channelInfo){
        if(channelDao.countByUserId(channelInfo) != 1){
            throw new XException("user channel does not exists");
        }
        channelDao.updateChannelPrice(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }


    /**
     * update channel all setting: title, message, price
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelAllSetting(ChannelInfo channelInfo){
        if(channelDao.countByUserId(channelInfo) != 1){
            throw new XException("user channel does not exists");
        }
        channelDao.updateChannelAllSetting(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }

    /**
     * update channel status
     * @param activate 1 -> activate, 0 -> deactivate
     * @param userId user id
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updateChannelStatus(int activate, int userId){
        if(activate == 1){
            channelDao.updateChannelAvailable(userId);
        }else {
            channelDao.updateChannelUnavailable(userId);
        }
        return ResultMaster.success(channelDao.selectOneByUserId(userId));
    }

    /**
     * update channel preview image
     * @param channelInfo ChannelInfo
     * @return ResultInfo
     */
    public ResultInfo<ChannelInfo> updatePreview(ChannelInfo channelInfo){
        channelDao.updatePreview(channelInfo);
        return ResultMaster.success(channelDao.selectOneByUserId(channelInfo.getUserId()));
    }
}
