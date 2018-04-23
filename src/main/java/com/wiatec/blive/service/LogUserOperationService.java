package com.wiatec.blive.service;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.dao.LogUserOperationDao;
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
public class LogUserOperationService {

    private final Logger logger = LoggerFactory.getLogger(LogUserOperationService.class);

    @Resource
    private LogUserOperationDao logUserOperationDao;

    public ResultInfo<LogUserOperationInfo> getOperations(int userId){
        List<LogUserOperationInfo> logUserOperationInfoList = logUserOperationDao.selectAllByUserId(userId);
        if(logUserOperationInfoList == null || logUserOperationInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(logUserOperationInfoList);
    }
}
