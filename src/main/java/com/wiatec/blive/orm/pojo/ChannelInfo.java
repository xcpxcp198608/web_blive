package com.wiatec.blive.orm.pojo;

/**
 * @author patrick
 */
public class ChannelInfo {

    private int id;
    private int userId;
    private String title;
    private String message;
    /**
     * 内容分级：0->G, 1->PG, 2->pg-13, 3->R, 4->NC-17
     */
    private int rating;
    private String url;
    private String rtmpUrl;
    private String rtmpKey;
    private String playUrl;
    private String preview;
    private String category;
    private boolean available;
    /**
     * 1:default live
     */
    private int type;
    private float price;
    private String startTime;

    public ChannelInfo() {
    }

    public ChannelInfo(int userId) {
        this.userId = userId;
    }

    public ChannelInfo(String preview, int userId) {
        this.preview = preview;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "ChannelInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", rating=" + rating +
                ", url='" + url + '\'' +
                ", rtmpUrl='" + rtmpUrl + '\'' +
                ", rtmpKey='" + rtmpKey + '\'' +
                ", playUrl='" + playUrl + '\'' +
                ", preview='" + preview + '\'' +
                ", category='" + category + '\'' +
                ", available=" + available +
                ", type=" + type +
                ", price=" + price +
                ", startTime='" + startTime + '\'' +
                ", userId=" + userId +
                '}';
    }
}
