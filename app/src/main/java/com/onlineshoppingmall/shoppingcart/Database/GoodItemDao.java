package com.onlineshoppingmall.shoppingcart.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.onlineshoppingmall.shoppingcart.cart.GoodContent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoodItemDao extends SQLiteOpenHelper {

    String create_sql = "create table goods(id integer primary key autoincrement,gid integer,selected integer,amount integer)";


    public GoodItemDao(Context context, int version) {
        super(context, "goods", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addGood(GoodContent.GoodItem item) throws ParseException {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String add_sql = "insert into goods(gid,selected,amount)values(?,?,?)";
        sqLiteDatabase.execSQL(add_sql, new Object[]{item.getGid(), item.getSelected(), item.getAmount()});
        sqLiteDatabase.close();
    }

    public void updateAmount(int id, int amount) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String add_sql = "update goods set amount=" + amount + " where id=" + id;
        sqLiteDatabase.execSQL(add_sql);
        sqLiteDatabase.close();
    }

    public void updateSelected(int id, int selected) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String add_sql = "update goods set selected=" + selected + " where id=" + id;
        sqLiteDatabase.execSQL(add_sql);
        sqLiteDatabase.close();
    }

    public List<GoodContent.GoodItem> query() {
        List<GoodContent.GoodItem> goodItems = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String qu_sql = "select * from goods";
        Cursor cursor = sqLiteDatabase.rawQuery(qu_sql, null);
        while (cursor.moveToNext()) {
            GoodContent.GoodItem item = new GoodContent.GoodItem();
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int gid = cursor.getInt(cursor.getColumnIndex("gid"));
            int selected = cursor.getInt(cursor.getColumnIndex("selected"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            item.setId(id);
            item.setGid(gid);
            item.setSelected(selected);
            item.setAmount(amount);
            goodItems.add(item);
        }
        sqLiteDatabase.close();
        return goodItems;
    }

}
