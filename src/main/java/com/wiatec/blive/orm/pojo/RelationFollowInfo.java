package com.wiatec.blive.orm.pojo;


/**
 * @author patrick
 */
public class RelationFollowInfo extends BaseInfo {

    private int userId;
    private int friendId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return "RelationFollowInfo{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
