package com.wiatec.blive.listener;

import com.wiatec.blive.xutils.LoggerUtil;

import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;

/**
 * monitor session
 */
public class SessionListener implements HttpSessionListener,HttpSessionAttributeListener {

    public static Map<String ,HttpSession> sessionMap = new HashMap<>();
    public static Map<String ,HttpSession> userSessionMap = new HashMap<>();
    public static Map<String ,HttpSession> idSessionMap = new HashMap<>();
    public static final String KEY = "key";
    public static final String KEY_USER_NAME = "username";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println(httpSessionEvent.getSession().getId()+" created");
        idSessionMap.put(httpSessionEvent.getSession().getId(), httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println(httpSessionEvent.getSession().getId()+" destroyed");
        idSessionMap.remove(httpSessionEvent.getSession().getId());
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        if(KEY.equals(httpSessionBindingEvent.getName())){
            String userName = (String) httpSessionBindingEvent.getValue();
            HttpSession httpSession = sessionMap.remove(userName);
            if(httpSession != null){
                httpSession.removeAttribute(KEY);
            }
            sessionMap.put(userName, httpSessionBindingEvent.getSession());
        }
        if(KEY_USER_NAME.equals(httpSessionBindingEvent.getName())){
            String userName = (String) httpSessionBindingEvent.getValue();
            HttpSession httpSession = userSessionMap.remove(userName);
            if(httpSession != null){
                httpSession.removeAttribute(KEY_USER_NAME);
            }
            userSessionMap.put(userName, httpSessionBindingEvent.getSession());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        if(KEY.equals(httpSessionBindingEvent.getName())){
            try {
                String userName = (String) httpSessionBindingEvent.getValue();
                sessionMap.remove(userName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(KEY_USER_NAME.equals(httpSessionBindingEvent.getName())){
            try {
                String userName = (String) httpSessionBindingEvent.getValue();
                userSessionMap.remove(userName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        if(KEY.equals(httpSessionBindingEvent.getName())){
            String userName = (String) httpSessionBindingEvent.getValue();
            sessionMap.remove(userName);
            sessionMap.put(userName, httpSessionBindingEvent.getSession());
        }
        if(KEY_USER_NAME.equals(httpSessionBindingEvent.getName())){
            String userName = (String) httpSessionBindingEvent.getValue();
            userSessionMap.remove(userName);
            userSessionMap.remove(httpSessionBindingEvent.getSession().getId());
        }
    }

    public static HttpSession getSession (String userName){
        return sessionMap.get(userName);
    }

    public static HttpSession getUserSession (String userName){
        return userSessionMap.get(userName);
    }

    public static HttpSession getIdSession (String sessionId){
        return idSessionMap.get(sessionId);
    }

    public static HttpSession setIdSession (HttpSession session){
        return idSessionMap.put(session.getId(), session);
    }
}
