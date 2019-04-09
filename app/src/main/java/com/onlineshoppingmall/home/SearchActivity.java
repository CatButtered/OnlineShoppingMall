package com.onlineshoppingmall.home;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.onlineshoppingmall.R;

public class SearchActivity extends AppCompatActivity {
    private View mSearch;
    private ImageView btn_return;
    private EditText editText_search;
    private Button btn_search;
    private LinearLayout search_content;
    private ConstraintLayout search_top;
    float frameBgHeight = 0;
    float searchBgHeight = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
    private void initView(){
        mSearch = findViewById(R.id.home_search_ed_bg);
        search_content = findViewById(R.id.home_search_content_bg);
        btn_return = findViewById(R.id.home_search_return);
        editText_search = findViewById(R.id.home_search_ed);
        btn_search = findViewById(R.id.home_search_btn);
        search_top = findViewById(R.id.home_search_top_bg);

    }
    private void performEnterAnimation(){
        float originY = getIntent().getIntExtra("y",0);
        int location[] = new int[2];
        mSearch.getLocationOnScreen(location);
        final float translateY = originY - (float) location[1];
        frameBgHeight = search_top.getHeight();

        //放到前一个页面的位置
        mSearch.setY(mSearch.getY()+translateY);
        editText_search.setY(mSearch.getY()+(mSearch.getHeight()-editText_search.getHeight()));
        btn_search.setY(mSearch.getY()+(mSearch.getHeight()-btn_search.getHeight()));
        final ValueAnimator translateVa = ValueAnimator.ofFloat(mSearch.getY(),mSearch.getY());
        searchBgHeight = mSearch.getY();
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearch.setY((Float)animation.getAnimatedValue());
                ViewGroup.LayoutParams linearParams = search_top.getLayoutParams();//取控件当前的布局参数
                linearParams.height = (int) (frameBgHeight-(searchBgHeight-(Float)animation.getAnimatedValue())*2);
                search_top.setLayoutParams(linearParams);
                editText_search.setY(mSearch.getY()+(mSearch.getHeight()-editText_search.getHeight())/2);
                btn_search.setY(mSearch.getY()+(mSearch.getHeight()-btn_search.getHeight())/2);
                btn_return.setY(mSearch.getY() + (mSearch.getHeight()-btn_return.getHeight())/2);
            }
        });
        ValueAnimator scaleVa = ValueAnimator.ofFloat(1,0.8f);

        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearch.setScaleX((Float) animation.getAnimatedValue());
            }
        });
        ValueAnimator alphaVa = ValueAnimator.ofFloat(0,1f);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                btn_search.setAlpha((Float)animation.getAnimatedValue());
                search_content.setAlpha((Float)animation.getAnimatedValue());
                btn_return.setAlpha((Float)animation.getAnimatedValue());
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
    private  void performExitAnimation(){
        float originY = getIntent().getIntExtra("y",0);
        int location[] = new int[2];
        mSearch.getLocationOnScreen(location);
        final float translateY = originY - (float)location[1];
        final ValueAnimator translateVa = ValueAnimator.ofFloat(mSearch.getY(),mSearch.getY()+translateY);
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearch.setY((Float)animation.getAnimatedValue());
                btn_return.setY(mSearch.getY()+ (mSearch.getHeight()-btn_return.getHeight())/2);
                btn_search.setY(mSearch.getY() + (mSearch.getHeight()-btn_search.getHeight())/2);
                editText_search.setY(mSearch.getY()+(mSearch.getHeight()-editText_search.getHeight())/2);
                ViewGroup.LayoutParams linearParams = search_top.getLayoutParams();
                linearParams.height = (int)(frameBgHeight-(searchBgHeight-(Float)animation.getAnimatedValue())*2);
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
                overridePendingTransition(0,0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ValueAnimator scaleVa = ValueAnimator.ofFloat(0.8f,1f);
        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearch.setScaleX((Float)animation.getAnimatedValue());
            }
        });
        ValueAnimator alphaVa = ValueAnimator.ofFloat(1,0f);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                btn_search.setAlpha((Float)animation.getAnimatedValue());
                search_content.setAlpha((Float)animation.getAnimatedValue());
                btn_return.setAlpha((Float)animation.getAnimatedValue());

            }
        });
        alphaVa.setDuration(500);
        translateVa.setDuration(500);
        scaleVa.setDuration(500);
        alphaVa.start();
        translateVa.start();
        scaleVa.start();


    }
}
