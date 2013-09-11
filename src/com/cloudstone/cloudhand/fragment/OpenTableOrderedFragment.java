package com.cloudstone.cloudhand.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.data.Dish;

public class OpenTableOrderedFragment extends OpenTableBaseFragment {
    private TextView totalPriceView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_open_table_ordered, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        totalPriceView = (TextView)getView().findViewById(R.id.tv_total_price);
    }
    
    private void renderTotalPrice() {
        double totalPrice = getTotalPrice();
        totalPriceView.setText(totalPrice + "");
    }
    
    @Override
    protected List<Dish> getDishes() {
        List<Dish> data = new ArrayList<Dish>();
        List<Dish> dishes = ((OpenTableActivity)(getActivity())).getDishes();
        for(int i = 0; i < dishes.size(); i++) {
            if(getDishCount(dishes.get(i).getId()) > 0) {
                data.add(dishes.get(i));
            }
        }
        return data;
    }
    @Override
    protected void render() {
        adapter = new InnerAdapter();
        dishListView.setAdapter(adapter);
        renderTotalPrice();
    }
    
}
