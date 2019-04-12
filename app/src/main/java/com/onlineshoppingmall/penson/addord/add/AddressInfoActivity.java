package com.onlineshoppingmall.penson.addord.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;
import com.onlineshoppingmall.remote_entity.User;
import com.onlineshoppingmall.until.MyRequest;
import com.onlineshoppingmall.until.ShareData;

import java.util.HashMap;
import java.util.Map;

public class AddressInfoActivity extends AppCompatActivity implements MyRequest.OnPost {

    private AppCompatButton info_return;
    private AppCompatEditText username;
    private AppCompatEditText name;
    private AppCompatCheckBox primary;
    private AppCompatButton save;

    private ShareData data;

    private boolean isAdd;

    private String uid;
    private String old_address;
    private String new_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_info);

        username = findViewById(R.id.address_info_username);
        name = findViewById(R.id.address_info_name);
        primary = findViewById(R.id.address_info_primary);
        save = findViewById(R.id.address_info_save);
        info_return = findViewById(R.id.address_info_return);

        data = new ShareData(this);
        uid = data.readShared("uid");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            isAdd = true;
        } else {
            isAdd = false;
            AddressContent.AddressItem item = (AddressContent.AddressItem) bundle.getSerializable("address");

            username.setText(item.getUsername());
            name.setText(item.getName());
            old_address = item.getName();
        }

        info_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdd) {
                    MyRequest.volley_Post(AddressInfoActivity.this, "address/insertAddress");
                } else {
                    MyRequest.volley_Post(AddressInfoActivity.this, "address/updateAddress");
                }
                if (primary.isChecked()) {

                    RequestQueue queue = MyApplication.getHttpQueue();
                    String _url = MyApplication.getHost() + "address/setPrimaryAddress";
                    StringRequest request = new StringRequest(Request.Method.POST, _url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            User user = MyApplication.getGson().fromJson(s, User.class);
                            if (user == null) {
                                Toast.makeText(AddressInfoActivity.this, "保存默认地址失败!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddressInfoActivity.this, "保存默认地址成功!", Toast.LENGTH_SHORT).show();
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
                            map.put("address", name.getText().toString());
                            return map;
                        }
                    };
                    queue.add(request);

                }
            }
        });

    }

    @Override
    public void onSuccess(String res) {
        User user = MyApplication.getGson().fromJson(res, User.class);
        if (user == null) {
            Toast.makeText(AddressInfoActivity.this, "保存失败!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddressInfoActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onFailure() {

    }

    @Override
    public Map<String, String> setParam() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        if (isAdd) {
            map.put("address", name.getText().toString());
        } else {
            map.put("old_address", old_address);
            map.put("new_address", name.getText().toString());
        }
        return map;
    }
}
