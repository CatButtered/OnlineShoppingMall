package com.onlineshoppingmall.communication.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageRecordDao extends SQLiteOpenHelper {

    String create_sql = "create table message(id integer primary key autoincrement,oid integer,date long,content varchar(500),kind integer)";


    public MessageRecordDao(Context context, int version) {
        super(context, "message", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addMessage(String message, int OID, int KIND) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.format(new Date());
        long time = time();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String add_sql = "insert into message(oid,content,date,kind)values(?,?,?,?)";
        sqLiteDatabase.execSQL(add_sql, new Object[]{OID, message, time, KIND});
        sqLiteDatabase.close();
    }


    public static String time(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(time);
    }

    public static long time() {
        return new Date().getTime();
    }

    public List<MessageRecord> query() {

        List<MessageRecord> messageList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String qu_sql = "select a.* from message a where a.date = (select max(date) from message where oid = a.oid ) ORDER by date DESC limit 10";
        Cursor cursor = sqLiteDatabase.rawQuery(qu_sql, null);
        while (cursor.moveToNext()) {
            MessageRecord message = new MessageRecord();
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int oid = cursor.getInt(cursor.getColumnIndex("oid"));
            long date = cursor.getLong(cursor.getColumnIndex("date"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            message.setId(id);
            message.setOid(oid);
            message.setDate(date);
            message.setContent(content);
            message.setKind(kind);
            messageList.add(message);
        }
        sqLiteDatabase.close();
        return messageList;
    }

}
