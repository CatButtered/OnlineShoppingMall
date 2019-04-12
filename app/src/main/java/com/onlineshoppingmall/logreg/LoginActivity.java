package com.onlineshoppingmall.logreg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineshoppingmall.MainActivity;
import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;
import com.onlineshoppingmall.remote_entity.User;
import com.onlineshoppingmall.until.MyRequest;
import com.onlineshoppingmall.until.ShareData;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener,
        TextWatcher, MyRequest.OnPost {
    //    private ImageButton mIbNavigationBack;
//    private LinearLayout mLlLoginPull;
//    private View mLlLoginLayer;
//    private LinearLayout mLlLoginOptions;
    private EditText mEtLoginUsername;
    private EditText mEtLoginPwd;
    //    private LinearLayout mLlLoginUsername;
    private ImageView mIvLoginUsernameDel;
    private Button mBtLoginSubmit;
    //    private LinearLayout mLlLoginPwd;
    private ImageView mIvLoginPwdDel;
    //    private ImageView mIvLoginLogo;
//    private LinearLayout mLayBackBar;
    private TextView mTvLoginForgetPwd;
    private Button mBtLoginRegister;
    //全局Toast
    private Toast mToast;
    private int mLogoHeight;
    private int mLogoWidth;

    private CheckBox isRem;
    private ShareData shareData;

    View statusBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        shareData = new ShareData(this);
        if (shareData.readShared("isrem") != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                initstatusBarView();
                getWindow().getDecorView().removeOnLayoutChangeListener(this);
            }
        });

        initView();
    }

    private void initstatusBarView() {
        if (statusBarView == null) {
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getWindow().findViewById(identifier);
        }
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.background_login);
        }
    }

    //初始化视图
    private void initView() {

        //logo
        //  mIvLoginLogo = findViewById(R.id.iv_login_logo);

        //username
        // mLlLoginUsername = findViewById(R.id.ll_login_username);
        mEtLoginUsername = findViewById(R.id.et_login_username);
        mIvLoginUsernameDel = findViewById(R.id.iv_login_username_del);

        //passwd
        // mLlLoginPwd = findViewById(R.id.ll_login_pwd);
        mEtLoginPwd = findViewById(R.id.et_login_pwd);
        mIvLoginPwdDel = findViewById(R.id.iv_login_pwd_del);

        //提交、注册
        mBtLoginSubmit = findViewById(R.id.bt_login_submit);
        mBtLoginRegister = findViewById(R.id.bt_login_register);

        isRem = findViewById(R.id.cb_remember_login);

        //忘记密码
//        mTvLoginForgetPwd = findViewById(R.id.tv_login_forget_pwd);
//        mTvLoginForgetPwd.setOnClickListener(this);

        //注册点击事件
        // mLlLoginPull.setOnClickListener(this);
        // mIbNavigationBack.setOnClickListener(this);
        mEtLoginUsername.setOnClickListener(this);
        mIvLoginUsernameDel.setOnClickListener(this);
        mBtLoginSubmit.setOnClickListener(this);
        mBtLoginRegister.setOnClickListener(this);
        mEtLoginPwd.setOnClickListener(this);
        mIvLoginPwdDel.setOnClickListener(this);

        //注册其它事件
        //  mLayBackBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mEtLoginUsername.setOnFocusChangeListener(this);
        mEtLoginUsername.addTextChangedListener((TextWatcher) this);
        mEtLoginPwd.setOnFocusChangeListener(this);
        mEtLoginPwd.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_login_username:
                mEtLoginPwd.clearFocus();
                mEtLoginUsername.setFocusableInTouchMode(true);
                mEtLoginUsername.requestFocus();
                break;
            case R.id.et_login_pwd:
                mEtLoginUsername.clearFocus();
                mEtLoginPwd.setFocusableInTouchMode(true);
                mEtLoginPwd.requestFocus();
                break;
            case R.id.iv_login_username_del:
                //清空用户名
                mEtLoginUsername.setText(null);
                break;
            case R.id.iv_login_pwd_del:
                //清空密码
                mEtLoginPwd.setText(null);
                break;
            case R.id.bt_login_submit:
                //登录
                loginRequest();
                break;
            case R.id.bt_login_register:
                //注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
//            case R.id.tv_login_forget_pwd:
//                //忘记密码
//                startActivity(new Intent(MainActivity.this, ForgetPwdActivity.class));

            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public void afterTextChanged(Editable s) {
        String username = mEtLoginUsername.getText().toString().trim();
        String pwd = mEtLoginPwd.getText().toString().trim();

        //是否显示清除按钮
        if (username.length() > 0) {
            mIvLoginUsernameDel.setVisibility(View.VISIBLE);
        } else {
            mIvLoginUsernameDel.setVisibility(View.INVISIBLE);
        }
        if (pwd.length() > 0) {
            mIvLoginPwdDel.setVisibility(View.VISIBLE);
        } else {
            mIvLoginPwdDel.setVisibility(View.INVISIBLE);
        }

        //登录按钮是否可用
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(username)) {
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
        } else {
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void showToast(int msg) {
        if (null != mToast) {
            mToast.setText(msg);
        } else {
            mToast = Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT);
        }

        mToast.show();
    }

    private void loginRequest() {
        if (shareData.readShared("isrem") == null) {
            MyRequest.volley_Post(this, "user/selectUser");
        } else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void onGlobalLayout() {

    }

    @Override
    public void onSuccess(String res) {
        User user = MyApplication.getGson().fromJson(res, User.class);
        if (user == null) {
            Toast.makeText(this, "用户名及密码错误!", Toast.LENGTH_SHORT).show();
        } else {
            shareData.writeShared("uid", String.valueOf(user.getId()));
            shareData.writeShared("username", user.getUsername());
            shareData.writeShared("password", user.getPassword());
            if (isRem.isChecked()) {
                shareData.writeShared("isrem", "1");
            }
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "用户名及密码错误!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Map<String, String> setParam() {
        Map<String, String> map = new HashMap<>();
        String username = mEtLoginUsername.getText().toString().trim();
        String pwd = mEtLoginPwd.getText().toString().trim();
        map.put("username", username);
        map.put("password", pwd);
        return map;
    }
}
