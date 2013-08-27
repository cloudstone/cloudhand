package com.cloudstone.cloudhand.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.ListTableApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;

/**
 * 
 * @author xhc
 *
 */
public class TableInfoDialogFragment extends BaseAlertDialogFragment {
    private ListView listTableInfo;
    private SimpleAdapter adapter;
    
    private List<Map<String, String>> tables = new ArrayList<Map<String,String>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_table_info, container, false);
        listTableInfo = (ListView)view.findViewById(R.id.listview_table_info);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
      //获取桌况
        new ListTableApi().asyncCall(new IApiCallback<List<Table>>() {

            @Override
            public void onSuccess(List<Table> result) {
                for(int i = 0;i < result.size();i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("tableName", result.get(i).getName());
                    if(result.get(i).getStatus() == 0) {
                        map.put("tableStatus", getString(R.string.empty));
                    } else if (result.get(i).getStatus() == 1) {
                        map.put("tableStatus", getString(R.string.occupied));
                    } else {
                        map.put("tableStatus", getString(R.string.ordered));
                    }
                    tables.add(map);
                }
                render();
            }

            @Override
            public void onFailed(ApiException exception) {
                Toast.makeText(getActivity(), R.string.error_list_table_info_failed, Toast.LENGTH_SHORT).show();
                L.e(TableInfoDialogFragment.class, exception);
            }

            @Override
            public void onFinish() {
            }

        });
    }
    
    private void render() {
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), tables,R.layout.view_table_info_dropdown_list_line, 
                new String[] {"tableName", "tableStatus"}, 
                new int[]{R.id.tv_table_name, R.id.tv_table_status});
        listTableInfo.setAdapter(adapter);
    }
    
}