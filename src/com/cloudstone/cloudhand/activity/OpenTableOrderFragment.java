package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.ListDishApi;
import com.cloudstone.cloudhand.network.api.ListTableApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.pinyin.ContrastPinyin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

public class OpenTableOrderFragment extends Fragment implements SearchView.OnQueryTextListener {
	private ListView listView;
    private List<Map<String, String>> data = new ArrayList<Map<String,String>>();
    private SimpleAdapter adapter;
    
    private SearchView searchView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	
    	new ListDishApi().asyncCall(new IApiCallback<List<Dish>>() {
			
			@Override
			public void onSuccess(List<Dish> result) {
				System.out.println("onSuccess");
				for(int i = 0;i < result.size();i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("dishName", result.get(i).getName());
                    map.put("price", result.get(i).getPrice() + "");
                    map.put("count", "0");
                    data.add(map);
                }
				System.out.println(data.size());
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				System.out.println("onFinish");
				
			}
			
			@Override
			public void onFailed(ApiException exception) {
				// TODO Auto-generated method stub
				System.out.println("onFailed");
			}
		});
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.view_open_table_tab1, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		searchView = (SearchView)getView().findViewById(R.id.searchview_dish);
		
		listView = (ListView)getView().findViewById(R.id.listView_dish);
        adapter = new SimpleAdapter(getActivity(), data, R.layout.view_dish_item, 
                new String[] {"dishName", "price", "count", "incr", "decr"}, 
                new int[]{R.id.text_dish_name, R.id.text_price, R.id.text_count, R.id.button_incr, R.id.button_decr});
        listView.setAdapter(adapter);
        
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
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
            String pinyin = cp.getSpells(data.get(i).get("dishName"));
            int index2 = pinyin.indexOf(name);
            // 存在匹配的数据
            if (index2 != -1) {
                mSearchList.add(data.get(i));
           }
        }
        return mSearchList;
    }
    
    public void updateLayout(List<Map<String, String>> obj) {
        listView.setAdapter(new SimpleAdapter(getActivity(), obj, R.layout.view_dish_item, 
        		new String[] {"dishName", "price", "count", "incr", "decr"}, 
                new int[]{R.id.text_dish_name, R.id.text_price, R.id.text_count, R.id.button_incr, R.id.button_decr}));
    }

}
