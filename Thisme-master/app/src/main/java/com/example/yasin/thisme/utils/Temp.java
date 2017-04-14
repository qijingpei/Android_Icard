package com.example.yasin.thisme.utils;

/**
 * Created by Yasin on 2016/2/16.
 * 用于暂存名片信息的item中两项
 */
public class Temp {
    String name;
    String content;

    public Temp(String name,String content){
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
