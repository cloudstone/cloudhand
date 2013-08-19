package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.ListTableApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * 
 * @author xhc
 *
 */
public class TableInfoActivity extends BaseActivity {
    
    ListView lvTableInfo;
    SimpleAdapter adapter;
    
    private List<Map<String, String>> data = new ArrayList<Map<String,String>>();
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_info);
        
        lvTableInfo = (ListView)findViewById(R.id.listview_table_info);
        
        //获取桌况
        new ListTableApi().asyncCall(new IApiCallback<List<Table>>() {

            @Override
            public void onSuccess(List<Table> result) {
                for(int i = 0;i < result.size();i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("tableName", result.get(i).getName());
                    if(result.get(i).getStatus() == 0) {
                        map.put("tableStatus", "空闲");
                    } else if (result.get(i).getStatus() == 1) {
                        map.put("tableStatus", "已用");
                    } else {
                        map.put("tableStatus", "已下单");
                    }
                    data.add(map);
                }
                render();
            }

            @Override
            public void onFailed(ApiException exception) {
                System.out.println("onFailed");
                Toast.makeText(TableInfoActivity.this, R.string.error_list_table_info_failed, 0).show();
                L.e(TableInfoActivity.class, exception);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
            }

        });
        
    }
    
    private void render() {
        SimpleAdapter adapter = new SimpleAdapter(this, data,R.layout.view_table_info_item, 
                new String[] {"tableName", "tableStatus"}, 
                new int[]{R.id.text_table_name, R.id.text_table_status});
        lvTableInfo.setAdapter(adapter);
    }

}