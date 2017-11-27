package com.wiatec.blive.service;

import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.entity.ResultInfo;
import com.wiatec.blive.orm.dao.ChannelDao;
import com.wiatec.blive.orm.dao.UserDao;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChannelService {

    @Resource
    private ChannelDao channelDao;

    @Resource
    private UserDao userDao;

    @Transactional
    public List<ChannelInfo> selectAllAvailable(){
        return channelDao.selectAllAvailable();
    }

    @Transactional
    public ChannelInfo selectOne(ChannelInfo channelInfo){
        return channelDao.selectOneByUserId(channelInfo);
    }

    @Transactional
    public ResultInfo<ChannelInfo> updateChannel(ChannelInfo channelInfo){
        ResultInfo<ChannelInfo> resultInfo = new ResultInfo<>();
        try{
            if(channelDao.countUserId(channelInfo) == 1){
                channelDao.updateChannel(channelInfo);
            }else{
                channelInfo.setTitle(userDao.selectOneById(new UserInfo(channelInfo.getUserId())).getUsername());
                channelDao.insertChannel(channelInfo);
            }
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setT(channelDao.selectOneByUserId(channelInfo));
            resultInfo.setMessage("update successfully");
            return resultInfo;
        }catch (Exception e){
            resultInfo.setCode(ResultInfo.CODE_SERVER_ERROR);
            resultInfo.setStatus(ResultInfo.STATUS_SERVER_ERROR);
            resultInfo.setMessage("channel server error");
            return resultInfo;
        }
    }

    @Transactional
    public ResultInfo<ChannelInfo> updateChannelTitle(ChannelInfo channelInfo){
        ResultInfo<ChannelInfo> resultInfo = new ResultInfo<>();
        try{
            if(channelDao.countUserId(channelInfo) == 1){
                channelDao.updateChannelTitle(channelInfo);
                resultInfo.setCode(ResultInfo.CODE_OK);
                resultInfo.setStatus(ResultInfo.STATUS_OK);
                resultInfo.setT(channelDao.selectOneByUserId(channelInfo));
                resultInfo.setMessage("update successfully");
            }else{
                resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
                resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
                resultInfo.setMessage("signin error ,please signin again");
            }
            return resultInfo;
        }catch (Exception e){
            resultInfo.setCode(ResultInfo.CODE_SERVER_ERROR);
            resultInfo.setStatus(ResultInfo.STATUS_SERVER_ERROR);
            resultInfo.setMessage("update error");
            return resultInfo;
        }
    }

    @Transactional
    public ResultInfo<ChannelInfo> updateChannelPrice(ChannelInfo channelInfo){
        ResultInfo<ChannelInfo> resultInfo = new ResultInfo<>();
        try{
            if(channelDao.countUserId(channelInfo) == 1){
                channelDao.updateChannelPrice(channelInfo);
                resultInfo.setCode(ResultInfo.CODE_OK);
                resultInfo.setStatus(ResultInfo.STATUS_OK);
                resultInfo.setT(channelDao.selectOneByUserId(channelInfo));
                resultInfo.setMessage("update successfully");
            }else{
                resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
                resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
                resultInfo.setMessage("signin error ,please signin again");
            }
            return resultInfo;
        }catch (Exception e){
            resultInfo.setCode(ResultInfo.CODE_SERVER_ERROR);
            resultInfo.setStatus(ResultInfo.STATUS_SERVER_ERROR);
            resultInfo.setMessage("update error");
            return resultInfo;
        }
    }

    @Transactional
    public ResultInfo<ChannelInfo> updateChannelStatus(int activate, int userId){
        ResultInfo<ChannelInfo> resultInfo = new ResultInfo<>();
        try{
            if(activate == 1){
                channelDao.updateChannelAvailable(new ChannelInfo(userId));
                resultInfo.setMessage("activate successfully");
            }else {
                channelDao.updateChannelUnavailable(new ChannelInfo(userId));
                resultInfo.setMessage("deactivate successfully");
            }
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setT(channelDao.selectOneByUserId(new ChannelInfo(userId)));
            return resultInfo;
        }catch (Exception e){
            resultInfo.setCode(ResultInfo.CODE_SERVER_ERROR);
            resultInfo.setStatus(ResultInfo.STATUS_SERVER_ERROR);
            resultInfo.setMessage("channel server error");
            return resultInfo;
        }
    }

    @Transactional
    public ResultInfo<ChannelInfo> updatePreview(ChannelInfo channelInfo){
        ResultInfo<ChannelInfo> resultInfo = new ResultInfo<>();
        if(channelInfo.getUserId() <=0 || TextUtil.isEmpty(channelInfo.getPreview())){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("missing upload parameters");
            return resultInfo;
        }
        channelDao.updatePreview(channelInfo);
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_OK);
        resultInfo.setMessage("upload successfully");
        resultInfo.setT(channelDao.selectOneByUserId(channelInfo));
        return resultInfo;
    }
}
