package com.wiatec.blive.apns;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.wiatec.blive.common.utils.ApplicationContextHelper;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.orm.dao.DeviceTokenDao;
import com.wiatec.blive.orm.pojo.DeviceTokenInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author patrick
 */
@Component
public class APNsMaster {

    private static final Logger logger = LoggerFactory.getLogger(APNsMaster.class);

    private static final String TOKEN_41 = "809267062d69e53db70ab23622317b959e97b29ee72d3b4fee8438e2360ea887";

    public static final String ACTION_LIVE_START = "ACTION_LIVE_START";
    public static final String ACTION_TRENDING = "ACTION_TRENDING";
    public static final String ACTION_NEW_FRIEND_REQUEST = "ACTION_NEW_FRIEND_REQUEST";

    private static final String PATH_LOCAL = "/Users/xuchengpeng/IdeaProjects/blive/src/main/resources/aps_development.com.legacy.bvision.p12";
    private static final String PATH_PRODUCT = "D:/blive/web/WEB-INF/classes/aps_development.com.legacy.bvision.p12";

    private static ApnsService service;
    private static DeviceTokenDao deviceTokenDao;

    static {
        try {
            SqlSession sqlSession = (SqlSession) ApplicationContextHelper.getApplicationContext().getBean("sqlSessionTemplate");
            deviceTokenDao = sqlSession.getMapper(DeviceTokenDao.class);
            service = APNS.newService()
                    .withCert(PATH_LOCAL, "123456")
                    .withSandboxDestination()
                    .build();
        } catch (Exception e) {
            logger.error("apnsClient create error", e);
        }
    }

    public static void send(int playerId, int userId, String action, String content){
        String token = deviceTokenDao.selectOne(userId);
        if(TextUtil.isEmpty(token)){
            return;
        }
        String payload = APNS.newPayload()
                .badge(1)
                .alertAction(action)
                .alertBody(content)
                .customField("sendUserId", playerId)
                .sound("default")
                .build();
        service.push(token, payload);
    }


    public static void batchSend(int playerId, List<Integer> userIds, String action, String content){
        List<DeviceTokenInfo> deviceTokenInfos = deviceTokenDao.batchSelect(userIds);
        if(deviceTokenInfos == null || deviceTokenInfos.size() <= 0){
            return;
        }
        String title = "";
        if(ACTION_LIVE_START.equals(action)){
            title = "Live start";
        }

        for (DeviceTokenInfo deviceTokenInfo: deviceTokenInfos){
            String payload = APNS.newPayload()
                    .badge(1)
                    .sound("default")
                    .alertAction(action)
                    .alertTitle(title)
                    .alertBody(content)
                    .actionKey(playerId+"")
                    .build();
            service.push(deviceTokenInfo.getDeviceToken(), payload);
        }
    }



}
