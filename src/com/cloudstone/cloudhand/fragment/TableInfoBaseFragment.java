package com.cloudstone.cloudhand.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.TableInfoActivity;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.dialog.ClearTableDialogFragment;
import com.cloudstone.cloudhand.dialog.OpenTableDialogFragment;
import com.cloudstone.cloudhand.pinyin.ContrastPinyin;
import com.cloudstone.cloudhand.view.TableItem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author xhc
 *
 */
public class TableInfoBaseFragment extends BaseFragment implements SearchView.OnQueryTextListener {
    private SearchView searchView;
    private ListView listTableInfo;
    private BaseAdapter adapter;
    
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("updateTableInfo")) {
                render();
            }
            if(intent.getAction().equals("tableInfoDismiss")) {
                getActivity().finish();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_table_info, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listTableInfo = (ListView)getView().findViewById(R.id.listview_table_info);
        searchView = (SearchView)getView().findViewById(R.id.searchview_table);
        searchView.setFocusable(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        
        listTableInfo.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int intPosition,
                    long longPosition) {
                Bundle bundle = new Bundle();
                if(getTables().get(intPosition).getStatus() == 0) {
                    OpenTableDialogFragment dialog = new OpenTableDialogFragment();
                    bundle.putInt("tableId", getTables().get(intPosition).getId());
                    dialog.setArguments(bundle);
                    dialog.show(getFragmentManager(), "openTableDialogFragment");
                    render();
                } else {
                    ClearTableDialogFragment dialog = new ClearTableDialogFragment();
                    bundle.putInt("tableId", getTables().get(intPosition).getId());
                    dialog.setArguments(bundle);
                    dialog.show(getFragmentManager(), "clearTableDialogFragment");
                }
            }
        });
    }
    
    private class InnerAdapter extends BaseAdapter {
        
        @Override
        public int getCount() {
            return searchItem(searchView.getQuery().toString()).size();
        }

        @Override
        public Table getItem(int position) {
            return (Table)searchItem(searchView.getQuery().toString()).get(position);
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
    
    @Override
    public boolean onQueryTextChange(String keywords) {
        render();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    
    //模糊搜索过滤出一个菜单结果
    private List<Table> searchItem(String keywords) {
        ContrastPinyin contrastPinyin = new ContrastPinyin();
        List<Table> data = new ArrayList<Table>();
        List<Table> tables = getTables();
        for (int i = 0; i < tables.size(); i++) {
            int index = 0;;
            if(contrastPinyin.isContain(keywords)) {
                index = tables.get(i).getName().indexOf(keywords);
            } else {
                String pinyin = contrastPinyin.getSpells(tables.get(i).getName());
                index = pinyin.indexOf(keywords.toLowerCase());
            }
            // 存在匹配的数据
            if (index != -1) {
                data.add(tables.get(i));
            }
        }
        return data;
    }
    
    protected List<Table> getTables() {
        List<Table> tables = new ArrayList<Table>();
        Iterator<Table> it = ((TableInfoActivity)(getActivity())).getTables().iterator();
        while (it.hasNext()) {
            Table table = it.next();
            if(getClass() == TableInfoEmptyFragment.class) {
                if(table.getStatus() == 0) {
                    tables.add(table);
                }
            } else if (getClass() == TableInfoOccupiedFragment.class) {
                if(table.getStatus() > 0) {
                    tables.add(table);
                }
            }
        }
        return tables;
    }
    
}
