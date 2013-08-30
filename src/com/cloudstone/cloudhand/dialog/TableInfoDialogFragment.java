package com.cloudstone.cloudhand.dialog;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.ListTableApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;
import com.cloudstone.cloudhand.view.TableItem;

/**
 * 
 * @author xhc
 *
 */
public class TableInfoDialogFragment extends BaseAlertDialogFragment {
    private ListView listTableInfo;
    private BaseAdapter adapter;
    
    private List<Table> tables = new ArrayList<Table>();
    
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("updateTableInfo")) {
                render();
            }
            if(intent.getAction().equals("tableInfoDismiss")) {
                dismiss();
            }
        }
    };
    
    
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("updateTableInfo");
        filter.addAction("tableInfoDismiss");
        getActivity().registerReceiver(broadcastReceiver, filter);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
    
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
        listTableInfo.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v,
                    int intPosition, long longPosition) {
                Bundle bundle = new Bundle();
                if(tables.get(intPosition).getStatus() == 0) {
                    OpenTableDialogFragment dialog = new OpenTableDialogFragment();
                    bundle.putInt("tableId", tables.get(intPosition).getId());
                    dialog.setArguments(bundle);
                    dialog.show(getFragmentManager(), "openTableDialogFragment");
                    render();
                } else {
                    ClearTableDialogFragment dialog = new ClearTableDialogFragment();
                    bundle.putInt("tableId", tables.get(intPosition).getId());
                    dialog.setArguments(bundle);
                    dialog.show(getFragmentManager(), "clearTableDialogFragment");
                }
                return false;
            }
        });
        
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       render();
    }
    
    public void getTableInfo() {
        //获取桌况
        new ListTableApi().asyncCall(new IApiCallback<List<Table>>() {

            @Override
            public void onSuccess(List<Table> result) {
                tables = result;
                render();
            }

            @Override
            public void onFailed(ApiException exception) {
                Toast.makeText(getActivity(), R.string.error_list_table_info_failed, Toast.LENGTH_SHORT).show();
                L.e(TableInfoDialogFragment.class, exception);
            }

            @Override
            public void onFinish() {}

        });
    }
    
    private class InnerAdapter extends BaseAdapter {
        
        @Override
        public int getCount() {
            return tables.size();
        }

        @Override
        public Table getItem(int position) {
            return (Table)tables.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TableItem view = (TableItem) convertView;
            if (view == null) {
                view = createView();
            }
            Table table = getItem(position);
            view.render(table);
            bindView(view, position);
            return view;
        }
        
        private TableItem createView() {
            TableItem view = new TableItem(getActivity());
            return view;
        }
        
        private void bindView(TableItem view, int position) {
            Table table = getItem(position);
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                view.setTag(holder);
            }
            holder.setTable(table);
        }
        
    }
    
    private class ViewHolder {
        private Table table;

        public Table getTable() {
            return table;
        }

        public void setTable(Table table) {
            this.table = table;
        }
        
    }
    
    private void render() {
        getTableInfo();
        adapter = new InnerAdapter(); 
        listTableInfo.setAdapter(adapter);
    }
    
}