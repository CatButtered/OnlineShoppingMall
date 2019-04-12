package com.onlineshoppingmall.logreg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;
import com.onlineshoppingmall.remote_entity.User;
import com.onlineshoppingmall.until.MyRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, MyRequest.OnPost {

    private AppCompatEditText username;
    private AppCompatEditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.et_register_username);
        password = findViewById(R.id.et_register_auth_code);

        findViewById(R.id.bt_register_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register_submit:
                MyRequest.volley_Post(this, "user/insertUser");
                break;
        }
    }

    @Override
    public void onSuccess(String res) {
        User user = MyApplication.getGson().fromJson(res, User.class);
        if (user == null) {
            Toast.makeText(this, "注册失败，请重试!", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "注册失败，请重试!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Map<String, String> setParam() {
        Map<String, String> map = new HashMap<>();
        String name = username.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        map.put("username", name);
        map.put("password", pwd);
        return map;
    }
}

