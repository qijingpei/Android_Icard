package com.example.yasin.thisme.utils;

import com.example.yasin.thisme.model.User;

/**
 * 单例模式实现用户登录的记录，
 * Created by lenovo on 2016/2/22.
 */
public class SingletonUser {

    /*
    单例对象实例
     */
    private static SingletonUser instance = null;
    private static User user;

    //获得实例
    public  static SingletonUser getInstance() {
        if(instance ==null) {
            synchronized (SingletonUser.class) {
                if(instance ==null) {
                    instance = new SingletonUser();
                }
            }
        }
        return instance;
    }
    //设置User的参数
    public static void setUser(User form) {
        user = form;
    }
    //获取User
    public static User getUser() {
        return user;
    }
}
