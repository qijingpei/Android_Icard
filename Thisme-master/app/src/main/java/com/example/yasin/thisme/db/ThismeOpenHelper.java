package com.example.yasin.thisme.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yasin on 2016/1/31.
 */
public class ThismeOpenHelper extends SQLiteOpenHelper{

    /*
    * MyCard表建表语句
    * */
    public static final String CREATE_CARD = "create table Card ("+
            "id integer primary key autoincrement,"+
            "shuxing text,"+
            "name text,"+
            "phonenum text,"+
            "email text," +
            "qq text," +
            "weixin text," +
            "miaoshu text,"+
            "more text)";
    public ThismeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CARD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
