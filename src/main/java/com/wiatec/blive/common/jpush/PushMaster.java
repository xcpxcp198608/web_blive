package com.wiatec.blive.common.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patrick
 */
public class PushMaster {

    private static final Logger logger = LoggerFactory.getLogger(PushMaster.class);
    private static final String MASTER_SECRET = "90a2df3e6f1611391366abe3";
    private static final String APP_KEY = "dc2b41f40b74d86e3508127b";
    private static JPushClient jpushClient;

    static {
        jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
    }

    public static void push(PushPayload payload){
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.debug("Got result - {}", result);

        } catch (APIConnectionException e) {
            logger.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            logger.error("Should review the error, and fix the request", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
        }
    }


    public static void main (String [] args){
        PushMaster.push(PushPayloadBuilder.buildForIos("3123"));
    }


}
