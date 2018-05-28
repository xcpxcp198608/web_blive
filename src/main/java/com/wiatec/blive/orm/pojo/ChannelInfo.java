package com.wiatec.blive.orm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author patrick
 */
public class ChannelInfo extends BaseInfo {

    private int userId;
    private boolean available;
    private String username;
    private String userIcon;
    private int level;
    /**
     * 内容分级：0->G, 1->PG, 2->pg-13, 3->R, 4->NC-17
     */
    private int rating;
    private float price;
    private String title;
    private String message;
    /**
     * 1: live
     * 2: vod
     */
    private int type;
    private String category;
    private String streamId;
    private String videoId;
    private String pushUrl;
    private String playUrl;
    private String rtmpUrl;
    private String rtmpKey;
    private String preview;
    private String link;
    private String fileId;
    private String fileFormat;
    private String fileSize;
    private int duration;
    private int startTime;
    private int endTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date liveTime;

}
