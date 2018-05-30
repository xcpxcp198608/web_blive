package com.wiatec.blive.ws;

import com.google.gson.Gson;
import com.wiatec.blive.dto.ChannelCommentInfo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author patrick
 */
@Component
public class SendChannelViewerTask {


    @Scheduled(fixedRate = 5000)
    public void executeTask() {
        Map<Integer, LiveSocket> clientMap = LiveSocket.clientMap;
        for(Map.Entry<Integer, LiveSocket> entry: clientMap.entrySet()){
            LiveSocket liveSocket = entry.getValue();
            int count = LiveSocket.getCountByGroupId(liveSocket.getGroupId());
            ChannelCommentInfo channelCommentInfo = new ChannelCommentInfo();
            channelCommentInfo.setPusherId(liveSocket.getGroupId());
            channelCommentInfo.setScope(ChannelCommentInfo.SCOPE_GROUP);
            channelCommentInfo.setType(ChannelCommentInfo.TYPE_LIVE_VIEWERS);
            channelCommentInfo.setViewers(count);
            liveSocket.sendMessage(new Gson().toJson(channelCommentInfo));
        }
    }
}
