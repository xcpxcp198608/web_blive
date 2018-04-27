package com.wiatec.blive.service;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.dao.ClipsDao;
import com.wiatec.blive.orm.pojo.ClipsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class ClipsService {

    private final Logger logger = LoggerFactory.getLogger(ClipsService.class);

    @Resource
    private ClipsDao clipsDao;

    public ResultInfo getAllVisible(){
        List<ClipsInfo> clipsInfoList = clipsDao.selectAllVisible();
        if(clipsInfoList == null || clipsInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(clipsInfoList);
    }
}
