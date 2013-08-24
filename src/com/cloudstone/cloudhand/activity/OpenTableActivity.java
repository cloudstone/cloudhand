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
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.fragment.OpenTableOrderFragment;
import com.cloudstone.cloudhand.fragment.OpenTableOrderdFragment;

public class OpenTableActivity extends FragmentActivity {
    
    private ViewPager viewPager; //页卡内容
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private TextView tvOrder; //页卡标题 - 点餐
    private TextView tvOrderd; //页卡标题 - 已点
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_table);
        
        initTextView();
        initViewPager();
    }
    
    //初始化ViewPager
    private void initViewPager() {
        viewPager = (ViewPager)findViewById(R.id.viewPager_open_table);
        fragmentList.add(new OpenTableOrderFragment());
        fragmentList.add(new OpenTableOrderdFragment());
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    
    //初始化头标
    private void initTextView() {
        tvOrder = (TextView)findViewById(R.id.tv_order);
        tvOrderd = (TextView)findViewById(R.id.tv_orderd);

        tvOrder.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                showOrderTab();
            }
        });
        
        tvOrderd.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                showOrderdTab();
            }
        });
    }
    
    //切换到点餐界面
    private void showOrderTab() {
        viewPager.setCurrentItem(0);
    }
    
    //切换到已点界面
    private void showOrderdTab() {
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
            //TODO 改成selector
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
