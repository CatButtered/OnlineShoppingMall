package com.onlineshoppingmall.home.PageList.v;

import com.onlineshoppingmall.R;

import java.util.ArrayList;
import java.util.List;

public class GoodsImagesContent {

    public static final List<GoodsImagesItem> ITEMS = new ArrayList<GoodsImagesItem>();

    private static final int COUNT = 6;

    static {
        int[] small_icons = new int[]{
                R.drawable.home_little_logo1, R.drawable.home_little_logo2,
                R.drawable.home_little_logo3, R.drawable.home_little_logo4,
                R.drawable.home_little_logo5, R.drawable.home_little_logo6
        };
        String[] titles = new String[]{
                "夕夕抢购", "夕夕视频", "夕夕好店", "夕夕特卖", "夕夕直播", "夕夕好货"
        };
        String[] intros = new String[]{
                "限时限量抢半价", "亲测什么好买", "神店挖掘机", "9.9包邮特划算", "越买越开心", "只给精致的你"
        };
        int[] good1s = new int[]{
                R.drawable.home_goods1, R.drawable.home_goods3,
                R.drawable.home_goods5, R.drawable.home_goods7,
                R.drawable.home_goods9, R.drawable.home_goods11
        };
        int[] good2s = new int[]{
                R.drawable.home_goods2, R.drawable.home_goods4,
                R.drawable.home_goods6, R.drawable.home_goods8,
                R.drawable.home_goods10, R.drawable.home_goods12
        };
        for (int i = 1; i <= COUNT; i++) {
            addItem(new GoodsImagesItem(small_icons[i - 1], titles[i - 1], intros[i - 1], good1s[i - 1], good2s[i - 1]));
        }
    }

    private static void addItem(GoodsImagesItem item) {
        ITEMS.add(item);
    }


    public static class GoodsImagesItem {

        public int small_icon;
        public String title;
        public String intro;
        public int good1;
        public int good2;

        public GoodsImagesItem(int small_icon, String title, String intro, int good1, int good2) {
            this.small_icon = small_icon;
            this.title = title;
            this.intro = intro;
            this.good1 = good1;
            this.good2 = good2;
        }
    }
}
