package com.wiatec.blive.xutils;

import org.apache.log4j.Logger;

/**
 * Created by xuchengpeng on 09/06/2017.
 */
public class LoggerUtil {

    private static Logger logger = Logger.getLogger(LoggerUtil.class.getName());

    static {
        logger = Logger.getLogger("");
    }


    public static void d (Object message){
        logger.debug("-->  "+message);
    }

    public static void i (Object message){
        logger.info(message);
    }

    public static void w (Object message){
        logger.warn(message);
    }

    public static void e (Object message){
        logger.error(message);
    }
}
