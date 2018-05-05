package com.wiatec.blive.interceptor;

import com.wiatec.blive.common.cache.LocalCache;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.security.AccessToken;
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
public class AccessTokenInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(AccessTokenInterceptor.class);

    private static final String UUID = "UUID";
    private static final String AUTH = "Authorization";
    private static final String TID = "TID";
    private static final String ID = "ID";
    private static final int EXPIRES = 20000;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AUTH);
        String uuid = request.getHeader(UUID);
        String value = request.getHeader(ID);
        String time = request.getHeader(TID);
        if(TextUtil.isEmpty(token)){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
        if(TextUtil.isEmpty(uuid)){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
        if(TextUtil.isEmpty(value)){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }

        if(!AccessToken.decrypt(token, uuid, value, time)){
            throw new XException(EnumResult.ERROR_ACCESS_TOKEN);
        }

        String s = LocalCache.get(UUID + uuid);
        if(!TextUtil.isEmpty(s)){
            throw new XException(EnumResult.ERROR_ACCESS_TOKEN);
        }
        LocalCache.set(UUID + uuid, uuid);

        long t;
        try {
            t = Long.parseLong(time);
        }catch (Exception e){
            throw new XException(EnumResult.ERROR_ACCESS_TOKEN);
        }
        if((System.currentTimeMillis() - t) > EXPIRES){
            throw new XException(EnumResult.ERROR_ACCESS_TOKEN_TIMEOUT);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
