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

public class OpenTableOrderdFragment extends OpenTableBaseFragment {
    private TextView totalPriceView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_open_table_orderd, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        totalPriceView = (TextView)getView().findViewById(R.id.tv_total_price);
    }
    
    private Dish getDish(int dishId) {
        List<Dish> dishes =((OpenTableActivity)(getActivity())).getDishes();
        if (dishes != null) {
            for (Dish dish:dishes) {
                if (dish.getId() == dishId) {
                    return dish;
                }
            }
        }
        return null;
    }
    
    private double getTotalPrice() {
        double total = 0;
        for (int i = 0;i < ((OpenTableActivity)(getActivity())).getDishCountMap().size();i++) {
            int count = getDishCount(i);
            if (count > 0) {
                Dish dish = getDish(i);
                total += dish.getPrice() * count;
            }
        }
        return total;
    }
    
    private void renderTotalPrice() {
        double totalPrice = getTotalPrice();
        totalPriceView.setText(totalPrice + "");
    }
    
    @Override
    protected List<Dish> getDishes() {
        List<Dish> data = new ArrayList<Dish>();
        for(int i = 0; i < ((OpenTableActivity)(getActivity())).getDishes().size(); i++) {
            if(getDishCount(i) > 0) {
                data.add(((OpenTableActivity)(getActivity())).getDishes().get(i));
            }
        }
        return data;
    }
    @Override
    protected void render() {
        adapter = new InnerAdapter();
        listView.setAdapter(adapter);
        renderTotalPrice();
    }
    
}
