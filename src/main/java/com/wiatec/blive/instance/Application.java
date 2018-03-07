package com.wiatec.blive.instance;

import okhttp3.OkHttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author patrick
 */
public class Application {

    private ExecutorService executorService;
    private String payAccessToken = "";
    private OkHttpClient okHttpClient;

    private Application(){
        executorService = Executors.newCachedThreadPool();
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    private static volatile Application instance;

    public static synchronized Application getInstance(){
        if(instance == null){
            synchronized (Application.class){
                if(instance == null){
                    instance = new Application();
                }
            }
        }
        return instance;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public String getPayAccessToken() {
        return payAccessToken;
    }

    public void setPayAccessToken(String payAccessToken) {
        this.payAccessToken = payAccessToken;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
