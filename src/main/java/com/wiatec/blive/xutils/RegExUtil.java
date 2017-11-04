package com.wiatec.blive.xutils;

import java.util.regex.Pattern;

/**
 * Created by xuchengpeng on 12/06/2017.
 */
public class RegExUtil {

    public static boolean matchEmail(String email){
        Pattern pattern = Pattern.compile("[d]{1,9}@");
        return pattern.matcher(email).matches();
    }
}
