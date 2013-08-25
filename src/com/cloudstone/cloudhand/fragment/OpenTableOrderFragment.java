package com.cloudstone.cloudhand.fragment;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class OpenTableOrderFragment extends BaseFragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_open_table_order, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView)getView().findViewById(R.id.lv_dish);
    }
    
    @Override
    protected void render() {
        adapter = new InnerAdapter(((OpenTableActivity)(getActivity())).getData());
        listView.setAdapter(adapter);
    }
    
    @Override
    protected void update() {
        Intent intent = new Intent();
        intent.setAction("update");
        getActivity().sendBroadcast(intent);
    }
    
}