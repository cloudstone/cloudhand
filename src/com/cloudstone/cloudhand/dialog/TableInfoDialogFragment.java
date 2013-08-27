package com.cloudstone.cloudhand.dialog;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                    tables.add(result.get(i));
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
    
    private class InnerAdapter extends BaseAdapter {
        
        List<Table> data = new ArrayList<Table>();
        
        public InnerAdapter() {}
        
        public InnerAdapter(List<Table> data) {
            this.data = data;
        }

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
        adapter = new InnerAdapter(); 
        listTableInfo.setAdapter(adapter);
    }
    
}