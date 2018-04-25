package com.wiatec.blive.common.http.listener;


import java.io.IOException;

import com.wiatec.blive.common.http.pojo.ResultInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class UploadListener extends BaseListener {

    public abstract void onSuccess(ResultInfo resultInfo) throws Exception;

    public UploadListener() {
        super(null);
    }

    @Override
    protected void handResponse(String jsonString) throws Exception {
    }
}
