package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
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
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.dialog.TableInfoDialogFragment;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.fragment.TableInfoEmptyFragment;
import com.cloudstone.cloudhand.fragment.TableInfoOccupiedFragment;
import com.cloudstone.cloudhand.network.api.ListTableApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;

public class TableInfoActivity extends FragmentActivity {
    private ViewPager viewPager; //页卡内容
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private CheckedTextView tvEmpty; //页卡标题 - 空闲
    private CheckedTextView tvOccupied; //页卡标题 - 已用
    
    //用于桌子列表的数据
    private List<Table> tables = new ArrayList<Table>();
    
    public List<Table> getTables() {
        return tables;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_table_info);
        update();
        
        initTextView();
        initViewPager();
    }
    
    public void update() {
        //获取桌况
        new ListTableApi().asyncCall(new IApiCallback<List<Table>>() {

            @Override
            public void onSuccess(List<Table> result) {
                tables = result;
                Intent intent = new Intent();
                intent.setAction("updateTableInfo");
                TableInfoActivity.this.sendBroadcast(intent);
            }

            @Override
            public void onFailed(ApiException exception) {
                Toast.makeText(TableInfoActivity.this, R.string.error_list_table_info_failed, Toast.LENGTH_SHORT).show();
                L.e(TableInfoDialogFragment.class, exception);
            }

            @Override
            public void onFinish() {
            }

        });
    }
    
    //初始化ViewPager
    private void initViewPager() {
        viewPager = (ViewPager)findViewById(R.id.viewPager_table_info);
        fragmentList.add(new TableInfoEmptyFragment());
        fragmentList.add(new TableInfoOccupiedFragment());
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    
    //初始化头标
    private void initTextView() {
        tvEmpty = (CheckedTextView)findViewById(R.id.tv_table_name);
        tvOccupied = (CheckedTextView)findViewById(R.id.tv_table_status);

        tvEmpty.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                showEmptyTab();
            }
        });
        
        tvOccupied.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                showOccupiedTab();
            }
        });
    }
    
    //切换到空闲界面
    private void showEmptyTab() {
        viewPager.setCurrentItem(0);
    }
    
    //切换到已用界面
    private void showOccupiedTab() {
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
            tvEmpty.setChecked(position == 0);
            tvOccupied.setChecked(position == 1);
        }
        
    }
    
}
