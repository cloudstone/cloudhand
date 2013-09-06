package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;

import com.cloudstone.cloudhand.R;

public class ViewPagerBaseActivity extends FragmentActivity {
    
    private ViewPager viewPager; //页卡内容
    protected List<Fragment> fragmentList = new ArrayList<Fragment>();
    private CheckedTextView tvFirstTitle; //页卡标题1
    private CheckedTextView tvSecondTitle; //页卡标题2
    
    //初始化ViewPager
    protected void initViewPager() {
        viewPager = (ViewPager)findViewById(R.id.viewPager_base);
//        fragmentList.add(new OpenTableOrderFragment());
//        fragmentList.add(new OpenTableOrderedFragment());
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    
    //初始化头标
    protected void initTextView() {
        tvFirstTitle = (CheckedTextView)findViewById(R.id.tv_firstTitle);
        tvSecondTitle = (CheckedTextView)findViewById(R.id.tv_secondTitle);

        tvFirstTitle.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                showFirstTab();
            }
        });
        
        tvSecondTitle.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                showSecondTab();
            }
        });
    }
    
    //切换到页卡1
    private void showFirstTab() {
        viewPager.setCurrentItem(0);
    }
    
    //切换到页卡2
    private void showSecondTab() {
        viewPager.setCurrentItem(1);
    }
    
    //ViewPager适配器
    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return  fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        
    }
    
    //页面切换事件
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            tvFirstTitle.setChecked(position == 0);
            tvSecondTitle.setChecked(position == 1);
        }
        
    }
    
}