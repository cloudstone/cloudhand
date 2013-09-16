package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
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
    //用于桌子列表的数据
    private List<Table> tables = new ArrayList<Table>();
    
    public List<Table> getTables() {
        return tables;
    }
    
    @Override
    protected void initViewPager() {
        super.initViewPager();
        fragmentList.add(new TableInfoEmptyFragment());
        fragmentList.add(new TableInfoOccupiedFragment());
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
                L.e(TableInfoActivity.class, exception);
            }

            @Override
            public void onFinish() {
            }

        });
    }
    
}
