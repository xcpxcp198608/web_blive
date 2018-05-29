package com.wiatec.blive.orm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wiatec.blive.txcloud.TXEventInfo;

import java.util.Date;

/**
 * @author patrick
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private long duration;
    private long startTime;
    private long endTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date liveTime;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    public String getRtmpKey() {
        return rtmpKey;
    }

    public void setRtmpKey(String rtmpKey) {
        this.rtmpKey = rtmpKey;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Date getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(Date liveTime) {
        this.liveTime = liveTime;
    }

    @Override
    public String toString() {
        return "ChannelInfo{" +
                "userId=" + userId +
                ", available=" + available +
                ", username='" + username + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", level=" + level +
                ", rating=" + rating +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", streamId='" + streamId + '\'' +
                ", videoId='" + videoId + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                ", playUrl='" + playUrl + '\'' +
                ", rtmpUrl='" + rtmpUrl + '\'' +
                ", rtmpKey='" + rtmpKey + '\'' +
                ", preview='" + preview + '\'' +
                ", link='" + link + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileFormat='" + fileFormat + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", liveTime=" + liveTime +
                '}';
    }


    public static ChannelInfo createFrom(TXEventInfo txEventInfo){
        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setStreamId(txEventInfo.getStream_id());
        channelInfo.setVideoId(txEventInfo.getVideo_id());
        channelInfo.setPlayUrl(txEventInfo.getVideo_url());
        channelInfo.setFileId(txEventInfo.getFile_id());
        channelInfo.setFileFormat(txEventInfo.getFile_format());
        channelInfo.setFileSize(txEventInfo.getFile_size());
        channelInfo.setStartTime(txEventInfo.getStart_time());
        channelInfo.setEndTime(txEventInfo.getEnd_time());
        channelInfo.setDuration(txEventInfo.getDuration());
        return channelInfo;
    }
}
