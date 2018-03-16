package com.wiatec.blive.orm.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author patrick
 */
public class AuthRegisterUserInfo {

    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private int level;
    private int emailStatus;
    private String mac;
    private String country;
    private String region;
    private String city;
    private String timeZone;
    private String token;
    private String status;
    private String deviceModel;
    private String romVersion;
    private String uiVersion;

    /**
     * 是否从bvision平台注册
     */
    private boolean bvision;

    /**
     * 是否从btv设备注册
     */
    private boolean btv;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activeTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiresTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastOnLineTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    private boolean experience;
    private boolean online;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(int emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getRomVersion() {
        return romVersion;
    }

    public void setRomVersion(String romVersion) {
        this.romVersion = romVersion;
    }

    public String getUiVersion() {
        return uiVersion;
    }

    public void setUiVersion(String uiVersion) {
        this.uiVersion = uiVersion;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
    }

    public Date getLastOnLineTime() {
        return lastOnLineTime;
    }

    public void setLastOnLineTime(Date lastOnLineTime) {
        this.lastOnLineTime = lastOnLineTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public boolean isExperience() {
        return experience;
    }

    public void setExperience(boolean experience) {
        this.experience = experience;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isBvision() {
        return bvision;
    }

    public void setBvision(boolean bvision) {
        this.bvision = bvision;
    }

    public boolean isBtv() {
        return btv;
    }

    public void setBtv(boolean btv) {
        this.btv = btv;
    }

    @Override
    public String toString() {
        return "AuthRegisterUserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", level=" + level +
                ", emailStatus=" + emailStatus +
                ", mac='" + mac + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", token='" + token + '\'' +
                ", status='" + status + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", romVersion='" + romVersion + '\'' +
                ", uiVersion='" + uiVersion + '\'' +
                ", bvision=" + bvision +
                ", btv=" + btv +
                ", activeTime=" + activeTime +
                ", expiresTime=" + expiresTime +
                ", lastOnLineTime=" + lastOnLineTime +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", experience=" + experience +
                ", online=" + online +
                '}';
    }
}
