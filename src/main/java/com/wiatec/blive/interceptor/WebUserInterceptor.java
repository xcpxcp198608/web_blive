package com.wiatec.blive.interceptor;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.TextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author patrick
 */
public class WebUserInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(WebUserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().equals("/blive/users/signin")){
            return true;
        }
        String ref = request.getHeader("Referer");
        if(ref == null || !ref.contains("/blive")){
            throw new XException(EnumResult.ERROR_FORBIDDEN);
        }
        String username = (String) request.getSession().getAttribute("username");
        logger.debug(username);
        if(TextUtil.isEmpty(username)){
            throw new XException("sign in error");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(request.getRequestURI().equals("/blive/users/signin")){
            return;
        }
        String username = (String) request.getSession().getAttribute("username");
        logger.debug(username);
        if(TextUtil.isEmpty(username)){
            throw new XException("sign in error");
        }
        if(modelAndView != null) {
            modelAndView.getModel().put("username", username);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
