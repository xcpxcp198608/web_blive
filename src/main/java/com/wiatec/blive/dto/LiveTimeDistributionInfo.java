package com.wiatec.blive.dto;


/**
 * @author patrick
 */
public class LiveTimeDistributionInfo {

    private int hour;
    private int count;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "LiveTimeDistributionInfo{" +
                "hour=" + hour +
                ", count=" + count +
                '}';
    }
}
