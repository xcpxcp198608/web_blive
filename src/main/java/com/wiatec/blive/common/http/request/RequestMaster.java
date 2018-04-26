package com.wiatec.blive.common.http.request;


import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wiatec.blive.common.http.HttpMaster;
import com.wiatec.blive.common.http.configuration.Header;
import com.wiatec.blive.common.http.configuration.Parameters;
import com.wiatec.blive.common.http.listener.UploadListener;
import com.wiatec.blive.common.http.pojo.DownloadInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public abstract class RequestMaster {
    private Header header;
    private Parameters parameters;
    private Object mTag;
    private Map<Object ,Call> callMap = new ConcurrentHashMap<>();
    protected DownloadInfo mDownloadInfo;
    protected boolean isJsonEncoding;

    public RequestMaster() {
        parameters = new Parameters();
        header = new Header();
    }

    public RequestMaster tag(Object tag){
        this.mTag = tag;
        return this;
    }

    public RequestMaster jsonEncodeing(boolean encode){
        this.isJsonEncoding = encode;
        return this;
    }

    public RequestMaster param(String key , String  value){
        parameters.put(key ,value);
        return this;
    }

    public RequestMaster param(String key , File value){
        parameters.put(key ,value);
        return this;
    }

    public RequestMaster param(String key , Object value){
        parameters.put(key ,value);
        return this;
    }

    public RequestMaster params(Parameters parameters){
        this.parameters = parameters;
        return this;
    }

    public RequestMaster headers(String key ,String value){
        header.put (key ,value);
        return this;
    }

    public RequestMaster headers(Header header){
        this.header = header;
        return this;
    }

    protected abstract Request createRequest(Header header, Parameters parameters ,Object tag);

    /**
     * 同步执行请求
     */
    public Response execute(){
        try {
            Request request = createRequest(header, parameters, mTag);
            Call call = HttpMaster.okHttpClient.newCall(request);
            Response response = call.execute();
            return response;
        }catch (Exception e){
            if(e.getMessage() != null) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    //异步执行请求
    public void enqueue (Callback callback){
        try {
            Request request = createRequest(header, parameters, mTag);
            Call call = HttpMaster.okHttpClient.newCall(request);
            call.enqueue(callback);
            if (mTag != null) {
                callMap.put(mTag, call);
            }
        }catch (Exception e){
            if(e.getMessage() != null) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void upload(UploadListener uploadListener){
        try {
            Request request = createRequest(header , parameters ,mTag);
            Call call = HttpMaster.okHttpClient.newCall(request);
            call.enqueue(uploadListener);
        }catch (Exception e){
            if(e.getMessage() != null) {
                System.out.println(e.getMessage());
            }
        }
    }
    //通过标签取消请求
    public void cancel (Object tag){
        Call call = callMap.get(tag);
        if(call != null) {
            call.cancel();
        }
    }
    //取消所有请求
    public void cancelAll(){
        HttpMaster.okHttpClient.dispatcher().cancelAll();
    }
}
