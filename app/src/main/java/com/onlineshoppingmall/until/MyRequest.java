package com.onlineshoppingmall.until;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;

import java.util.Map;

public class MyRequest {

    public interface OnGet {
        void onSuccess(String res);

        void onFailure();
    }

    public interface OnPost {
        void onSuccess(String res);

        void onFailure();

        Map<String, String> setParam();
    }

    public static void setBitmap(Context context, ImageView view, String url) {
        Glide.with(context).load(url).placeholder(R.drawable.msg_loading)
                .error(R.drawable.msg_failure).into(view);
    }

    public static void setGoodImg(Context context, ImageView view, int id) {
        String url = MyApplication.getHost() + "good/image?id=" + id;
        Glide.with(context).load(url)
                .placeholder(R.drawable.msg_loading)
                .error(R.drawable.msg_failure).into(view);
    }

    public static void setBitmap(Context context, ImageView view, Integer resourceId) {
        Glide.with(context).load(resourceId)
                .placeholder(R.drawable.msg_loading)
                .error(R.drawable.msg_failure).into(view);
    }

    private static void volley_setBitmap(Context context, ImageView view, int id) {
        RequestQueue queue = MyApplication.getHttpQueue();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(view,
                R.drawable.msg_loading, R.drawable.msg_failure);
        ImageLoader loader = new ImageLoader(queue, new BitmapCache());
        loader.get(MyApplication.getHost() + "img/goods/" + id + ".jpg",
                listener, view.getWidth(), view.getHeight());
    }

    public static void volley_Get(final OnGet onGet, String url) {
        RequestQueue queue = MyApplication.getHttpQueue();
        String _url = MyApplication.getHost() + url;
        StringRequest request = new StringRequest(Request.Method.GET, _url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                onGet.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                onGet.onFailure();
            }
        });
        queue.add(request);
    }

    public static void volley_Post(final OnPost onPost, String url) {
        RequestQueue queue = MyApplication.getHttpQueue();
        String _url = MyApplication.getHost() + url;
        StringRequest request = new StringRequest(Request.Method.POST, _url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                onPost.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                onPost.onFailure();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return onPost.setParam();
            }
        };
        queue.add(request);
    }

}
