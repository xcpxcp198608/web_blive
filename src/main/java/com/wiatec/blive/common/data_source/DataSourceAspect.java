package com.wiatec.blive.common.data_source;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author patrick
 */
@Component
@Aspect
@Order(0) //配置切面优先级，防止被事务注解切面覆盖无法生效
public class DataSourceAspect {


    private final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("execution(public * com.wiatec.blive.service.*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void changeDateSource(JoinPoint jp){
        logger.debug("========= DataSourceHandler");
        logger.debug("========= Method: {}", jp.getSignature().getName());
        try{
            String methodName = jp.getSignature().getName();
            Class<?> targetClass = Class.forName(jp.getTarget().getClass().getName());
            for(Method method : targetClass.getMethods()){
                if(methodName.equals(method.getName())){
                    Class<?>[] args = method.getParameterTypes();
                    if(args.length == jp.getArgs().length){
                        DataSource ds = method.getAnnotation(DataSource.class);
                        if(ds != null) {
                            DataSourceHolder.setDataSources(ds.name());
                        }else{
                            DataSourceHolder.setDataSources(DataSource.DATA_SOURCE_BLIVE);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error("Exception: ", e);
        }
    }

}
