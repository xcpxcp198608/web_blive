package com.wiatec.blive.service;

import com.wiatec.blive.entity.ResultInfo;
import com.wiatec.blive.orm.dao.TokenDao;
import com.wiatec.blive.orm.pojo.TokenInfo;
import com.wiatec.blive.xutils.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private static final long DURATION = 2592000000L;

    @Resource
    private TokenDao tokenDao;

    @Transactional
    public TokenInfo selectOne(TokenInfo tokenInfo){
        return tokenDao.selectOne(tokenInfo);
    }

    @Transactional
    public ResultInfo validate(TokenInfo tokenInfo){
        ResultInfo resultInfo = new ResultInfo();
        if(!(tokenDao.countOne(tokenInfo) == 1)){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("token not exists");
            return resultInfo;
        }
        TokenInfo tokenInfo1 = tokenDao.selectOne(tokenInfo);
        long cTime = TimeUtil.getUnixFromStr(tokenInfo1.getCreateTime());
        if(cTime + DURATION < System.currentTimeMillis()){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("token overdue");
        }else {
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setMessage("token validate successfully");
        }
        return resultInfo;
    }
}
