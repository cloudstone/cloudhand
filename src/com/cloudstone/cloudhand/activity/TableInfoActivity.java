package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.List;

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
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.fragment.TableInfoEmptyFragment;
import com.cloudstone.cloudhand.fragment.TableInfoOccupiedFragment;
import com.cloudstone.cloudhand.network.api.ListTableApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;

/**
 * 
 * @author xhc
 *
 */
public class TableInfoActivity extends ViewPagerBaseActivity {
    private int tabNumber;
    private CheckedTextView secondTitle;
    
    public int getTabNumber() {
        return tabNumber;
    }
	
    //用于桌子列表的数据
    private List<Table> tables = new ArrayList<Table>();
    
    public List<Table> getTables() {
        return tables;
    }
    
    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BroadcastConst.UPDATE_TABLES)) {
                updateTables();
            }
        }
    };
    
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastConst.UPDATE_TABLES);
        this.registerReceiver(broadcastReceiver, filter);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);
    }
    
    @Override
    protected void initViewPager() {
        super.initViewPager();
        fragmentList.add(new TableInfoEmptyFragment());
        if(tabNumber > 1) {
            fragmentList.add(new TableInfoOccupiedFragment());
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_table_info);
        Intent intent = getIntent();
        tabNumber = intent.getIntExtra("tabNumber", 2);
        secondTitle = (CheckedTextView) findViewById(R.id.tv_secondTitle);
        if(tabNumber < 2) {
            secondTitle.setVisibility(View.GONE);
        }
        initTextView(tabNumber);
        initViewPager();
        updateTables();
    }
    
    public void updateTables() {
        new ListTableApi().asyncCall(new IApiCallback<List<Table>>() {

            @Override
            public void onSuccess(List<Table> result) {
                Intent intent = new Intent();
                if(tables.isEmpty()) {
                    tables = result;
                    intent.setAction(BroadcastConst.INIT_TABLE_INFO);
                } else {
                    tables = result;
                    intent.setAction(BroadcastConst.UPDATE_TABLE_INFO);
                }
                TableInfoActivity.this.sendBroadcast(intent);
            }

            @Override
            public void onFailed(ApiException exception) {
                Toast.makeText(TableInfoActivity.this, R.string.error_list_table_info_failed, Toast.LENGTH_SHORT).show();
                L.e(TableInfoActivity.class, exception);
            }

            @Override
            public void onFinish() {
            }

        });
    }
    
}
