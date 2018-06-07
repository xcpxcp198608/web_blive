package com.wiatec.blive.dto;

/**
 * @author patrick
 */
public class ChannelCommentInfo {

    public static final int SCOPE_ALL = 1;
    public static final int SCOPE_GROUP = 2;
    public static final int TYPE_LIVE_END = 0;
    public static final int TYPE_LIVE_VIEWERS = 1;
    public static final int TYPE_LIVE_TEXT_COMMENT = 2;
    public static final int TYPE_LIVE_GIFT_COMMENT = 3;

    private int pusherId;
    private int viewerId;
    private String viewerUsername;
    private String viewerIcon;
    /**
     *  1 all， 2 in group
     */
    private int scope;
    /**
     *  0 live end, 1 get live viewer count, 2 text comment, 3 gift comment
     */
    private int type;
    private int viewers;
    private int giftIndex;
    private String comment;
    private String createTime;

    public int getPusherId() {
        return pusherId;
    }

    public void setPusherId(int pusherId) {
        this.pusherId = pusherId;
    }

    public int getViewerId() {
        return viewerId;
    }

    public void setViewerId(int viewerId) {
        this.viewerId = viewerId;
    }

    public String getViewerUsername() {
        return viewerUsername;
    }

    public void setViewerUsername(String viewerUsername) {
        this.viewerUsername = viewerUsername;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public int getGiftIndex() {
        return giftIndex;
    }

    public void setGiftIndex(int giftIndex) {
        this.giftIndex = giftIndex;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getViewerIcon() {
        return viewerIcon;
    }

    public void setViewerIcon(String viewerIcon) {
        this.viewerIcon = viewerIcon;
    }

    @Override
    public String toString() {
        return "ChannelCommentInfo{" +
                "pusherId=" + pusherId +
                ", viewerId=" + viewerId +
                ", viewerUsername='" + viewerUsername + '\'' +
                ", viewerIcon='" + viewerIcon + '\'' +
                ", scope=" + scope +
                ", type=" + type +
                ", viewers=" + viewers +
                ", giftIndex=" + giftIndex +
                ", comment='" + comment + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
