package com.onlineshoppingmall.penson.addord;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.onlineshoppingmall.MainActivity;
import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;
import com.onlineshoppingmall.penson.addord.add.AddressActivity;
import com.onlineshoppingmall.penson.addord.ord.OrderContent;
import com.onlineshoppingmall.penson.addord.ord.OrderItemFragment;
import com.onlineshoppingmall.remote_entity.Address;
import com.onlineshoppingmall.remote_entity.Good;
import com.onlineshoppingmall.remote_entity.User;
import com.onlineshoppingmall.shoppingcart.cart.GoodContent;
import com.onlineshoppingmall.until.ShareData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {


    private AppCompatTextView address;
    private AppCompatButton edit;
    private AppCompatButton commit;
    private ShareData shareData;

    private String uid;
    private User user;

    private String addressText = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                address.setText(addressText);
            }else {
                address.setText(addressText);
                commit.setEnabled(true);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        AppCompatButton order_return = findViewById(R.id.address_return);
        order_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initOrders();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.order_list, OrderItemFragment.newInstance(1));
        transaction.commit();

        shareData = new ShareData(this);
        uid = shareData.readShared("uid");

        address = findViewById(R.id.show_address);

        edit = findViewById(R.id.order_edit_address);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });
        commit = findViewById(R.id.order_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uid = shareData.readShared("uid");
                final String _address = (String) address.getText();
                for (OrderContent.OrderItem item : OrderContent.ITEMS) {
                    insertOrder(uid, String.valueOf(item.getGid()), String.valueOf(user.getAidP()), String.valueOf(item.getAmount()));
                }
            }
        });
        commit.setEnabled(false);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getUser();
    }

    private void getUser() {
        RequestQueue queue = MyApplication.getHttpQueue();
        String _url = MyApplication.getHost() + "user/selectUserById";
        StringRequest request = new StringRequest(Request.Method.POST, _url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                user = MyApplication.getGson().fromJson(s, User.class);
                if (user.getAidP() == null) {
                    handler.sendEmptyMessage(0);
                } else {
                    getPrimaryAddress();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", uid);
                return map;
            }
        };
        queue.add(request);
    }

    private void getPrimaryAddress() {
        RequestQueue queue = MyApplication.getHttpQueue();
        String _url = MyApplication.getHost() + "address/selectByPrimaryKey";
        StringRequest request = new StringRequest(Request.Method.POST, _url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Address _address = MyApplication.getGson().fromJson(s, Address.class);
                addressText = _address.getAddress();
                handler.sendEmptyMessage(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("aid", String.valueOf(user.getAidP()));
                return map;
            }
        };
        queue.add(request);
    }

    private void initOrders() {
        OrderContent.ITEMS.clear();
        List<GoodContent.GoodItem> list = GoodContent.getITEMS();
        for (GoodContent.GoodItem item : list) {
            if (item.getSelected() != 0) {
                OrderContent.OrderItem orderItem = new OrderContent.OrderItem();
                orderItem.setAmount(item.getAmount());
                Good good = GoodContent.getCache().get(item.getGid());
                orderItem.setGid(good.getId());
                orderItem.setName(good.getName());
                OrderContent.ITEMS.add(orderItem);
            }
        }
    }

    private void insertOrder(final String uid, final String gid, final String aid, final String amount) {
        RequestQueue queue = MyApplication.getHttpQueue();
        String _url = MyApplication.getHost() + "order/insertOrder";
        StringRequest request = new StringRequest(Request.Method.POST, _url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                User user = MyApplication.getGson().fromJson(s, User.class);
                if (user == null) {
                    Toast.makeText(OrderActivity.this, "插入错误!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderActivity.this, "购买成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", uid);
                map.put("gid", gid);
                map.put("aid", aid);
                map.put("amount", amount);
                return map;
            }
        };
        queue.add(request);
    }
}
