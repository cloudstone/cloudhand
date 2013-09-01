package com.cloudstone.cloudhand.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.dialog.DeleteDishDialogFragment;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.data.OrderDish;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.SubmitOrderApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;

public class OpenTableOrderdFragment extends OpenTableBaseFragment {
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
        
        //长按弹出删除一道菜对话框
        dishListView.setOnItemLongClickListener(new OnItemLongClickListener() {
            
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int intPosition,
                    long longPosition) {
                int deleteDishId = getDishes().get(intPosition).getId();
                
                DeleteDishDialogFragment dialog = new DeleteDishDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("dishId", deleteDishId);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "deleteDishDialogFragment");

                return false;
            }
        });
                
        btnSubmit = (Button)getView().findViewById(R.id.btn_submit_order_confirm);
        
        btnSubmit.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                OrderDish od1 = new OrderDish();
                od1.setId(1);
                od1.setOrderId(1);
                od1.setNumber(2);
//                od1.setRemarks(new String[]{"暂无备注"});
                OrderDish od2 = new OrderDish();
                od2.setId(2);
                od2.setOrderId(2);
                od2.setNumber(2);
//                od2.setRemarks(new String[]{"暂无备注"});
                
                List<OrderDish> list = new ArrayList<OrderDish>();
                list.add(od1);
                list.add(od2);
                
                Order order = new Order();
                order.setTableId(5);
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
    
//    private void initData() {
//        data.clear();
//        for(int i = 0; i < ((OpenTableActivity)(getActivity())).getDishes().size(); i++) {
//            if(getDishCount(i) > 0) {
//                data.add(((OpenTableActivity)(getActivity())).getDishes().get(i));
//            }
//        });
//    }
    
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
        for (int i = 0;i < getDishes().size();i++) {
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
        List<Dish> dishes = ((OpenTableActivity)(getActivity())).getDishes();
        for(int i = 0; i < dishes.size(); i++) {
            if(getDishCount(i) > 0) {
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
