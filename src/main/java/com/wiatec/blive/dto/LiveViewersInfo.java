package com.wiatec.blive.dto;


/**
 * @author patrick
 */
public class LiveViewersInfo {

    private int userId;
    private String username;
    private int count;
    private int seconds;
    private String duration;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "LiveViewersInfo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", count=" + count +
                ", seconds=" + seconds +
                ", duration='" + duration + '\'' +
                '}';
    }
}
