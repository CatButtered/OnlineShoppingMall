package com.onlineshoppingmall.penson.addord.add;

import com.onlineshoppingmall.remote_entity.Address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressContent {

    public static final List<AddressItem> ITEMS = new ArrayList<AddressItem>();

    public static final Map<Integer, Address> cache = new HashMap<>();

    public static class AddressItem implements Serializable {
        private String username;
        private String name;

        public AddressItem() {
        }

        public AddressItem(String username, String name) {
            this.username = username;
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
