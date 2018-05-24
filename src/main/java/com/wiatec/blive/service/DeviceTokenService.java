package com.wiatec.blive.service;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.orm.dao.DeviceTokenDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Service
public class DeviceTokenService {

    @Resource private DeviceTokenDao deviceTokenDao;


    public ResultInfo storeDeviceToken(int userId, String deviceToken){
        int re = 0;
        if(deviceTokenDao.countOne(userId) == 1){
            re = deviceTokenDao.updateOne(userId, deviceToken);
        }else{
            re = deviceTokenDao.insertOne(userId, deviceToken);
        }
        if(re != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }

    public ResultInfo getDeviceToken(int userId){
        String deviceToken = deviceTokenDao.selectOne(userId);
        if(TextUtil.isEmpty(deviceToken)){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success(deviceToken);
    }
}
