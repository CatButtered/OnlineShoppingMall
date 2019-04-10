package com.onlineshoppingmall;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MyApplication extends Application {

    private static RequestQueue queue;

    private static Gson gson;

    private static String host = "http://192.168.43.24:8080/jxx/";

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());
        gson = new Gson();
    }

    public static RequestQueue getHttpQueue() {
        return queue;
    }

    public static Gson getGson(){
        return gson;
    }

    public static String getHost() {
        return host;
    }
}
