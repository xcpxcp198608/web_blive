package com.wiatec.blive.service;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.dao.ClipsDao;
import com.wiatec.blive.orm.dao.ImageAdDao;
import com.wiatec.blive.orm.pojo.ClipsInfo;
import com.wiatec.blive.orm.pojo.ImageAdInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class ImageAdService {

    private final Logger logger = LoggerFactory.getLogger(ImageAdService.class);

    @Resource
    private ImageAdDao imageAdDao;

    public ResultInfo selectByPosition(int position){
        List<ImageAdInfo> imageAdInfoList = imageAdDao.selectByPosition(position);
        if(imageAdInfoList == null || imageAdInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(imageAdInfoList);
    }
}
