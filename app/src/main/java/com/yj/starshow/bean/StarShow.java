package com.yj.starshow.bean;

import java.io.Serializable;
import java.util.Date;

public class StarShow implements Serializable{
    String id;// 唯一值
    int week;// 星期几播出
    String name;// 节目名
    Date time;// 播出时间
    String type;// 类型
    String platform; // 播出平台

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
