package com.wiatec.blive.common.utils;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * token util
 * @author patrick
 */
public class TokenUtil {

    public static String create16(String s1){
        return DigestUtils.md5Hex(s1 + System.currentTimeMillis()).substring(8,24);
    }

    public static String create32(String s1){
        return DigestUtils.md5Hex(s1 + System.currentTimeMillis());
    }

    public static String create64(String s1){
        return DigestUtils.md5Hex(s1 + System.currentTimeMillis()) +
                DigestUtils.md5Hex(s1 + System.currentTimeMillis() + System.currentTimeMillis()) ;
    }

    public static boolean macValidate(String token){
        String tokenAfterDecrypt = AESUtil.decrypt(token,AESUtil.KEY);
        return tokenAfterDecrypt.startsWith("5c:41:e7");
    }

    public static String createKey(int i){
        return TokenUtil.create16("www.wiatec.com");
    }


    public static void main (String [] args){
        String t = create32("patrick");
        String t2 = create64("patrick");
        String t3 = create16("patrick");
        System.out.println(t);
        System.out.println(t2);
        System.out.println(t3);

    }

}
