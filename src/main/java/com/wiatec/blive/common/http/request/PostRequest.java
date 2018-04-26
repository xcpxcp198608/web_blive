package com.wiatec.blive.common.http.request;


import java.util.Map;

import com.wiatec.blive.common.http.configuration.Header;
import com.wiatec.blive.common.http.configuration.Parameters;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostRequest extends RequestMaster {

    private String url;

    public PostRequest (String  url){
        this.url = url;
    }


    @Override
    protected Request createRequest(Header header, Parameters parameters , Object tag) {
        Request.Builder builder = new Request.Builder();
        if(header != null){
            Headers headers = Headers.of(header.stringMap);
            builder.headers(headers);
        }
        if(parameters != null){
            MediaType mediaType;
            if (isJsonEncoding){
                mediaType = MediaType.parse("application/json; charset=utf-8");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("{");
                for (Map.Entry<String ,String > entry : parameters.stringMap.entrySet()){
                    stringBuilder.append("\"")
                            .append(entry.getKey())
                            .append("\": \"")
                            .append(entry.getValue())
                            .append("\",");
                }
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
                stringBuilder.append("}");
                RequestBody requestBody = RequestBody.create(mediaType, stringBuilder.toString());
                System.out.println(requestBody.toString());
                builder.post(requestBody);
            }else {
                mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
                StringBuilder stringBuilder = new StringBuilder();
                for (Map.Entry<String ,String > entry : parameters.stringMap.entrySet()){
                    stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                RequestBody requestBody = RequestBody.create(mediaType, stringBuilder.toString());
                builder.post(requestBody);
            }
        }
        if(tag != null){
            builder.tag(tag);
        }
        builder.url(url);
        builder.tag("post");
        return builder.build();
    }
}
