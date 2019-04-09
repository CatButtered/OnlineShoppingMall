package com.onlineshoppingmall;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {

    private static RequestQueue queue;

    private static String host = "http://192.168.38.27:8080/jxx/";

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getHttpQueue() {
        return queue;
    }

    public static String getHost() {
        return host;
    }
}
