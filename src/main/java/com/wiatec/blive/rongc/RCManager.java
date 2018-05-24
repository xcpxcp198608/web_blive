package com.wiatec.blive.rongc;

import com.wiatec.blive.common.http.HttpMaster;
import com.wiatec.blive.common.http.configuration.Header;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

/**
 * @author patrick
 */
public class RCManager {

    private static final Logger logger = LoggerFactory.getLogger(RCManager.class);

    private static final String APP_KEY = "pwe86ga5pvv96";
    private static final String SECRET = "2GUBE5iG1N";
    private static final String URL = "http://api.cn.ronghub.com/user/getToken.json";

    private static Header createHeader() {
        Random random = new Random();
        String nonce = random.nextInt(100000) + "";
        String timeStamp = System.currentTimeMillis() + "";
        String signature = DigestUtils.sha1Hex(SECRET + nonce + timeStamp);
        Header header = new Header();
        header.put("App-Key", APP_KEY);
        header.put("Nonce", nonce);
        header.put("Timestamp", timeStamp);
        header.put("Signature", signature);
        header.put("Content-Type", "application/x-www-form-urlencoded");
        return header;

    }

    public static String getToken(int userId, String name, String iconUri) {
        try {
            Response response = HttpMaster.post(URL)
                    .headers(createHeader())
                    .param("userId", userId + "")
                    .param("name", name)
                    .param("portraitUri", iconUri)
                    .execute();
            String result = null;
                result = response.body().string();
            if (response.code() == 200){
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.getInt("code") == 200) {
                    return jsonObject.getString("token");
                }
            }else{
                logger.error(response.message());
            }
            return "";
        } catch (Exception e) {
            logger.error("rc token get error", e);
            return "";
        }
    }

    public static void main (String [] args) throws IOException {
       String x = getToken(1, "sdf", "sdfsf");
       System.out.println(x);
    }


}
