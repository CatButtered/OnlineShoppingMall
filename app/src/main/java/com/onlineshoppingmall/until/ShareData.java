package com.onlineshoppingmall.until;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class ShareData {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ShareData(Context context) {
        sharedPreferences = context.getSharedPreferences("myShared", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void writeShared(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String readShared(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void removeShared(String key) {
        editor.remove(key);
        editor.commit();
    }
}
