package com.onlineshoppingmall.penson.addord.ord;

import java.util.ArrayList;
import java.util.List;

public class OrderContent {

    public static List<OrderItem> ITEMS = new ArrayList<OrderItem>();

    public static class OrderItem {
        private int gid;
        private String name;
        private int amount;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
