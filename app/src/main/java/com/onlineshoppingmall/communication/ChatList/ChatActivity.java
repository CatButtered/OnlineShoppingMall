package com.onlineshoppingmall.communication.ChatList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;
import com.onlineshoppingmall.communication.PageList.MessageContent;
import com.onlineshoppingmall.until.MyRequest;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;


public class ChatActivity extends AppCompatActivity implements MyRequest.OnGet {
    private static final String TAG = "ChatActivity";

    private AppCompatEditText input;
    private AppCompatButton send;

    private int wid = 0;
    private WebSocketClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recyclerView, ChatFragment.newInstance())
                    .commitNow();
        }
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.chat_tool_bar);
        toolbar.setNavigationIcon(R.drawable.home_search_return);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        MessageContent.MessageItem item = (MessageContent.MessageItem) bundle.getSerializable("item");
        AppCompatTextView title = findViewById(R.id.chat_title);
        title.setText(item.name);

        input = findViewById(R.id.input);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = input.getText().toString().trim();
                if (!s.equals("")) {
                    client.send(s);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (client == null) {
            MyRequest.volley_Get(this, "message/size");
        } else {
            client.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        client.close();
    }

    @Override
    public void onSuccess(String res) {
        wid = Integer.valueOf(res);
        try {
            client = new WebSocketClient(new URI(MyApplication.getWshost() + "message/" + (wid + 1))) {

                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d(TAG, "onOpen");
                }

                @Override
                public void onMessage(String message) {
                    Log.d(TAG, "onMessage: " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d(TAG, "onClose");
                }

                @Override
                public void onError(Exception ex) {
                    Log.d(TAG, "onError");
                }
            };
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure() {

    }
}
