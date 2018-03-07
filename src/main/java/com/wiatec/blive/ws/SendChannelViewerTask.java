package com.wiatec.blive.ws;

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
            liveSocket.sendMessage("blive group count:" + count);
        }
    }
}
