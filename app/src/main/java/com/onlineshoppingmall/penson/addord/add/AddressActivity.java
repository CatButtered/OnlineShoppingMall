package com.onlineshoppingmall.penson.addord.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;
import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;
import com.onlineshoppingmall.remote_entity.Address;
import com.onlineshoppingmall.remote_entity.User;
import com.onlineshoppingmall.until.MyRequest;
import com.onlineshoppingmall.until.ShareData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AddressActivity extends AppCompatActivity implements MyRequest.OnPost {

    private AppCompatButton address_return;
    private AppCompatButton address_add;

    private ShareData data;
    private AddressFragment fragment;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        address_return = findViewById(R.id.address_return);
        address_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        address_add = findViewById(R.id.address_add);
        address_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, AddressInfoActivity.class);
                startActivity(intent);
            }
        });

        data = new ShareData(this);
        uid = data.readShared("uid");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        AddressContent.ITEMS.clear();
        address_add.setEnabled(false);
        MyRequest.volley_Post(this, "user/selectUserById");
    }

    @Override
    public void onSuccess(String res) {
        final User user = MyApplication.getGson().fromJson(res, User.class);
        if (user == null) {
            Toast.makeText(AddressActivity.this, "网络错误!", Toast.LENGTH_SHORT).show();
        } else {
            Type setType = new TypeToken<Set<Integer>>() {
            }.getType();
            Set<Integer> aids = MyApplication.getGson().fromJson(user.getAids(), setType);
            if (aids==null||aids.size()==0){
                address_add.setEnabled(true);
            }else {
                for (final Integer aid : aids) {

                    RequestQueue queue = MyApplication.getHttpQueue();
                    String _url = MyApplication.getHost() + "address/selectByPrimaryKey";
                    StringRequest request = new StringRequest(Request.Method.POST, _url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Address address = MyApplication.getGson().fromJson(s, Address.class);
                            AddressContent.cache.put(address.getId(), address);
                            AddressContent.ITEMS.add(new AddressContent.AddressItem(user.getUsername(), address.getAddress()));

                            if (fragment == null) {
                                fragment = AddressFragment.newInstance(1);
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.add(R.id.address_recyclerView, fragment);
                                transaction.commit();
                            } else {
                                fragment.addressRecyclerViewAdapter.handler.sendEmptyMessage(1);
                            }
                            address_add.setEnabled(true);
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
                            map.put("aid", String.valueOf(aid));
                            return map;
                        }
                    };
                    queue.add(request);
                }
            }
        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public Map<String, String> setParam() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        return map;
    }
}
