package com.wiatec.blive.orm.pojo;

import org.kohsuke.rngom.parse.host.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author patrick
 */
public class ImageAdInfo extends BaseInfo {

    private int position;
    private String name;
    private String url;
    private String link;
    private int action;
    private int flag;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "ImageAdInfo{" +
                "position=" + position +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", link='" + link + '\'' +
                ", action=" + action +
                ", flag=" + flag +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
