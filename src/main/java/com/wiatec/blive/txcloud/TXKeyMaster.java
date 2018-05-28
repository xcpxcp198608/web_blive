package com.wiatec.blive.txcloud;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author patrick
 */
public class TXKeyMaster {

    private static final String SECRET_ID = "AKIDcDj5GHhmJpESbZsyOdhZWak2cdUcBTOC";
    private static final String SECRET_KEY = "mO5e2FfMygSK33z86tkw0WuZMyPBbhfT";
    private static final String URL = "POSTvod.api.qcloud.com/v2/index.php?";

    private static final String URL_SCREENSHOT = "https://vod.api.qcloud.com/v2/index.php?";

    public static String getSignatureUrl(String params){
        String data = URL + params;
        String se = TXEncryptUtils.base64(TXEncryptUtils.hMacSHA256(data, SECRET_KEY));
        try {
            se = URLEncoder.encode(se, "utf-8");
        } catch (UnsupportedEncodingException e) {
            se = "";
        }
        String newUrl = new StringBuilder().append(URL_SCREENSHOT)
                .append(params)
                .append("&")
                .append("Signature=")
                .append(se)
                .toString();
        return newUrl;
    }

    public static void main (String [] args){
        String params = new StringBuilder()
                .append("Action=CreateSnapshotByTimeOffset").append("&")
                .append("definition=10").append("&")
                .append("fileId=").append("210046043_c3a6ad996a4a46fa90b6f433ec62fcbd").append("&")
                .append("Nonce=").append(12321).append("&")
                .append("Region=szjr").append("&")
                .append("SecretId=AKIDcDj5GHhmJpESbZsyOdhZWak2cdUcBTOC").append("&")
                .append("Timestamp=").append(System.currentTimeMillis() / 1000).append("&")
                .append("timeOffset=100")
                .toString();
        System.out.println(TXKeyMaster.getSignatureUrl(params));
    }

}
