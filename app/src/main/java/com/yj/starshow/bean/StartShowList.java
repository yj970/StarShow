package com.yj.starshow.bean;

import java.io.Serializable;
import java.util.List;

public class StartShowList implements Serializable{
    List<StarShow> list;

    public List<StarShow> getList() {
        return list;
    }

    public void setList(List<StarShow> list) {
        this.list = list;
    }
}
