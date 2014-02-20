package com.cloudstone.cloudhand.activity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckedTextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.dialog.ExitOrderDialogFragment;
import com.cloudstone.cloudhand.fragment.OpenTableOrderFragment;
import com.cloudstone.cloudhand.fragment.OpenTableOrderedFragment;
import com.cloudstone.cloudhand.fragment.OpenTableSubmittedFragment;

/**
 * 
 * @author xhc
 *
 */
public class OpenTableActivity extends ViewPagerBaseActivity {
    
    private int tableId;
    private int customerNumber;
    private int orderId;
    private CheckedTextView thirdTitle;
    
//    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if(intent.getAction().equals(BroadcastConst.INIT_VIEW_PAGE)) {
//                initTextView(3);
//                initViewPager();
//            }
//        }
//    };
//    
//    @Override
//    public void onResume() {
//        super.onResume();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(BroadcastConst.INIT_VIEW_PAGE);
//        this.registerReceiver(broadcastReceiver, filter);
//    }
    
    public int getTableId() {
        return tableId;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }
    
    //用于记录每样菜点了几份
    private Map<Integer, Integer> dishCountMap = new HashMap<Integer, Integer>();
    //用于记录每样菜选择了哪些备注
    private Map<Integer, Set<Integer>> dishNoteMap = new HashMap<Integer, Set<Integer>>();
    
    //getter and setter
    public int getDishCount(int dishId) {
        if (!dishCountMap.containsKey(dishId)) {
            return 0;
        }
        return dishCountMap.get(dishId);
    }
    
    public void setDishCount(int dishId, int count) {
        dishCountMap.put(dishId, count);
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
        fragmentList.clear();
        fragmentList.add(new OpenTableOrderFragment());
        fragmentList.add(new OpenTableOrderedFragment());
//        if(GlobalValue.getIns().getOrder() != null) {
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
        initTextView(3);
        initViewPager();
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