package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.constant.BroadcastConst;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.dialog.ExitOrderDialogFragment;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.fragment.OpenTableOrderFragment;
import com.cloudstone.cloudhand.fragment.OpenTableOrderedFragment;
import com.cloudstone.cloudhand.network.api.ListDishApi;
import com.cloudstone.cloudhand.network.api.ListDishNoteApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.DishBag;
import com.cloudstone.cloudhand.util.L;

/**
 * 
 * @author xhc
 *
 */
public class OpenTableActivity extends ViewPagerBaseActivity {
    
    //菜品列表数据
    private DishBag dishes = new DishBag();
    //用于记录每样菜点了几份
    private Map<Integer, Integer> dishCountMap = new HashMap<Integer, Integer>();
    //菜品备注列表数据
    private List<DishNote> dishNotes = new ArrayList<DishNote>();
    //用于记录每样菜选择了哪些备注
    private Map<Integer, Set<Integer>> dishNoteMap = new HashMap<Integer, Set<Integer>>();
    
    //getter and setter
    public DishBag getDishes() {
        return dishes;
    }
    
    public int getDishCount(int dishId) {
        if (!dishCountMap.containsKey(dishId)) {
            return 0;
        }
        return dishCountMap.get(dishId);
    }
    
    public void setDishCount(int dishId, int count) {
        dishCountMap.put(dishId, count);
    }
    
    public List<DishNote> getDishNotes() {
        return dishNotes;
    }
    
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
    protected void initViewPager() {
        super.initViewPager();
        fragmentList.add(new OpenTableOrderFragment());
        fragmentList.add(new OpenTableOrderedFragment());
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
                    dishes.put(result.get(i));
                }
                //发送更新菜单界面的广播
                Intent intent = new Intent();
                intent.setAction(BroadcastConst.UPDATE_ORDER);
                OpenTableActivity.this.sendBroadcast(intent);
            }
            
            @Override
            public void onFinish() {
            }
            
            @Override
            public void onFailed(ApiException exception) {
                Toast.makeText(OpenTableActivity.this, R.string.error_list_dishes_failed, Toast.LENGTH_SHORT).show();
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
            public void onFinish() {
            }
            
            @Override
            public void onFailed(ApiException exception) {}
        });
    }
    
    //按下返回键弹出退出确认
    @Override
    public void onBackPressed() {
        if (getFragmentManager().findFragmentByTag(ExitOrderDialogFragment.class.getSimpleName()) == null) {
            ExitOrderDialogFragment dialog = new ExitOrderDialogFragment();
            dialog.show(getSupportFragmentManager(), ExitOrderDialogFragment.class.getSimpleName());
        } else {
            super.onBackPressed();
        }
    }
}