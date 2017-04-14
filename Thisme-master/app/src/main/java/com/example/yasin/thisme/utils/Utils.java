package com.example.yasin.thisme.utils;

import java.util.HashMap;

/**
 * Created by Yasin on 2016/2/16.
 */
public class Utils {

    static public String[] HashMapString2StringArray(String data){

        String[] datas;
        datas = data.split("\\{|,|=|\\}");
        return datas;
    }

}
