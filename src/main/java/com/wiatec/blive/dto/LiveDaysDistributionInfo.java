package com.wiatec.blive.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author patrick
 */
public class LiveDaysDistributionInfo {

    private String day;
    private int count;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "LiveDaysDistributionInfo{" +
                "day=" + day +
                ", count=" + count +
                '}';
    }
}
