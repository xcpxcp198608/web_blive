package com.wiatec.blive.common.http.request;


import java.util.Map;

import com.wiatec.blive.common.http.configuration.Header;
import com.wiatec.blive.common.http.configuration.Parameters;
import okhttp3.Headers;
import okhttp3.Request;


public class GetRequest extends RequestMaster {

    private String url;
    public GetRequest(String url) {
        this.url = url;
    }

    @Override
    protected Request createRequest(Header header, Parameters parameters , Object tag) {
        Request.Builder builder = new Request.Builder();
        if(header !=null){
            Headers headers = Headers.of(header.stringMap);
            builder.headers(headers);
        }
        if(parameters != null){
            StringBuilder stringBuilder = new StringBuilder(url);
            stringBuilder.append("?");
            for(Map.Entry<String,String> entry : parameters.stringMap.entrySet()){
                stringBuilder.append(entry.getKey()+"=").append(entry.getValue()+"&");
            }
            url = stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);
        }
        if(tag != null){
            builder.tag(tag);
        }
        //Logger.d(url);
        builder.get().url(url);
        builder.tag("get");
        return builder.build();
    }
}
