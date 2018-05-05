package com.wiatec.blive.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patrick
 */
public class YearMonthInfo {

    private String start;
    private String end;

    public YearMonthInfo() {
    }


    /**
     * 创建按月划分的实际段(eg: 2017-01-01   ~  2018-02-01)
     * @param year start year
     * @param month start month
     */
    public YearMonthInfo(int year, int month) {
        if(month < 10) {
            this.start = year + "-0" + month + "-01";
        }else{

            this.start = year + "-" + month + "-01";
        }
        if(month == 12){
            this.end = (year + 1) + "-01-01";
        }else{
            int e = month + 1;
            if(e < 10){
                this.end = year + "-0" + e + "-01";
            }else {
                this.end = year + "-" + e + "-01";
            }
        }
    }

    /**
     * 创建以当前月为基准向前倒退1年的时间段(eg: 2017-03-01   ~   2018-02-01)
     * @param year current year
     * @param month current month
     * @param is 与3个参数的构造区分
     */
    public YearMonthInfo(int year, int month, boolean is) {
        month += 1;
        if(month > 12){
            month = 1;
            this.end = (year+1) + "-0" + month + "-01";
        }else {
            if (month < 10) {
                this.end = year + "-0" + month + "-01";
            } else {
                this.end = year + "-" + month + "-01";
            }
        }
        if(month >= 12){
            this.start = year + "-01-01";
        }else{
            int startMonth = month - 12 + 13;
            if(startMonth < 10){
                this.start = (year-1) + "-0" + startMonth + "-01";
            }else {
                this.start = (year-1) + "-" + startMonth + "-01";
            }
        }
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }


    @Override
    public String toString() {
        return "YearOrMonthInfo{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }

    /**
     * 创建当前月的下个月的时间段（eg: 当前月是1月则时间段为 2017-02-01  ~ 2017-03-01）
     * @param year 当前年
     * @param month 当前月
     * @return YearOrMonthInfo
     */
    public static YearMonthInfo createNextMonthly(int year, int month){
        YearMonthInfo yearOrMonthInfo = new YearMonthInfo();
        int startYear = year, endYear = 0, startMonth = 0, endMonth = 0;
        startMonth = month + 1;
        if(startMonth > 12){
            startMonth = 1;
            startYear ++;
        }
        String start = startYear + "-" + startMonth + "-01";
        endMonth = startMonth + 1;
        endYear = startYear;
        if(endMonth > 12){
            endMonth = 1;
            endYear ++;
        }
        String end;
        if(endMonth < 10){
            end = endYear + "-0" + endMonth + "-01";
        }else {
            end = endYear + "-" + endMonth + "-01";
        }
        yearOrMonthInfo.setStart(start);
        yearOrMonthInfo.setEnd(end);
        return yearOrMonthInfo;
    }

    public static void main (String [] args){
        YearMonthInfo yearMonthInfo = YearMonthInfo.createNextMonthly(2016, 11);
        System.out.println(yearMonthInfo);


        YearMonthInfo yearMonthInfo1 = new YearMonthInfo(2018, 5);
        System.out.println(yearMonthInfo1);

        YearMonthInfo yearMonthInfo2 = new YearMonthInfo(2018, 5, true);
        System.out.println(yearMonthInfo2);

    }

}
