package com.wiatec.blive.txcloud;

import com.wiatec.blive.common.result.ResultInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@RestController
public class EventCallback {

    @Resource private EventCallbackService eventCallbackService;

    @PostMapping(value = "/tx/event")
    public ResultInfo receiveEvent(@RequestBody TXEventInfo txEventInfo){
        ResultInfo resultInfo = new ResultInfo();
        if(eventCallbackService.handleEvent(txEventInfo)) {
            resultInfo.setCode(0);
        }else{
            resultInfo.setCode(500);
        }
        return resultInfo;
    }



    @PostMapping(value = "/tx/event/screenshot")
    public ResultInfo receiveScreenshotEvent(@RequestBody TXScreenshotEventInfo txEventInfo){
        ResultInfo resultInfo = new ResultInfo();
        if(eventCallbackService.handleScreenshotEvent(txEventInfo)) {
            resultInfo.setCode(0);
        }else{
            resultInfo.setCode(500);
        }
        return resultInfo;
    }
}
