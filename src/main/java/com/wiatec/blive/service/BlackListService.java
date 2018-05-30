package com.wiatec.blive.service;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.dao.AuthRegisterUserDao;
import com.wiatec.blive.orm.dao.BlackListDao;
import com.wiatec.blive.orm.dao.LogUserOperationDao;
import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import com.wiatec.blive.orm.pojo.BlackListInfo;
import com.wiatec.blive.orm.pojo.LogUserOperationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class BlackListService {

    private final Logger logger = LoggerFactory.getLogger(BlackListService.class);

    @Resource
    private BlackListDao blackListDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource
    private LogUserOperationDao logUserOperationDao;

    public ResultInfo insertOne(int userId, String blackUsername){
        AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneByUsername(blackUsername);
        if(authRegisterUserInfo == null){
            throw new XException("user does not exists");
        }
        if(userId == authRegisterUserInfo.getId()){
            throw new XException("can not do this on yourself");
        }
        if(blackListDao.countOne(userId, authRegisterUserInfo.getId()) == 1){
            throw new XException("the user has been in your black list");
        }
        int i = blackListDao.insertOne(userId, authRegisterUserInfo.getId(), blackUsername);
        if(i != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        logUserOperationDao.insertOne(userId, LogUserOperationInfo.TYPE_INSERT, "add " + blackUsername + " in black list");
        return ResultMaster.success();
    }

    public ResultInfo deleteOne(int userId, String blackUsername){
        AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneByUsername(blackUsername);
        if(authRegisterUserInfo == null){
            throw new XException("user does not exists");
        }
        if(userId == authRegisterUserInfo.getId()){
            throw new XException("can not do this on yourself");
        }
        int i = blackListDao.deleteOne(userId, authRegisterUserInfo.getId());
        if(i != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        logUserOperationDao.insertOne(userId, LogUserOperationInfo.TYPE_DELETE, "remove " + blackUsername + " from black list");
        return ResultMaster.success();
    }

    public ResultInfo listAll(int userId){
        List<BlackListInfo> blackListInfoList = blackListDao.selectAllByUserId(userId);
        if(blackListInfoList == null || blackListInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(blackListInfoList);
    }


    public ResultInfo checkBlack(int userId, int targetUserId){
        if(blackListDao.countOne(userId, targetUserId) == 1){
            throw new XException(555, "Please contact the broadcaster for additional information");
        }
        return ResultMaster.success();
    }

}
