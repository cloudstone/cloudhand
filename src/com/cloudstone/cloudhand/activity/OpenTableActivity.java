package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.constant.BroadcastConst;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.dialog.ExitOrderDialogFragment;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.fragment.OpenTableOrderFragment;
import com.cloudstone.cloudhand.fragment.OpenTableOrderedFragment;
import com.cloudstone.cloudhand.fragment.OpenTableSubmittedFragment;
import com.cloudstone.cloudhand.logic.DishLogic;
import com.cloudstone.cloudhand.logic.DishNoteLogic;
import com.cloudstone.cloudhand.logic.MiscLogic;
import com.cloudstone.cloudhand.network.api.GetOrderApi;
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
    
    private int tableId;
    private int customerNumber;
    private int orderId;
    private Order order;
    private boolean flag; //是否下过单
    private CheckedTextView thirdTitle;
    
//    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if(intent.getAction().equals(BroadcastConst.UPDATE_TABLES)) {
//                updateTables();
//            }
//        }
//    };
//    
//    @Override
//    public void onResume() {
//        super.onResume();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(BroadcastConst.UPDATE_TABLES);
//        this.registerReceiver(broadcastReceiver, filter);
//    }
    
    public int getTableId() {
        return tableId;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public boolean getFlag() {
        return flag;
    }
    
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
//        if(flag) {
//            fragmentList.add(new OpenTableSubmittedFragment());
//        }
        fragmentList.add(new OpenTableSubmittedFragment());
        
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_open_table);
        thirdTitle = (CheckedTextView) findViewById(R.id.tv_thirdTitle);
        
        Intent intent = getIntent();
        tableId = intent.getIntExtra("tableId", 0);
        customerNumber = intent.getIntExtra("customerNumber", 0);
        orderId = intent.getIntExtra("orderId", 0);
        flag = intent.getBooleanExtra("flag", false);
//        if(!flag) {
//            thirdTitle.setVisibility(View.GONE);
//        }
        
        initTextView(3);
        initViewPager();
        updateDish();
    }
    
    private void updateDish() {
        if(MiscLogic.getInstance().getNoNet()) {
            int dishSize = DishLogic.getInstance().getAllDish(OpenTableActivity.this).size();
            List<Dish> dish = DishLogic.getInstance().getAllDish(OpenTableActivity.this);
            for(int i = 0;i < dishSize;i++) {
                dishes.put(dish.get(i));
            }
            dishNotes = DishNoteLogic.getInstance().getAllDishTable(OpenTableActivity.this);
            //发送更新菜单界面的广播
            Intent intent = new Intent();
            intent.setAction(BroadcastConst.INIT_OPEN_TABLE);
            OpenTableActivity.this.sendBroadcast(intent);
        } else {
            //获取菜单列表
            new ListDishApi().asyncCall(new IApiCallback<List<Dish>>() {
                
                @Override
                public void onSuccess(List<Dish> result) {
                    DishLogic.getInstance().insertDish(OpenTableActivity.this, result);
                    for(int i = 0;i < result.size();i++) {
                        dishes.put(result.get(i));
                    }
                    //发送更新菜单界面的广播
                    Intent intent = new Intent();
                    intent.setAction(BroadcastConst.INIT_OPEN_TABLE);
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
                    DishNoteLogic.getInstance().insertDishNote(OpenTableActivity.this, result);
                    dishNotes = result;
                }
                
                @Override
                public void onFinish() {
                }
                
                @Override
                public void onFailed(ApiException exception) {}
            });
            
            new GetOrderApi(orderId).asyncCall(new IApiCallback<Order>() {
                
                @Override
                public void onSuccess(Order result) {
                    order = result;
                }
                
                @Override
                public void onFinish() {
                }
                
                @Override
                public void onFailed(ApiException exception) {
                }
            });
        }
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