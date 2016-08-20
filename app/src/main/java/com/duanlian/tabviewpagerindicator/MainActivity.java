package com.duanlian.tabviewpagerindicator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.duanlian.tabviewpagerindicator.adapter.MyFragmentPagerAdapter;
import com.duanlian.tabviewpagerindicator.fragment.FragmentA;
import com.duanlian.tabviewpagerindicator.fragment.FragmentB;
import com.duanlian.tabviewpagerindicator.fragment.FragmentC;
import com.duanlian.tabviewpagerindicator.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;//导航栏指示器
    private List<Fragment> mContents;//装载fragment的集合
    private MyFragmentPagerAdapter mAdapter;
    private TextView yiYan;
    private TextView liYa;
    private TextView yuanYuan;
    //定义两个颜色和大小
    private int colorA = Color.parseColor("#ff00ff");//选中的颜色
    private int colorB = Color.WHITE;//默认的颜色
    private float sizeA = 19f;//选中大小
    private float sizeB = 16f;//默认大小



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();
        setListener();
    }


    private void initViews() {
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        //3个标题
        yiYan = (TextView) findViewById(R.id.main_yiyan);
        liYa = (TextView) findViewById(R.id.main_liya);
        yuanYuan = (TextView) findViewById(R.id.yuanyuan);
        FragmentA fragmentA = new FragmentA();
        FragmentB fragmentB = new FragmentB();
        FragmentC fragmentC = new FragmentC();
        mContents = new ArrayList<>();
        mContents.add(fragmentA);
        mContents.add(fragmentB);
        mContents.add(fragmentC);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mContents);
        mViewPager.setAdapter(mAdapter);
        //设置默认页面和颜色
        mViewPager.setCurrentItem(0);
        setTextAttribute(colorA,colorB,colorB,sizeA,sizeB,sizeB);
    }
    /**
     * 导航栏点击事件
     * @param view
     */
    public void meiZi(View view){
        switch (view.getId()) {
            case R.id.main_yiyan:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.main_liya:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.yuanyuan:
                mViewPager.setCurrentItem(2);
                break;
        }

    }

    private void setListener() {
        //ViewPager的监听事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //让indicator和ViewPager联动
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setTextAttribute(colorA,colorB,colorB,sizeA,sizeB,sizeB);
                        break;
                    case 1:
                        setTextAttribute(colorB,colorA,colorB,sizeB,sizeA,sizeB);
                        break;
                    case 2:
                        setTextAttribute(colorB,colorB,colorA,sizeB,sizeB,sizeA);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    /**
     * 自己定义一个给textView设置属性的方法
     */
    private void setTextAttribute(int color1, int color2, int color3,  float size1, float size2, float size3){
        yiYan.setTextColor(color1);
        liYa.setTextColor(color2);
        yuanYuan.setTextColor(color3);
        yiYan.setTextSize(size1);
        liYa.setTextSize(size2);
        yuanYuan.setTextSize(size3);

    }
}
