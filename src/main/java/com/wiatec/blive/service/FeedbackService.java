package com.wiatec.blive.service;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.dao.FeedbackDao;
import com.wiatec.blive.orm.pojo.FeedbackInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Service
public class FeedbackService {

    private final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    @Resource
    private FeedbackDao feedbackDao;

    public ResultInfo insertOne(FeedbackInfo feedbackInfo){
        if(feedbackInfo.getUserId() <=0){
            throw new XException(EnumResult.ERROR_FORBIDDEN);
        }
        if(feedbackInfo.getSubject() == null || feedbackInfo.getSubject().length() <=0 || feedbackInfo.getSubject().length() > 100){
            throw new XException("subject format error");
        }
        if(feedbackInfo.getDescription() == null || feedbackInfo.getDescription().length() <=0 ){
            throw new XException("description is empty");
        }
        if(feedbackDao.insertOne(feedbackInfo) !=1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }

}
