package com.onlineshoppingmall.communication.ChatList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;
import com.onlineshoppingmall.communication.Database.MessageRecord;
import com.onlineshoppingmall.communication.PageList.MessageContent;
import com.onlineshoppingmall.until.MyRequest;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class ChatActivity extends AppCompatActivity implements MyRequest.OnPost {
    private static final String TAG = "ChatActivity";

    private AppCompatEditText input;
    private AppCompatButton send;

    MessageContent.MessageItem item;
    private WebSocketClient client;

    private String s;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                MessageRecordContent.adapter.notifyItemInserted(MessageRecordContent.ITEMS.size());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recyclerView, ChatFragment.newInstance(1))
                    .commitNow();
        }

        AppCompatButton chat_return = findViewById(R.id.chat_return);
        chat_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        item = (MessageContent.MessageItem) bundle.getSerializable("item");
        AppCompatTextView title = findViewById(R.id.chat_title);
        title.setText(item.name);

        input = findViewById(R.id.input);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = input.getText().toString().trim();
                if (!s.equals("")) {

                    input.setText("");
                    MessageRecord record = new MessageRecord();
                    record.setContent(s);
                    record.setKind(0);
                    MessageRecordContent.ITEMS.add(record);

                    MyRequest.volley_Post(ChatActivity.this, "message/push");

                    handler.sendEmptyMessage(1);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (client == null) {
            try {
                client = new WebSocketClient(new URI(MyApplication.getWshost() + "message/" + item.avatar)) {

                    @Override
                    public void onOpen(ServerHandshake handshakedata) {
                        Log.d(TAG, "onOpen");
                    }

                    @Override
                    public void onMessage(String message) {
                        Log.d(TAG, "onMessage: " + message);
                        MessageRecord record = new MessageRecord();
                        record.setContent(message);
                        record.setKind(1);
                        MessageRecordContent.ITEMS.add(record);
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        Log.d(TAG, "onClose");
                    }

                    @Override
                    public void onError(Exception ex) {
                        Log.d(TAG, "onError");
                        ex.printStackTrace();
                    }
                };
                client.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
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

    }

    @Override
    public void onFailure() {

    }

    @Override
    public Map<String, String> setParam() {
        Map<String, String> map = new HashMap<>();
        if (item.avatar == 2) {
            map.put("cid", "27");
        } else {
            map.put("cid", "2");
        }
        map.put("message", s);
        return map;
    }
}
