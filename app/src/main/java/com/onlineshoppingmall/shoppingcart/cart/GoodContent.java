package com.onlineshoppingmall.shoppingcart.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodContent {

    public static final List<GoodItem> ITEMS = new ArrayList<GoodItem>();

    public static final Map<String, GoodItem> ITEM_MAP = new HashMap<String, GoodItem>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(GoodItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.id), item);
    }

    private static GoodItem createDummyItem(int position) {
        return new GoodItem(1, 1, 1, 1);
    }

    public static class GoodItem {
        public int id;
        public int gid;
        public int selected;
        public int amount;

        public GoodItem() {

        }

        public GoodItem(int id, int gid, int selected, int amount) {
            this.id = id;
            this.gid = gid;
            this.selected = selected;
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
