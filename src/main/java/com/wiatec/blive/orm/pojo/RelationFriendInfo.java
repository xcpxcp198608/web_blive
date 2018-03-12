package com.wiatec.blive.orm.pojo;


/**
 * @author patrick
 */
public class RelationFriendInfo {

    private int id;
    private int userId;
    private int friendId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        return "RelationFriendInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", friendId=" + friendId +
                '}';
    }
}
