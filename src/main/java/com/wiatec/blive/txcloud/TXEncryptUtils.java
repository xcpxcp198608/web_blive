package com.wiatec.blive.txcloud;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author patrick
 */
public class TXEncryptUtils {


    public static byte[] hMacSHA256(String data, String key) {
        try  {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("utf-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return mac.doFinal(data.getBytes("utf-8"));
        } catch (Exception e) {
            return null;
        }
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    public static String base64(byte[] bytes){
        try {
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return "";
        }
    }

    public static void main (String [] args){
        System.out.println(base64(hMacSHA256("GETcvm.api.qcloud.com/v2/index.php?Action=DescribeInstances&InstanceIds.0=ins-09dx96dg&Nonce=11886&Region=ap-guangzhou&SecretId=AKIDz8krbsJ5yKBZQpn74WFkmLPx3gnPhESA&SignatureMethod=HmacSHA256&Timestamp=1465185768", "Gu5t9xGARNpq86cd98joQYCN3Cozk1qA")));
    }




}
