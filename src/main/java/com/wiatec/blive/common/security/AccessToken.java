package com.wiatec.blive.common.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ws.security.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author patrick
 */
public class AccessToken {

    private final Logger logger = LoggerFactory.getLogger(AccessToken.class);

    private static final String SALT = "&23hsd234h7dskj324%2b34h23^#@*&324%2b34hsd234h7d23JBHY@W@DSD^%$DSDSDdskj324%2b34";

    public static String encrypt(String value){
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        String time = System.currentTimeMillis() + "";
        System.out.println(time);
        return DigestUtils.md5Hex(value + uuid + time + SALT);
    }

    public static boolean decrypt(String accessToken, String uuid, String value, String time){
        String s = DigestUtils.md5Hex(value + uuid + time + SALT);
        return StringUtils.equals(s, accessToken);
    }

    public static void main (String [] args){
        String token = encrypt("41");
        System.out.println(token);

        System.out.println(decrypt("e34dc94b20e06717fe2ff69e508118fd", "A491C896-6597-4368-8DF0-D1BD5F277F6D",
                "patrick", "1523122123"));

        String s = DigestUtils.md5Hex(41 + "2FDF3926-AA95-4A1A-9DA5-2AA0EB71572C" + "1525398495000" + SALT);
        System.out.println(s);

        boolean b = decrypt("d1559e59cc7f91af766b09282d30ba13",
                "2FDF3926-AA95-4A1A-9DA5-2AA0EB71572C",
                "41", "1525397941000");
        System.out.println(b);
    }


}
