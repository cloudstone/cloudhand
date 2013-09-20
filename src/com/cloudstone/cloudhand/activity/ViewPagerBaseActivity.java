package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;

import com.cloudstone.cloudhand.R;

/**
 * 
 * @author xhc
 *
 */
public class ViewPagerBaseActivity extends BaseActivity {
    
    private ViewPager viewPager; //页卡内容
    protected List<Fragment> fragmentList = new ArrayList<Fragment>();
    protected List<CheckedTextView> titleList = new ArrayList<CheckedTextView>(); //页卡标题
    
//    protected void setTitleList(int size) {
//        for(int i = 0; i < size; i++) {
//            CheckedTextView title = (CheckedTextView)findViewById(R.id.tv_firstTitle);
//            titleList.add(title);
//        }
//    }
    
    //初始化ViewPager
    protected void initViewPager() {
        viewPager = (ViewPager)findViewById(R.id.viewPager_base);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    
    //初始化头标
    protected void initTextView(int size) {
        for(int i = 0;i < size;i++) {
            CheckedTextView title;
            if(i == 0) {
                title = (CheckedTextView)findViewById(R.id.tv_firstTitle);
                title.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(0);
                    }
                });
            } else if(i == 1) {
                title = (CheckedTextView)findViewById(R.id.tv_secondTitle);
                title.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(1);
                    }
                });
            } else {
                title = (CheckedTextView)findViewById(R.id.tv_thirdTitle);
                title.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(2);
                    }
                });
            }
            titleList.add(title);
        }
    }
    
    //切换页卡
    /*private void showTab(int position) {
        viewPager.setCurrentItem(position);
    }*/
    
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
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            System.out.println(titleList.size());
            for(int i = 0; i < titleList.size(); i++) {
                titleList.get(i).setChecked(i == position); 
            }
        }
        
    }
    
}