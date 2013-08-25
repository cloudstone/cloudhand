package com.cloudstone.cloudhand.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.data.Dish;

public class OpenTableOrderdFragment extends BaseFragment {
    private List<Dish> data = new ArrayList<Dish>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_open_table_orderd, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView)getView().findViewById(R.id.lv_dish);
    }
    
    private void initData() {
        data.clear();
        for(int i = 0; i < ((OpenTableActivity)(getActivity())).getData().size(); i++) {
            if(getDishCount(i) > 0) {
                data.add(((OpenTableActivity)(getActivity())).getData().get(i));
            }
        }
    }
    
    @Override
    protected void render() {
        initData();
        adapter = new InnerAdapter(data);
        listView.setAdapter(adapter);
    }
    
    @Override
    protected void update() {
        Intent intent = new Intent();
        intent.setAction("update");
        getActivity().sendBroadcast(intent);
    }
    
}
