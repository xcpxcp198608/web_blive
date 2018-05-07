package com.wiatec.blive.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author patrick
 */
public class TimeUtil {

    public static long getUnixFromStr(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            return 0;
        }
        return date.getTime();
    }

    public static long getUnix10(){
        return System.currentTimeMillis() / 1000;
    }

    public static String getStrTime(){
        return getStrTime(System.currentTimeMillis());
    }

    public static String getStrTime(long time){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(new Date(time));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getStrDate(){
        return getStrDate(System.currentTimeMillis());
    }

    public static String getStrDate(long time){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(new Date(time));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getExpiresTime(String activateTime, int expires){
        Date date = new Date(TimeUtil.getUnixFromStr(activateTime));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, expires);
        date = calendar.getTime();
        return getStrTime(date.getTime());
    }

    public static String getExpiresDate(String activateTime, int expires){
        Date date = new Date(TimeUtil.getUnixFromStr(activateTime));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, expires);
        date = calendar.getTime();
        return getStrDate(date.getTime());
    }

    public static boolean isOutExpires(String activateTime, int expires){
        Date date = new Date(TimeUtil.getUnixFromStr(activateTime));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, expires);
        date = calendar.getTime();
        return System.currentTimeMillis() > date.getTime();
    }

    public static boolean isOutExpires(String expiresTime){
        return System.currentTimeMillis() > TimeUtil.getUnixFromStr(expiresTime);
    }


    public static String getMediaTime(long milliseconds){
        int dx ;
        int dm ;
        int x = (int) (milliseconds /1000);
        if(x > 60){
            dx = x % 60;
        }else{
            dx = x;
        }
        int minute = x / 60;
        if(minute > 60){
            dm = minute % 60;
        }else{
            dm = minute;
        }
        int hour = minute / 60;
        return ""+ hour + ":"+ dm +":"+ dx;
    }

    public static String getMediaTime(int seconds){
        int dx ;
        int dm ;
        if(seconds > 60){
            dx = seconds % 60;
        }else{
            dx = seconds;
        }
        int minute = seconds / 60;
        if(minute > 60){
            dm = minute % 60;
        }else{
            dm = minute;
        }
        int hour = minute / 60;
        String m = ""+dm;
        if(dm < 10){
            m = "0"+dm;
        }
        String x = ""+dx;
        if(dx < 10){
            x = "0"+dx;
        }
        return ""+ hour + ":"+ m +":"+ x;
    }

    public static void main (String [] args){
        System.out.println(getMediaTime(21665));
    }


}
