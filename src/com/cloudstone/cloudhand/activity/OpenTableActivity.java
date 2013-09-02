package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.dialog.ExitOrderDialogFragment;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.fragment.OpenTableOrderFragment;
import com.cloudstone.cloudhand.fragment.OpenTableOrderdFragment;
import com.cloudstone.cloudhand.network.api.ListDishApi;
import com.cloudstone.cloudhand.network.api.ListDishNoteApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;

public class OpenTableActivity extends FragmentActivity {
    
    private ViewPager viewPager; //页卡内容
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private TextView tvOrder; //页卡标题 - 点餐
    private TextView tvOrderd; //页卡标题 - 已点
    
    //菜品列表的数据
    private List<Dish> dishes = new ArrayList<Dish>();
    
    public List<Dish> getDishes() {
        return dishes;
    }
    
    //菜品备注列表的数据
    private List<DishNote> dishNotes = new ArrayList<DishNote>();
    
    public List<DishNote> getDishNotes() {
        return dishNotes;
    }
    
    //用于记录每样菜点了几份
    private Map<Integer, Integer> dishCountMap = new HashMap<Integer, Integer>();
    
    public int getDishCount(int dishId) {
        if (!dishCountMap.containsKey(dishId)) {
            return 0;
        }
        return dishCountMap.get(dishId);
    }
  
    public void setDishCount(int dishId, int count) {
        dishCountMap.put(dishId, count);
    }
    
    //用于记录每样菜选择了哪些备注
    private Map<Integer, Set<Integer>> dishNoteMap = new HashMap<Integer, Set<Integer>>();
    
    public Set<Integer> getDishNoteIdSet(int dishId) {
        if(dishNoteMap.get(dishId) == null) {
            return new HashSet<Integer>();
        } else {
            return dishNoteMap.get(dishId);
        }
    }

    public void setDishNoteIdSet(int dishId, Set<Integer> dishNotes) {
        dishNoteMap.put(dishId, dishNotes);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_open_table);
        
        initTextView();
        initViewPager();
        
        //获取菜单列表
        new ListDishApi().asyncCall(new IApiCallback<List<Dish>>() {
            
            @Override
            public void onSuccess(List<Dish> result) {
                for(int i = 0;i < result.size();i++) {
                    dishes.add(result.get(i));
                }
                //发送更新菜单界面的广播
                Intent intent = new Intent();
                intent.setAction("update");
                OpenTableActivity.this.sendBroadcast(intent);
            }
            
            @Override
            public void onFinish() {}
            
            @Override
            public void onFailed(ApiException exception) {
                L.e(OpenTableActivity.this, exception);
            }
        });
        
        //获取备注列表
        new ListDishNoteApi().asyncCall(new IApiCallback<List<DishNote>>() {
            
            @Override
            public void onSuccess(List<DishNote> result) {
                dishNotes = result;
            }
            
            @Override
            public void onFinish() {}
            
            @Override
            public void onFailed(ApiException exception) {}
        });
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
    
    //按下返回键弹出退出确认
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
            ExitOrderDialogFragment dialog = new ExitOrderDialogFragment();
            dialog.show(getFragmentManager(), "exitOrderDialogFragment");
            return true;
        } 
        return super.onKeyDown(keyCode, event);
    }
    
}