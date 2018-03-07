package com.wiatec.blive.task;

import com.wiatec.blive.instance.Application;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author patrick
 */
@Component
public class PayPalTask {

    @Scheduled(fixedRate = 3000000)
    public void getAccessToken(){
        Application.getInstance().getExecutorService().execute(new PayPalAccessToken());
    }
}
