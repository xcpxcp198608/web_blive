package com.wiatec.blive.rtmp;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author patrick
 */
public class RtmpMaster {

    private final Logger logger = LoggerFactory.getLogger(RtmpMaster.class);

    public RtmpInfo getRtmpInfo(String username){
        String url = "http://apilive.bvision.live/v1/blive_get_url.do?username="+username+"&token=36d4284ce8e188eb75bda72cb1de28c7";
        RtmpInfo rtmpInfo = new RtmpInfo();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            URLConnection urlConnection = new URL(url).openConnection();
            inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine()) != null){
                builder.append(line);
            }
            String result =  builder.toString();
            logger.debug("rtmp server result: {}", result);
            JSONObject jsonObject = new JSONObject(result);
            rtmpInfo.setUsername(username);
            rtmpInfo.setPush_url(jsonObject.getJSONObject("data").getString("push_url"));
            rtmpInfo.setPush_key(jsonObject.getJSONObject("data").getString("push_key"));
            rtmpInfo.setPush_full_url(jsonObject.getJSONObject("data").getString("push_full_url"));
            rtmpInfo.setPlay_url(jsonObject.getJSONObject("data").getString("play_url"));
            return rtmpInfo;
        } catch (Exception e){
            logger.error("rtmp server error:", e);
        }finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.debug(e.getMessage());
            }
        }
        return null;
    }

}
