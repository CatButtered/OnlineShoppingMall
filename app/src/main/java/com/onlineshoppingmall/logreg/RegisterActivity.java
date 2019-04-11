package com.onlineshoppingmall.logreg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.onlineshoppingmall.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.bt_register_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register_submit:
                finish();
                break;
        }
    }
}

