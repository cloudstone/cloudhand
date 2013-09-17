package com.cloudstone.cloudhand.fragment;

import java.util.Iterator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.dialog.DeleteDishDialogFragment;
import com.cloudstone.cloudhand.util.DishBag;

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
        //长按弹出删除一道菜对话框
        dishListView.setOnItemLongClickListener(new OnItemLongClickListener() {
            
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int intPosition,
                    long longPosition) {
                int deleteDishId = getDishes().getByPos(intPosition).getId();
                
                DeleteDishDialogFragment dialog = new DeleteDishDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("dishId", deleteDishId);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "deleteDishDialogFragment");

                return false;
            }
        });
    }
    
    private void renderTotalPrice() {
        double totalPrice = getTotalPrice();
        totalPriceView.setText(totalPrice + "");
    }
    
    @Override
    protected void render() {
        super.render();
        renderTotalPrice();
    }
    
    @Override
    protected DishBag filter(DishBag dishes) {
        DishBag data = new DishBag();
        Iterator<Dish> it = dishes.iterator();
        while(it.hasNext()) {
            Dish dish = it.next();
            if(getDishCount(dish.getId()) > 0) {
                data.put(dish);
            }
        }
        return data;
    }
    
}
