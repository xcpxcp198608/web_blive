package com.wiatec.blive.common.http.interceptors;


import java.io.IOException;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.util.TextUtils;

/**
 * Created by patrick on 06/01/2018.
 * create time : 9:58 AM
 */

public class SessionInterceptor implements Interceptor {

    private static final String KEY = "cookie";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        Response response;
        try{
            response = chain.proceed(requestBuilder.build());
            return response;
        }catch(Exception e){
            return chain.proceed(request);
        }
    }
}
