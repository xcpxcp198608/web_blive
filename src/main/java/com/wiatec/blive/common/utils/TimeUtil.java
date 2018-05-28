package com.wiatec.blive.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static Date getExpiresTime(Date startDate, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
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

    /**
     * 获取当前时间的字符串格式
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String toStr(){
        return toStr(new Date(System.currentTimeMillis()));
    }

    /**
     * 获取指定unix毫秒数对应的时间字符串
     * @param milliseconds  milliseconds
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String toStr(long milliseconds){
        return toStr(new Date(milliseconds));
    }

    /**
     * 获取指定date对应的时间字符串
     * @param date date
     * @return milliseconds
     */
    public static String toStr(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date);
    }

    /**
     * 判断指定日期是否已过期
     * @param expiresDate expiresDate
     * @return boolean
     */
    public static boolean isExpires(Date expiresDate){
        return expiresDate.before(new Date());
    }

    /**
     * 获取从现在起指定month后的结束date
     * @param days days
     * @return end date
     */
    public static Date getExpiresByDays(int days){
        return getExpiresByDays(new Date(), days);
    }

    /**
     * 获取指定起始date在指定month后的结束date
     * @param date start date
     * @param days days
     * @return end date
     */
    public static Date getExpiresByDays(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    /**
     * 获取从现在起指定month后的结束date
     * @param month month
     * @return end date
     */
    public static Date getExpires(int month){
        return getExpires(new Date(), month);
    }

    /**
     * 获取指定起始date在指定month后的结束date
     * @param date start date
     * @param month month
     * @return end date
     */
    public static Date getExpires(Date date, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 获取字符串格式时间对应的unix时间戳
     * @param date 字符串时间 yyyy-MM-dd or yyyy-MM-dd HH:mm:ss
     * @return unix时间戳
     */
    public static long toUnix(String date){
        if(TextUtil.isEmpty(date)){
            return 0;
        }
        date = date.trim();
        try {
            Date d;
            if(date.length() == 10){
                d = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date);
            }else{
                d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(date);
            }
            return toUnix(d);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取指定date对应的unix时间戳
     * @param date date
     * @return unix时间戳
     */
    public static long toUnix(Date date){
        return date.getTime();
    }

    /**
     * 计算2个日期间相差的天数
     * @param d1
     * @param d2
     * @return
     */
    public static int diffDays(Date d1, Date d2){
        long diff = d1.getTime() - d2.getTime();
        int days = (int) (diff / (24 * 3600 * 1000));
        if(days == 0){
            days = 1;
        }
        return days;
    }

    public static void main (String [] args){
        System.out.println(diffDays(new Date(1520313102000L), new Date()));
    }



}
