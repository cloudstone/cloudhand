package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.pinyin.ContrastPinyin;

@SuppressLint("ValidFragment")
public class OpenTable_Tab1 extends BaseFragment implements SearchView.OnQueryTextListener {
	
    private ListView listView;
    private List<Map<String, String>> data;
    private SimpleAdapter adapter;
    
    private SearchView searchView;
    
    public OpenTable_Tab1() {}
    
    public OpenTable_Tab1(List<Map<String, String>> data) {
		this.data = data;
		System.out.println(data.get(0).get("amount"));
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        data = getData();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_opentable_tab1, container, false);
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	OpenTableActivity activity = (OpenTableActivity)getActivity();
    	System.out.println("this  -->" + this.data.get(0).get("amount"));
    	System.out.println("activity  -->" + activity.data.get(0).get("amount"));
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        
//        btnADD = (Button)getView().findViewById(R.id.adapter_add);
        
        searchView = (SearchView)getView().findViewById(R.id.search_view);
        listView = (ListView)getView().findViewById(R.id.listView);
        adapter = new SimpleAdapter(getActivity(), data, R.layout.listview, 
                new String[] {"name", "price", "amount", "add", "sub"}, 
                new int[]{R.id.adapter_name, R.id.adapter_price, R.id.adapter_amount, R.id.adapter_add, R.id.adapter_sub});
        listView.setAdapter(adapter);
        
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println(listView.getSelectedItem().getClass().toString());
				
			}
		});
        
        SimpleAdapter.ViewBinder binder = new SimpleAdapter.ViewBinder() {
        	List<Map<String, String>> data2 = data;
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
            	
                if (view instanceof Button) {
                    final View button = view;
                    view.setOnClickListener(new OnClickListener() {
                        LinearLayout listItem = (LinearLayout) button.getParent().getParent();
                        TextView tv = (TextView)listItem.findViewById(R.id.adapter_amount);
                        @Override
                        public void onClick(View v) {
                        	int amount = Integer.parseInt(tv.getText().toString());
                        	
                        	if(button.getId() == R.id.adapter_add) {
                        		tv.setText(++amount + "");
                        		System.out.println("getTextAlignment -->" + listView.getSelectedItemPosition());
                        	} else {
                        		if(amount > 0) {
                        			tv.setText(--amount + "");
                        		}
                        	}
//                        	listItem
//                        	data2.get(tv.getImeOptions()).put("amount", tv.getText().toString());
                        	System.out.println("item -->" + button);
                        }
                    });
                    return false;
                }
                return false;
            }
        };
        adapter.setViewBinder(binder);
        
//        btnADD.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				btnADD.setText("add");
//			}
//		});
    }
    
    

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Map<String, String>>  obj = searchItem(newText);
        updateLayout(obj);
        return false;
  }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }
    
    public List<Map<String, String>> searchItem(String name) {
        ContrastPinyin cp = new ContrastPinyin();
        List<Map<String, String>> mSearchList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < data.size(); i++) {
            String pinyin = cp.getSpells(data.get(i).get("name"));
            int index2 = pinyin.indexOf(name);
            // 存在匹配的数据
            if (index2 != -1) {
                mSearchList.add(data.get(i));
           }
        }
        return mSearchList;
    }
    
    public void updateLayout(List<Map<String, String>> obj) {
        listView.setAdapter(new SimpleAdapter(getActivity(), obj, R.layout.listview, 
                new String[] {"name", "price", "amount"}, 
                new int[]{R.id.adapter_name, R.id.adapter_price, R.id.adapter_amount}));
    }
    

}
