package com.example.yasin.thisme.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yasin.thisme.db.ThismeOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasin on 2016/1/31.
 */
public class ThismeDB {
    /*
    数据库名
    * */
    public static final String DB_NAME = "thisme_db";
    public static final int VERSION = 1;
    private static ThismeDB thismeDB;
    private SQLiteDatabase db;
    /*
    * 构造方法私有化
    * */
    private ThismeDB(Context context){
        ThismeOpenHelper dbHelper = new ThismeOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }
    /*
    * 获取ThismeDB实例
    * */
    public synchronized static ThismeDB getInsstance(Context context){
        if(thismeDB == null){
            thismeDB = new ThismeDB(context);
        }
        return thismeDB;
    }
    /*
    * 将card存储到数据库
    * */
    public void saveCard(Card card){
        if(card!=null){
            ContentValues values = new ContentValues();
            values.put("shuxing",card.getShuxing());
            values.put("name",card.getName());
            values.put("phonenum",card.getPhoneNum());
            values.put("email",card.getEmail());
            values.put("qq",card.getQQ());
            values.put("weixin",card.getWeixin());
            values.put("miaoshu",card.getMiaosu());
            values.put("more",card.getMore());
            db.insert("Card",null,values);
        }
    }
    public void deleteCard(int cardId){
        final String DELETECARD = "delete from card where id="+String.valueOf(cardId);
        db.execSQL(DELETECARD);
    }
    public List<Card> loadMyCard(){
        List<Card> list = new ArrayList<Card>();
        String LOADMYCARD = "select * from Card where shuxing=?";
        Cursor cursor = db.rawQuery(LOADMYCARD,new String[]{"1"});
        if(cursor.moveToFirst()){
            do{
                Card card = new Card();
                card.setCardId(cursor.getInt(cursor.getColumnIndex("id")));
                card.setName(cursor.getString(cursor.getColumnIndex("name")));
                card.setPhoneNum(cursor.getString(cursor.getColumnIndex("phonenum")));
                card.setQQ(cursor.getString(cursor.getColumnIndex("qq")));
                card.setWeixin(cursor.getString(cursor.getColumnIndex("weixin")));
                card.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                card.setMiaosu(cursor.getString(cursor.getColumnIndex("miaoshu")));
                card.setMore(cursor.getString(cursor.getColumnIndex("more")));
                card.setShuxing("1");
                list.add(card);
            }while(cursor.moveToNext());
        }
        return list;
    }
    public List<Card> loadFriendCard(){
        Log.e("yasin","load");
        List<Card> list = new ArrayList<Card>();
        final String LOADFCARD = "select * from Card "+
                "where shuxing=?";
        Cursor cursor = db.rawQuery(LOADFCARD,new String[]{"2"});
        if(cursor.moveToFirst()){
            do{
                Card card = new Card();
                card.setCardId(cursor.getInt(cursor.getColumnIndex("id")));
                card.setName(cursor.getString(cursor.getColumnIndex("name")));
                card.setPhoneNum(cursor.getString(cursor.getColumnIndex("phonenum")));
                card.setQQ(cursor.getString(cursor.getColumnIndex("qq")));
                card.setWeixin(cursor.getString(cursor.getColumnIndex("weixin")));
                card.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                card.setShuxing("2");
                card.setMiaosu(cursor.getString(cursor.getColumnIndex("miaoshu")));
                card.setMore(cursor.getString(cursor.getColumnIndex("more")));
                list.add(card);
            }while(cursor.moveToNext());
        }
        return list;
    }
    public void xiugaiCard(Card card){
            ContentValues values = new ContentValues();
            values.put("shuxing", card.getShuxing());
            values.put("name", card.getName());
            Log.e("xiugai", card.getName());
            values.put("phonenum",card.getPhoneNum());
            values.put("email",card.getEmail());
            values.put("qq",card.getQQ());
            values.put("weixin",card.getWeixin());
            values.put("miaoshu", card.getMiaosu());
            values.put("more", card.getMore());
            String[] temp = {String.valueOf(card.getCardId())};
            db.update("Card",values,"id=?",temp);
//        String YJ = "UPDATE Card " +
//                "SET name = '"+card.getName()+"' WHERE id="+card.getCardId();
//        Log.e("xiugai",YJ);
//        db.execSQL(YJ);
    }
}
