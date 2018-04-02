package com.wiatec.blive.common.jpush;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;

/**
 * @author patrick
 */
public class PushPayloadBuilder {

    private final Logger logger = LoggerFactory.getLogger(PushPayloadBuilder.class);

    public static PushPayload buildForIos(String message) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(message)
                                .setBadge(1)
                                .setSound("happy")
                                .addExtra("from", "bvision")
                                .build())
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent("sdfdsf")
                        .addExtra("from", "bvision")
                        .build())
                .build();
    }

    public static PushPayload buildForAndroid(String message) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(message)
                                .addExtra("from", "bvision")
                                .build())
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent("sdfdsf")
                        .addExtra("from", "bvision")
                        .build())
                .build();
    }

}
