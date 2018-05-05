package com.wiatec.blive.orm.pojo;



/**
 * @author patrick
 */
public class LiveViewInfo extends BaseInfo {


    private int playerId;
    private int viewerId;

    public LiveViewInfo() {
    }

    public LiveViewInfo(int playerId, int viewerId) {
        this.playerId = playerId;
        this.viewerId = viewerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getViewerId() {
        return viewerId;
    }

    public void setViewerId(int viewerId) {
        this.viewerId = viewerId;
    }

    @Override
    public String toString() {
        return "LiveViewInfo{" +
                "playerId=" + playerId +
                ", viewerId=" + viewerId +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
