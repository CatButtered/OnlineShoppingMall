package com.onlineshoppingmall.home.search;

import com.onlineshoppingmall.remote_entity.Good;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchContent {

    private static List<Good> ITEMS = new ArrayList<>();

    public static List<Good> getITEMS() {
        return ITEMS;
    }

    public static void setITEMS(List<Good> ITEMS) {
        SearchContent.ITEMS.clear();
        for (Good item : ITEMS) {
            SearchContent.ITEMS.add(item);
        }
    }
}
