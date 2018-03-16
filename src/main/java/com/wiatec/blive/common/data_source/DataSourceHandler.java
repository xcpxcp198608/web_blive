package com.wiatec.blive.common.data_source;

import com.wiatec.blive.common.data_source.DataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author patrick
 */
//@Component
//@Aspect
//public class DataSourceHandler {
//
//    private final Logger logger = LoggerFactory.getLogger(DataSourceHandler.class);
//
//    @Pointcut("execution(public * com.wiatec.blive.service.panel.*.*(..))")
//    public void pointCut(){}
//
//    @Before("pointCut()")
//    public void before(JoinPoint joinPoint){
//        DataSourceHolder.setDataSources("panelDataSource");
//        logger.debug("========= PanelDataSourceHandler");
//        logger.debug("========= Method: {}", joinPoint.getSignature().getName());
//    }
//
//}
