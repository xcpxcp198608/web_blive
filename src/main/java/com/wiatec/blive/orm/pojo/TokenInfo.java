package com.wiatec.blive.orm.pojo;

public class TokenInfo {

    private int id;
    private String token;
    private String createTime;
    private int userId;
    private UserInfo userInfo;

    public TokenInfo() {
    }

    public TokenInfo(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", createTime='" + createTime + '\'' +
                ", userId=" + userId +
                ", userInfo=" + userInfo +
                '}';
    }
}
