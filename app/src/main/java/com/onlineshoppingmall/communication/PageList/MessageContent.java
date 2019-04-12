package com.onlineshoppingmall.communication.PageList;

import com.onlineshoppingmall.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageContent {

    public static final List<MessageItem> ITEMS = new ArrayList<MessageItem>();

    private static final int COUNT = 25;

    public static class MessageItem implements Serializable {

        public final int avatar;
        public final String name;
        public final String content;
        public final String time;
        public final String msg_num;

        public MessageItem(int avatar, String name, String content, String time, String msg_num) {
            this.avatar = avatar;
            this.name = name;
            this.content = content;
            this.time = time;
            this.msg_num = msg_num;
        }
    }
}
