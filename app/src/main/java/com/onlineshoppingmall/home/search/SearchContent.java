package com.onlineshoppingmall.home.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchContent {

    public static final List<SearchItem> ITEMS = new ArrayList<SearchItem>();

    public static final Map<String, SearchItem> ITEM_MAP = new HashMap<String, SearchItem>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(SearchItem item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
    }

    private static SearchItem createDummyItem(int position) {
        return new SearchItem();
    }

    public static class SearchItem {


    }
}
