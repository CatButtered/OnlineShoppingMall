package com.onlineshoppingmall.communication.PageList;

import com.onlineshoppingmall.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageContent {

    public static final List<MessageItem> ITEMS = new ArrayList<MessageItem>();

    public static final Map<String, MessageItem> ITEM_MAP = new HashMap<String, MessageItem>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(MessageItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.shop, item);
    }

    private static MessageItem createDummyItem(int position) {
        return new MessageItem(
                MyApplication.getHost() + "good/image?id=" + position,
                "陈扬蟹黄汤包", "蟹黄汤包好好吃！蟹黄汤包好好吃！蟹黄汤包好好吃！蟹黄汤包好好吃！", "12:00", "1");
    }

    public static class MessageItem implements Serializable {

        public final String avatar;
        public final String shop;
        public final String content;
        public final String time;
        public final String msg_num;

        public MessageItem(String avatar, String shop, String content, String time, String msg_num) {
            this.avatar = avatar;
            this.shop = shop;
            this.content = content;
            this.time = time;
            this.msg_num = msg_num;
        }

        @Override
        public String toString() {
            return "MessageItem{" +
                    "avatar=" + avatar +
                    ", shop='" + shop + '\'' +
                    ", content='" + content + '\'' +
                    ", time='" + time + '\'' +
                    ", msg_num='" + msg_num + '\'' +
                    '}';
        }
    }
}
