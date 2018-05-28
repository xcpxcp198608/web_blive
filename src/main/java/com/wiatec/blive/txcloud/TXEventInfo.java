package com.wiatec.blive.txcloud;

/**
 * @author patrick
 */
public class TXEventInfo {

    private int event_type;
    private String t;
    private String sign;
    private String stream_id;
    private String channel_id;

    private int errcode;
    private int event_time;
    private String app;
    private String appname;
    private String sequence;
    private String node;
    private String user_ip;
    private String errmsg;
    private String stream_param;
    private String push_duration;

    private long start_time;
    private long end_time;
    private int vod2Flag;
    private int duration;
    private String video_id;
    private String video_url;
    private String file_size;
    private String file_id;
    private String file_format;
    private String record_file_id;

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStream_id() {
        return stream_id;
    }

    public void setStream_id(String stream_id) {
        this.stream_id = stream_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public int getEvent_time() {
        return event_time;
    }

    public void setEvent_time(int event_time) {
        this.event_time = event_time;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getStream_param() {
        return stream_param;
    }

    public void setStream_param(String stream_param) {
        this.stream_param = stream_param;
    }

    public String getPush_duration() {
        return push_duration;
    }

    public void setPush_duration(String push_duration) {
        this.push_duration = push_duration;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getVod2Flag() {
        return vod2Flag;
    }

    public void setVod2Flag(int vod2Flag) {
        this.vod2Flag = vod2Flag;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getFile_format() {
        return file_format;
    }

    public void setFile_format(String file_format) {
        this.file_format = file_format;
    }

    public String getRecord_file_id() {
        return record_file_id;
    }

    public void setRecord_file_id(String record_file_id) {
        this.record_file_id = record_file_id;
    }

    @Override
    public String toString() {
        return "TXEventInfo{" +
                "event_type=" + event_type +
                ", t='" + t + '\'' +
                ", sign='" + sign + '\'' +
                ", stream_id='" + stream_id + '\'' +
                ", channel_id='" + channel_id + '\'' +
                ", errcode=" + errcode +
                ", event_time=" + event_time +
                ", app='" + app + '\'' +
                ", appname='" + appname + '\'' +
                ", sequence='" + sequence + '\'' +
                ", node='" + node + '\'' +
                ", user_ip='" + user_ip + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", stream_param='" + stream_param + '\'' +
                ", push_duration='" + push_duration + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", vod2Flag=" + vod2Flag +
                ", duration=" + duration +
                ", video_id='" + video_id + '\'' +
                ", video_url='" + video_url + '\'' +
                ", file_size='" + file_size + '\'' +
                ", file_id='" + file_id + '\'' +
                ", file_format='" + file_format + '\'' +
                ", record_file_id='" + record_file_id + '\'' +
                '}';
    }
}
