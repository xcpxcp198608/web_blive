package com.wiatec.blive.common.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patrick
 */
public class MD5Utils {

    private final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

    public static String create16(String s1){
        return DigestUtils.md5Hex(s1).substring(8,24);
    }

    public static String create32(String s1){
        return DigestUtils.md5Hex(s1);
    }

    public static String create64(String s1){
        return DigestUtils.md5Hex(s1) +
                DigestUtils.md5Hex(s1);
    }

}
