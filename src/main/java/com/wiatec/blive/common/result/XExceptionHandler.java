package com.wiatec.blive.common.result;

import com.wiatec.blive.common.utils.TextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author patrick
 */
@ControllerAdvice
public class XExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(XExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultInfo handleException(Exception e){
        if(e instanceof XException){
            XException xException = (XException) e;
            logger.error("x exception", e);
            return ResultMaster.error(xException.getCode(), xException.getMessage());
        }else {
            logger.error("system exception", e);
            String message = e.getMessage();
            if (TextUtil.isEmpty(message)) {
                return ResultMaster.error(EnumResult.ERROR_INTERNAL_SERVER);
            }else {
                return ResultMaster.error(message);
            }
        }
    }
}
