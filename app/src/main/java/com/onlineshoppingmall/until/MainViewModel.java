package com.onlineshoppingmall.until;

import android.arch.lifecycle.ViewModel;

import com.android.volley.RequestQueue;

public class MainViewModel extends ViewModel {

    private RequestQueue queue;

    public RequestQueue getQueue() {
        return queue;
    }

    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }
}
