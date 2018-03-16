package com.wiatec.blive.common.aop;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.TextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author patrick
 */
@Aspect
@Component
public class AuthShowUsername {

//    @Around("execution(* com.wiatec.blive.service.*.*(..))")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Object[] args = joinPoint.getArgs();
        Model model = null;
        for(Object o: args){
            if(o instanceof  Model){
                model = (Model) o;
            }
        }
        if(request != null && model != null) {
            String username = (String) request.getSession().getAttribute("username");
            if(TextUtil.isEmpty(username)){
                throw new XException(EnumResult.ERROR_FORBIDDEN);
            }
            model.addAttribute("username", username);
        }
        return joinPoint.proceed();
    }
}
