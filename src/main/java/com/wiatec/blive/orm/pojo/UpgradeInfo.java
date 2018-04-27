package com.wiatec.blive.orm.pojo;

/**
 * Created by xuchengpeng on 23/06/2017.
 */
public class UpgradeInfo extends BaseInfo {

    private String name;
    private String url;
    private String version;
    private int code;
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "UpgradeInfo{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                ", code=" + code +
                ", info='" + info + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
