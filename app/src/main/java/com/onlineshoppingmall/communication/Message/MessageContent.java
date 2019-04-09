package com.onlineshoppingmall.communication.Message;

import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.until.CircleImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MessageContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<MessageItem> ITEMS = new ArrayList<MessageItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, MessageItem> ITEM_MAP = new HashMap<String, MessageItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
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

    /**
     * A dummy item representing a piece of content.
     */
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
