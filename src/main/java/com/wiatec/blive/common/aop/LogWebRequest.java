package com.wiatec.blive.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogWebRequest {

    private Logger logger = LoggerFactory.getLogger(LogWebRequest.class);

    @Pointcut("execution(public * com.wiatec.blive.web.*.*(..))")
    public void httpRequestPointCut(){}

    @Before("httpRequestPointCut()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        logger.debug("======================================= http request start ====================================");
        logger.debug("=");
        logger.debug("= from= {}", request.getSession().getAttribute("username"));
        logger.debug("= url= {}", request.getRequestURL());
        logger.debug("= method= {}", request.getMethod());
        logger.debug("= ip= {}", request.getRemoteAddr());
        logger.debug("= user= {}", request.getRemoteUser());
        logger.debug("= class= {}", joinPoint.getSignature().getDeclaringTypeName());
        logger.debug("= method= {}", joinPoint.getSignature().getName());
        logger.debug("= args= {}", joinPoint.getArgs());
        logger.debug("=");
        logger.debug("===============================================================================================");
    }

    @AfterReturning(returning = "object", pointcut = "httpRequestPointCut()")
    public void afterReturning(Object object){
        logger.debug("======================================== http request end =====================================");
        logger.debug("=");
        logger.debug("= response= {}", object);
        logger.debug("=");
        logger.debug("===============================================================================================");
    }
}
