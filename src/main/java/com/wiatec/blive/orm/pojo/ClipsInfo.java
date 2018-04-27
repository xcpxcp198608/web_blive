package com.wiatec.blive.orm.pojo;


/**
 * @author patrick
 */
public class ClipsInfo extends BaseInfo {

    private String title;
    private String category;
    private int action;
    private int flag;
    private boolean visible;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "ClipsInfo{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", action=" + action +
                ", flag=" + flag +
                ", visible=" + visible +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
