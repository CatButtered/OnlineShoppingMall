package com.onlineshoppingmall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.onlineshoppingmall.communication.Message.MessageContent;
import com.onlineshoppingmall.communication.MessageFragment;
import com.onlineshoppingmall.communication.MessagePageFragment;
import com.onlineshoppingmall.home.HomePageFragment;
import com.onlineshoppingmall.penson.PersonalCenterFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MessageFragment.OnListFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private int[] rbs = {R.id.rd_homepage, R.id.rd_message, R.id.rd_shoppingcart, R.id.rd_personcenter};
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.vp_main);
        radioGroup = findViewById(R.id.rd_group);
        fragments = new ArrayList<>();
//        HomePageFragment homePageFragment = new HomePageFragment();
        MessagePageFragment messagePageFragment = new MessagePageFragment();
        ShoppingCartPageFragment shoppingCartPageFragment = new ShoppingCartPageFragment();
        PersonalCenterFragment personalCenterFragment = new PersonalCenterFragment();
//        fragments.add(homePageFragment);
        fragments.add(messagePageFragment);
        fragments.add(shoppingCartPageFragment);
        fragments.add(personalCenterFragment);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rbs.length; i++) {
                    if (rbs[i] != checkedId)
                        continue;
                    viewPager.setCurrentItem(i);
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                radioGroup.check(rbs[i]);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        radioGroup.check(rbs[1]);
    }

    @Override
    public void onListFragmentInteraction(MessageContent.MessageItem item) {

    }
}
