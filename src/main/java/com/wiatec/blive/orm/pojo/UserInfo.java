package com.wiatec.blive.orm.pojo;

/**
 * @author patrick
 */
public class UserInfo {

    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String icon;
    private String profile;
    private String registerTime;
    private boolean status;
    private boolean publisher;

    private ChannelInfo channelInfo;

    public UserInfo() { }

    public UserInfo(String username) {
        this.username = username;
    }

    public UserInfo(int id) {
        this.id = id;
    }

    public UserInfo(int id, String icon) {
        this.id = id;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isPublisher() {
        return publisher;
    }

    public void setPublisher(boolean publisher) {
        this.publisher = publisher;
    }

    public ChannelInfo getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(ChannelInfo channelInfo) {
        this.channelInfo = channelInfo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", icon='" + icon + '\'' +
                ", profile='" + profile + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", status=" + status +
                ", publisher=" + publisher +
                ", channelInfo=" + channelInfo +
                '}';
    }
}
