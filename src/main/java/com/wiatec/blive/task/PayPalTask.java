package com.wiatec.blive.task;

import com.wiatec.blive.instance.Application;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PayPalTask {

    @Scheduled(fixedRate = 1000000)
    public void getAccessToken(){
        Application.getInstance().getExecutorService().execute(new PayPalAccessToken());
    }
}
