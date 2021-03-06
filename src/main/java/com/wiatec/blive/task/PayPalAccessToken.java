package com.wiatec.blive.task;

import com.wiatec.blive.instance.Application;
import okhttp3.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author patrick
 */
public class PayPalAccessToken implements Runnable {

    private Logger logger = LoggerFactory.getLogger(PayPalAccessToken.class);

    private final String URL_SANDBOX = "https://api.sandbox.paypal.com/v1/oauth2/token";
    private final String URL = "https://api.paypal.com/v1/oauth2/token";
    private final String AUTH = "Basic QVVJb0V3WEdCV1Q0anNTUklDT1U1SFRUWHJvcDdPRnNFUXJJeUFOa2J3RnhMOTgyWXUyZVNNa29sd0FhOEUwelZzbG82ZDJFN1UwbVd2dno6RUp3SF9wb19ER2xHTzBJZEVqVWdHeVBXNWw4TjA2YWlrVTAwalAyMkduOE5fbGg2ZkVyenJHaTNoM3hoSkw4d0hDUjh4OWFZN2RFaDh3QlM=";
    private final String GRANT_TYPE = "client_credentials";

    @Override
    public void run() {
        getAccessToken();
    }

    private void getAccessToken() {
        OkHttpClient okHttpClient = Application.getInstance().getOkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.addHeader("Content-type", "application/x-www-form-urlencoded");
        builder.addHeader("Authorization", AUTH);
        FormBody.Builder builder1 = new FormBody.Builder();
        builder1.add("grant_type",GRANT_TYPE);
        builder.post(builder1.build());
        builder.url(URL);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.debug(e.getMessage());
                getAccessToken();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String accessToken = jsonObject.getString("access_token");
                    int expires = jsonObject.getInt("expires_in");
                    Application.getInstance().setPayAccessToken(accessToken);
                    logger.debug(accessToken);
                    logger.debug(expires+"");
//                    if(expires < 1000){
//                        Timer timer = new Timer();
//                        timer.schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                logger.debug("time task run");
//                                getAccessToken();
//                            }
//                        }, expires*1000);
//                    }
                }catch (Exception e){
                    logger.debug(e.getMessage());
                }
            }
        });
    }
}
