package com.wiatec.blive.common.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class XExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(XExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultInfo handleException(Exception e){
        if(e instanceof XException){
            XException xException = (XException) e;
            return ResultMaster.error(xException.getCode(), xException.getMessage());
        }else {
            logger.debug("= [system exception]= {}", e.getMessage());
            return ResultMaster.error(1001, e.getMessage());
        }
    }
}
