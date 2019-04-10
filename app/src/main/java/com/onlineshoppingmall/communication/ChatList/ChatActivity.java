package com.onlineshoppingmall.communication.ChatList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onlineshoppingmall.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recyclerView, ChatFragment.newInstance())
                    .commitNow();
        }
    }
}
