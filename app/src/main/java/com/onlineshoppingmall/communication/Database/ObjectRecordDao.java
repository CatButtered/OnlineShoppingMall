package com.onlineshoppingmall.communication.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class ObjectRecordDao extends SQLiteOpenHelper {

    String create_sql = "create table object(id integer primary key autoincrement,name varchar(50) not null unique)";

    public ObjectRecordDao(@Nullable Context context, int version) {
        super(context, "object", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addObject(String name) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String add_sql = "insert into object(name) values(?)";
        sqLiteDatabase.execSQL(add_sql, new Object[]{name});
        sqLiteDatabase.close();
    }

    public ObjectRecord query(int id) {

        List<ObjectRecord> objectRecordList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String qu_sql = "select * from object where id=" + id;
        Cursor cursor = sqLiteDatabase.rawQuery(qu_sql, null);
        while (cursor.moveToNext()) {
            ObjectRecord objectRecord = new ObjectRecord();
            int _id = cursor.getInt(cursor.getColumnIndex("id"));
            String _name = cursor.getString(cursor.getColumnIndex("name"));
            objectRecord.setId(_id);
            objectRecord.setName(_name);
            objectRecordList.add(objectRecord);
        }
        sqLiteDatabase.close();
        if (objectRecordList.size() == 1) {
            return objectRecordList.get(0);
        } else {
            throw new RuntimeException("can't find Record");
        }
    }
}
