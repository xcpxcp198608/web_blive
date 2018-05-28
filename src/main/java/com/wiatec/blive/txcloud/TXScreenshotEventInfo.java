package com.wiatec.blive.txcloud;

import java.util.List;

/**
 * @author patrick
 */
public class TXScreenshotEventInfo {

    private String version;
    private String eventType;
    private EventData data;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public EventData getData() {
        return data;
    }

    public void setData(EventData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TXScreenshotEventInfo{" +
                "version='" + version + '\'' +
                ", eventType='" + eventType + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    static class EventData{
        private String vodTaskId;
        private String fileId;
        private int definition;
        private List<PicInfo> picInfo;

        public String getVodTaskId() {
            return vodTaskId;
        }

        public void setVodTaskId(String vodTaskId) {
            this.vodTaskId = vodTaskId;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public int getDefinition() {
            return definition;
        }

        public void setDefinition(int definition) {
            this.definition = definition;
        }

        public List<PicInfo> getPicInfo() {
            return picInfo;
        }

        public void setPicInfo(List<PicInfo> picInfo) {
            this.picInfo = picInfo;
        }

        @Override
        public String toString() {
            return "EventData{" +
                    "vodTaskId='" + vodTaskId + '\'' +
                    ", fileId='" + fileId + '\'' +
                    ", definition='" + definition + '\'' +
                    ", picInfo=" + picInfo +
                    '}';
        }
    }


    static class PicInfo{
        private int status;
        private int timeOffset;
        private String url;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTimeOffset() {
            return timeOffset;
        }

        public void setTimeOffset(int timeOffset) {
            this.timeOffset = timeOffset;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "PicInfo{" +
                    "status=" + status +
                    ", timeOffset=" + timeOffset +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

}
