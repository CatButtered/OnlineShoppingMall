package com.onlineshoppingmall.home.PageList.h;

import com.onlineshoppingmall.R;

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
public class CircleItemContent {

    public static final List<CircleItem> ITEMS = new ArrayList<CircleItem>();

    private static final int COUNT = 11;

    static {
        int[] rids = new int[]{R.drawable.home_logo1, R.drawable.home_logo2,
                R.drawable.home_logo3, R.drawable.home_logo4, R.drawable.home_logo5,
                R.drawable.home_logo6, R.drawable.home_logo7, R.drawable.home_logo8,
                R.drawable.home_logo9, R.drawable.home_logo10, R.drawable.home_logo11};
        String[] contents = new String[]{
                "生活超市", "分润商城", "惠生活", "美食", "婴儿用品",
                "特产", "宠物花鸟", "移动充值", "E连商城", "叮咚网", "OSM专营"
        };
        for (int i = 1; i <= COUNT; i++) {
            addItem(new CircleItem(rids[i - 1], contents[i - 1]));
        }
    }

    private static void addItem(CircleItem item) {
        ITEMS.add(item);
    }

    public static class CircleItem {
        public int resourceId;
        public String content;

        public CircleItem(int resourceId, String content) {
            this.resourceId = resourceId;
            this.content = content;
        }
    }
}
