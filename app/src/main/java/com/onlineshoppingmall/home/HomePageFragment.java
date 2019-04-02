package com.onlineshoppingmall.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.onlineshoppingmall.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {

    AppCompatImageView search;
    //ViewPager
    private ViewPager viewPager;
    //装点点的AppCompatImageView数组
    private AppCompatImageView[] tips;
    //装AppCompatImageView数组
    private AppCompatImageView[] AppCompatImageViews;
    //图片资源
    private int[] imgIdArray;
    private ViewGroup group;

//    @SuppressLint("HandlerLeak")
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            int curindex = (viewPager.getCurrentItem()+1)%(AppCompatImageViews.length+2);
//            viewPager.removeView(getView());
//            viewPager.setCurrentItem(curindex,true);
//        }
//    };
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            Message message = new Message();
//            handler.sendMessage(message);
//        }
//    };


    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home_page, container, false);
        // Inflate the layout for this fragment
        search = v.findViewById(R.id.main_top_search);
        search.setOnClickListener(this);
        group = (ViewGroup) v.findViewById(R.id.main_viewGroup);
        viewPager = (ViewPager) v.findViewById(R.id.main_viewpager);

        //载入图片资源id
        imgIdArray = new int[]{R.drawable.main_img01, R.drawable.main_img02, R.drawable.main_img03, R.drawable.main_img04, R.drawable.main_img05};
        //将点点加入到viewGroup中
        setImage();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setImageBackground(i % AppCompatImageViews.length);

            }

            @Override
            public void onPageScrollStateChanged(int i) {


            }
        });
        //handler.postDelayed(runnable,3*1000);


        return v;
    }


    private void setImage() {
        tips = new AppCompatImageView[imgIdArray.length];
        for (int i = 0; i < tips.length; i++) {
            AppCompatImageView AppCompatImageView = new AppCompatImageView(getContext());
            AppCompatImageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            tips[i] = AppCompatImageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.main_pot1);
            } else {
                tips[i].setBackgroundResource(R.drawable.main_pot2);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 1;
            layoutParams.rightMargin = 1;
            group.addView(AppCompatImageView, layoutParams);
        }

        //将图片装载到数组中
        AppCompatImageViews = new AppCompatImageView[imgIdArray.length];
        for (int i = 0; i < AppCompatImageViews.length; i++) {
            AppCompatImageView AppCompatImageView = new AppCompatImageView(getContext());
            AppCompatImageViews[i] = AppCompatImageView;
            AppCompatImageView.setBackgroundResource(imgIdArray[i]);

        }
        //设置Adapter
        viewPager.setAdapter(new MyAdapter(AppCompatImageViews));
        //设置监听，主要是设置点点的监听
//        //viewPager.setOnPageChangeListener((ViewPager.OnPageChangeListener) getContext());
//        viewPager.setOnPageChangeListener();
        //设置ViewPager的默认项，设置为长度的100倍，这样子开始就能往左划
        viewPager.setCurrentItem((AppCompatImageViews.length) * 100);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_top_search: {
                Intent in = new Intent(getActivity(), SearchActivity.class);
                startActivity(in);
                break;
            }

        }

    }


    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.main_pot1);
            } else {
                tips[i].setBackgroundResource(R.drawable.main_pot2);
            }
        }
    }

}
