package com.wiatec.blive.task;

import com.wiatec.blive.instance.Application;
import com.wiatec.blive.xutils.LoggerUtil;
import okhttp3.*;
import org.json.JSONObject;
import sun.net.www.http.HttpClient;

import javax.json.JsonObject;
import java.io.IOException;

public class PayPalAccessToken implements Runnable {

//    private final String URL = "https://api.sandbox.paypal.com/v1/oauth2/token";
    private final String URL = "https://api.paypal.com/v1/oauth2/token";
//    private final String AUTH = "Basic QWExQjI1X01mcjlIemYxOGtPdW5nalNDWmFqM2s2X2I0LTNzTXBuWDhIeGZCWTY1ZUpmUDNFMHQycU85MC1OdWU3VkFRcGdKQWstbmN1ZFo6RUQyMERwTHZFRFlNUU1fYk1oV3pMaUFGZnN6NHdwZVUyRGJtNjhwMXNkWndaVlVSU1pSV1dVWjRTdTZsTHg3NVU5RXRlOUZ5dXprT19LNWM="; //PATRICK
    private final String AUTH = "Basic QWNpekpXaml3TGdLYllSVkFkTnRON0MyRFdpcEVXLVJBbWFfYk1iSzRKRTNfOXQxUm9NMHNzVnJxNDMydWc1NDVhc3BlVzZzRThEa1JfTTQ6RUhGejYzNkZqQU1pam1SWXFEaVhmbEJtNFZmV3lRanJqaE5YQWlWYVh2YVFrZmQwcjZIOGNZMThpUkNGMUpyd1AyMDI4R1UzeU1UQ3lzWXY="; //PATRICK LIVE
    private final String grant_type = "client_credentials";

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
        builder1.add("grant_type","client_credentials");
        builder.post(builder1.build());
        builder.url(URL);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LoggerUtil.d(e.getMessage());
                getAccessToken();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String accessToken = jsonObject.getString("access_token");
                    int expires = jsonObject.getInt("expires_in");
                    Application.getInstance().setPayAccessToken(accessToken);
                    LoggerUtil.d(expires);
                }catch (Exception e){
                    LoggerUtil.d(e.getMessage());
                }
            }
        });
    }
}
