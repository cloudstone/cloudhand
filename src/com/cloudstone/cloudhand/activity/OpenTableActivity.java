package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.cloudstone.cloudhand.R;

public class OpenTableActivity extends FragmentActivity {
    
    private ViewPager viewPager; //页卡内容
    private List<Fragment> fragmentList = new ArrayList<Fragment>(); // Tab页面列表
    private TextView tvOrder,tvOrderd; //页卡标题
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_table);
        
        InitTextView();
        InitViewPager();
    }
    
    //初始化ViewPager
    private void InitViewPager() {
        viewPager=(ViewPager) findViewById(R.id.viewPager_open_table);
        fragmentList.add(new OpenTableOrderFragment());
        fragmentList.add(new OpenTableOrderdActivity());
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    
    //初始化头标
    private void InitTextView() {
        tvOrder = (TextView) findViewById(R.id.tv_order);
        tvOrderd = (TextView) findViewById(R.id.tv_orderd);

        tvOrder.setOnClickListener(new MyOnClickListener(0));
        tvOrderd.setOnClickListener(new MyOnClickListener(1));
    }
    
    //头标点击监听
    private class MyOnClickListener implements OnClickListener{
        private int index=0;
        public MyOnClickListener(int i){
            index=i;
        }
        public void onClick(View v) {
            viewPager.setCurrentItem(index);            
        }
        
    }
    
    //ViewPager适配器
    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int arg0) {
            return (fragmentList == null || fragmentList.size() == 0) ? null : fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return  fragmentList.size();
        }
        
    }
    
    //页面切换事件
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onPageSelected(int arg0) {
            if(viewPager.getCurrentItem() == 0) {
                tvOrder.setBackgroundResource(R.drawable.bg_open_table_title_pressed);
                tvOrderd.setBackgroundResource(R.drawable.bg_open_table_title_normal);
            } else {
                tvOrder.setBackgroundResource(R.drawable.bg_open_table_title_normal);
                tvOrderd.setBackgroundResource(R.drawable.bg_open_table_title_pressed);
            }
        }
        
    }

}
