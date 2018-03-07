package com.wiatec.blive.rtmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patrick
 */

public class RtmpInfo{
    private String username;
    private String push_url = "";
    private String push_key = "";
    private String push_full_url = "";
    private String play_url = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPush_url() {
        return push_url;
    }

    public void setPush_url(String push_url) {
        this.push_url = push_url;
    }

    public String getPush_key() {
        return push_key;
    }

    public void setPush_key(String push_key) {
        this.push_key = push_key;
    }

    public String getPush_full_url() {
        return push_full_url;
    }

    public void setPush_full_url(String push_full_url) {
        this.push_full_url = push_full_url;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    @Override
    public String toString() {
        return "RtmpInfo{" +
                "username='" + username + '\'' +
                ", push_url='" + push_url + '\'' +
                ", push_key='" + push_key + '\'' +
                ", push_full_url='" + push_full_url + '\'' +
                ", play_url='" + play_url + '\'' +
                '}';
    }
}

