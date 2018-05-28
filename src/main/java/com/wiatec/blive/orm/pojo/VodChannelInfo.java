package com.wiatec.blive.orm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wiatec.blive.txcloud.TXEventInfo;

import java.util.Date;

/**
 * @author patrick
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VodChannelInfo extends BaseInfo {

    private int userId;
    private boolean available;
    /**
     * 内容分级：0->G, 1->PG, 2->pg-13, 3->R, 4->NC-17
     */
    private int rating;
    private float price;

    private String username;
    private String userIcon;
    private int level;

    private String title;
    private String message;
    private String streamId;
    private String videoId;
    private long duration;
    private String playUrl;
    private String fileId;
    private String fileFormat;
    private String fileSize;
    private String preview;
    private String link;
    private String category;
    /**
     * 1:default live
     */
    private int type;
    private long startTime;
    private long endTime;


    public VodChannelInfo() {
    }

    public VodChannelInfo(String videoId, String preview) {
        this.videoId = videoId;
        this.preview = preview;
    }

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "VodChannelInfo{" +
                "userId=" + userId +
                ", available=" + available +
                ", price=" + price +
                ", username='" + username + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", level=" + level +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", rating=" + rating +
                ", streamId='" + streamId + '\'' +
                ", videoId='" + videoId + '\'' +
                ", duration=" + duration +
                ", playUrl='" + playUrl + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileFormat='" + fileFormat + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", preview='" + preview + '\'' +
                ", link='" + link + '\'' +
                ", category='" + category + '\'' +
                ", type=" + type +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }

    public static VodChannelInfo createFrom(TXEventInfo txEventInfo){
        VodChannelInfo vodChannelInfo = new VodChannelInfo();
        vodChannelInfo.setStreamId(txEventInfo.getStream_id());
        vodChannelInfo.setVideoId(txEventInfo.getVideo_id());
        vodChannelInfo.setPlayUrl(txEventInfo.getVideo_url());
        vodChannelInfo.setFileId(txEventInfo.getFile_id());
        vodChannelInfo.setFileFormat(txEventInfo.getFile_format());
        vodChannelInfo.setFileSize(txEventInfo.getFile_size());
        vodChannelInfo.setStartTime(txEventInfo.getStart_time());
        vodChannelInfo.setEndTime(txEventInfo.getEnd_time());
        vodChannelInfo.setDuration(txEventInfo.getDuration());
        return vodChannelInfo;
    }
}
