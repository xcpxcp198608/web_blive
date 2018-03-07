package com.wiatec.blive.service;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.TimeUtil;
import com.wiatec.blive.orm.dao.TokenDao;
import com.wiatec.blive.orm.pojo.TokenInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Service
public class TokenService {

    private static final long DURATION = 2592000000L;

    @Resource
    private TokenDao tokenDao;

    public ResultInfo<TokenInfo> selectOne(String token){
        TokenInfo tokenInfo = tokenDao.selectOneByToken(token);
        if(tokenInfo == null){
            throw new XException("token not exists");
        }
        return ResultMaster.success(tokenInfo);
    }

    public ResultInfo validate(String token){
        if((tokenDao.countOneByToken(token) != 1)){
            throw new XException("token not exists");
        }
        TokenInfo t = tokenDao.selectOneByToken(token);
        if(t == null){
            throw new XException("token not exists");
        }
        long cTime = TimeUtil.getUnixFromStr(t.getCreateTime());
        if(cTime + DURATION < System.currentTimeMillis()){
            throw new XException("token expires");
        }
        return ResultMaster.success();
    }
}
