package com.onlineshoppingmall.shoppingcart.cart;

import com.onlineshoppingmall.remote_entity.Good;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodContent {

    private static List<GoodItem> ITEMS = new ArrayList<GoodItem>();

    private static Map<Integer, Good> cache = new HashMap<>();

    private static GoodItemRecyclerViewAdapter adapter = null;

    public static GoodItemRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public static void setAdapter(GoodItemRecyclerViewAdapter adapter) {
        GoodContent.adapter = adapter;
    }

    public static List<GoodItem> getITEMS() {
        return ITEMS;
    }

    public static void setITEMS(List<GoodItem> ITEMS) {
        GoodContent.ITEMS.clear();
        for (GoodItem item : ITEMS) {
            GoodContent.ITEMS.add(item);
        }
    }

    public static Map<Integer, Good> getCache() {
        return cache;
    }

    public static void setITEMSChecked(boolean isChecked) {
        for (GoodItem item : ITEMS) {
            if (isChecked) {
                item.setSelected(1);
            } else {
                item.setSelected(0);
            }
        }
    }

    public static class GoodItem {
        private int id;
        private int gid;
        private int selected;
        private int amount;

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

        @Override
        public String toString() {
            return "GoodItem{" +
                    "id=" + id +
                    ", gid=" + gid +
                    ", selected=" + selected +
                    ", amount=" + amount +
                    '}';
        }
    }
}
