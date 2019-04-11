package com.onlineshoppingmall.home;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;
import com.onlineshoppingmall.home.PageList.h.CircleIconFragment;
import com.onlineshoppingmall.home.PageList.v.GoodImagesFragment;
import com.onlineshoppingmall.home.search.SearchContent;
import com.onlineshoppingmall.home.search.SearchFragment;
import com.onlineshoppingmall.remote_entity.Good;
import com.onlineshoppingmall.until.MyRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchActivity extends AppCompatActivity implements MyRequest.OnPost, SearchFragment.OnListFragmentInteractionListener {
    private View mSearch;
    private ImageView btn_return;
    private EditText editText_search;
    private Button btn_search;
    private LinearLayoutCompat search_content;
    private ConstraintLayout search_top;
    private float frameBgHeight = 0;
    private float searchBgHeight = 0;

    private SearchFragment list;
    private MyRequest.OnPost onPost = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState == null) {
            list = SearchFragment.newInstance(1);
            getSupportFragmentManager().beginTransaction().add(R.id.search_list, list).commit();
        }

        initView();
        mSearch.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mSearch.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                performEnterAnimation();
            }
        });
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        mSearch = findViewById(R.id.home_search_ed_bg);
        search_content = findViewById(R.id.search_list);
        btn_return = findViewById(R.id.home_search_return);
        editText_search = findViewById(R.id.home_search_ed);
        btn_search = findViewById(R.id.home_search_btn);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRequest.volley_Post(onPost, "good/searchByKey");
            }
        });
        search_top = findViewById(R.id.home_search_top_bg);

    }

    private void performEnterAnimation() {
        float originY = getIntent().getIntExtra("y", 0);
        int location[] = new int[2];
        mSearch.getLocationOnScreen(location);
        final float translateY = originY - (float) location[1];
        frameBgHeight = search_top.getHeight();

        //放到前一个页面的位置
        mSearch.setY(mSearch.getY() + translateY);
        editText_search.setY(mSearch.getY() + (mSearch.getHeight() - editText_search.getHeight()));
        btn_search.setY(mSearch.getY() + (mSearch.getHeight() - btn_search.getHeight()));
        final ValueAnimator translateVa = ValueAnimator.ofFloat(mSearch.getY(), mSearch.getY());
        searchBgHeight = mSearch.getY();
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearch.setY((Float) animation.getAnimatedValue());
                ViewGroup.LayoutParams linearParams = search_top.getLayoutParams();//取控件当前的布局参数
                linearParams.height = (int) (frameBgHeight - (searchBgHeight - (Float) animation.getAnimatedValue()) * 2);
                search_top.setLayoutParams(linearParams);
                editText_search.setY(mSearch.getY() + (mSearch.getHeight() - editText_search.getHeight()) / 2);
                btn_search.setY(mSearch.getY() + (mSearch.getHeight() - btn_search.getHeight()) / 2);
                btn_return.setY(mSearch.getY() + (mSearch.getHeight() - btn_return.getHeight()) / 2);
            }
        });
        ValueAnimator scaleVa = ValueAnimator.ofFloat(1, 0.8f);

        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearch.setScaleX((Float) animation.getAnimatedValue());
            }
        });
        ValueAnimator alphaVa = ValueAnimator.ofFloat(0, 1f);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                btn_search.setAlpha((Float) animation.getAnimatedValue());
                search_content.setAlpha((Float) animation.getAnimatedValue());
                btn_return.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        alphaVa.setDuration(500);
        translateVa.setDuration(500);
        scaleVa.setDuration(500);

        alphaVa.start();
        translateVa.start();
        scaleVa.start();

    }

    @Override
    public void onBackPressed() {
        performExitAnimation();
    }

    private void performExitAnimation() {
        float originY = getIntent().getIntExtra("y", 0);
        int location[] = new int[2];
        mSearch.getLocationOnScreen(location);
        final float translateY = originY - (float) location[1];
        final ValueAnimator translateVa = ValueAnimator.ofFloat(mSearch.getY(), mSearch.getY() + translateY);
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearch.setY((Float) animation.getAnimatedValue());
                btn_return.setY(mSearch.getY() + (mSearch.getHeight() - btn_return.getHeight()) / 2);
                btn_search.setY(mSearch.getY() + (mSearch.getHeight() - btn_search.getHeight()) / 2);
                editText_search.setY(mSearch.getY() + (mSearch.getHeight() - editText_search.getHeight()) / 2);
                ViewGroup.LayoutParams linearParams = search_top.getLayoutParams();
                linearParams.height = (int) (frameBgHeight - (searchBgHeight - (Float) animation.getAnimatedValue()) * 2);
                search_top.setLayoutParams(linearParams);
            }
        });
        translateVa.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ValueAnimator scaleVa = ValueAnimator.ofFloat(0.8f, 1f);
        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearch.setScaleX((Float) animation.getAnimatedValue());
            }
        });
        ValueAnimator alphaVa = ValueAnimator.ofFloat(1, 0f);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                btn_search.setAlpha((Float) animation.getAnimatedValue());
                search_content.setAlpha((Float) animation.getAnimatedValue());
                btn_return.setAlpha((Float) animation.getAnimatedValue());

            }
        });
        alphaVa.setDuration(500);
        translateVa.setDuration(500);
        scaleVa.setDuration(500);
        alphaVa.start();
        translateVa.start();
        scaleVa.start();

    }

    @Override
    public void onSuccess(String res) {
        Type setType = new TypeToken<List<Good>>() {
        }.getType();
        SearchContent.setITEMS((List<Good>) MyApplication.getGson().fromJson(res, setType));
        list.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onFailure() {

    }

    @Override
    public Map<String, String> setParam() {
        Map<String, String> map = new HashMap<>();
        map.put("key", editText_search.getText().toString());
        return map;
    }

    @Override
    public void onListFragmentInteraction(Good item) {

    }
}
