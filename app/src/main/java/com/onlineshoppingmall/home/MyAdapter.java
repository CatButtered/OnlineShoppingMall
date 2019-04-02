package com.onlineshoppingmall.home;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class MyAdapter extends PagerAdapter {
    private ImageView[] imageViews;

    public MyAdapter(ImageView[] imageViews){
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
       ((ViewPager)container).removeView(imageViews[position % imageViews.length]);

    }

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     */
    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager)container).addView(imageViews[position % imageViews.length], 0);
        return imageViews[position % imageViews.length];
    }
}
