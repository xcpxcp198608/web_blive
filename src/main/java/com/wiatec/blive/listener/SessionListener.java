package com.wiatec.blive.listener;

import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;

/**
 * monitor session
 */
public class SessionListener implements HttpSessionListener,HttpSessionAttributeListener {

    public static Map<String ,HttpSession> sessionMap = new HashMap<>();
    public static Map<String ,HttpSession> userSessionMap = new HashMap<>();
    public static final String KEY = "key";
    public static final String KEY_USER_NAME = "userName";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println(httpSessionEvent.getSession().getId()+" created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println(httpSessionEvent.getSession().getId()+" destroyed");
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
            userSessionMap.put(userName, httpSessionBindingEvent.getSession());
        }
    }

    public static HttpSession getSession (String userName){
        return sessionMap.get(userName);
    }

    public static HttpSession getUserSession (String userName){
        return userSessionMap.get(userName);
    }
}
