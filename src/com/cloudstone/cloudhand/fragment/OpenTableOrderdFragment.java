package com.cloudstone.cloudhand.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.data.OrderDish;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.SubmitOrderApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;

public class OpenTableOrderdFragment extends BaseFragment {
    private List<Dish> data = new ArrayList<Dish>();
    private TextView totalPriceView;
    
    private Button btnSubmit;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_open_table_orderd, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        totalPriceView = (TextView)getView().findViewById(R.id.tv_total_price);
        
        btnSubmit = (Button)getView().findViewById(R.id.btn_submit_order_confirm);
        
        btnSubmit.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                OrderDish od1 = new OrderDish();
                od1.setId(1);
                od1.setOrderId(1);
                od1.setNumber(2);
//                od1.setRemarks(new String[]{"aa", "bb"});
                OrderDish od2 = new OrderDish();
                od2.setId(2);
                od2.setOrderId(2);
                od2.setNumber(2);
//                od2.setRemarks(new String[]{"aa", "bb"});
                
                List<OrderDish> list = new ArrayList<OrderDish>();
                list.add(od1);
                list.add(od2);
                
                Order order = new Order();
                order.setTableId(2);
                order.setUserId(2);
                order.setCustomerNumber(3);
                order.setDishes(list);
                
                SubmitOrderApi submit = new SubmitOrderApi(order);
                
                
                submit.asyncCall(new IApiCallback<Order>() {
                    
                    @Override
                    public void onSuccess(Order result) {
                        L.i(OpenTableOrderdFragment.class, "onSuccess");
                        
                    }
                    
                    @Override
                    public void onFinish() {
                    	L.i(OpenTableOrderdFragment.class, "onFinish");
                        
                    }
                    
                    @Override
                    public void onFailed(ApiException exception) {
                    	L.i(OpenTableOrderdFragment.class, "onFailed");
                        
                    }
                });
            }

        });
    }
    
    private void initData() {
        data.clear();
        for(int i = 0; i < ((OpenTableActivity)(getActivity())).getDishes().size(); i++) {
            if(getDishCount(i) > 0) {
                data.add(((OpenTableActivity)(getActivity())).getDishes().get(i));
            }
        }
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
            int count = ((OpenTableActivity)(getActivity())).getDishCountMap().get(i);
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
    protected void render() {
        initData();
        adapter = new InnerAdapter(data);
        listView.setAdapter(adapter);
        renderTotalPrice();
    }
    
}
