package com.cloudstone.cloudhand.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.dialog.BaseDialog;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.data.OrderDish;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.logic.UserLogic;
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
                final int deleteDishId = getDishes().get(intPosition).getId();
                BaseDialog dialog = new BaseDialog(getActivity());
                dialog.setIcon(R.drawable.ic_ask);
                dialog.setMessage(R.string.message_delete_dish);
                dialog.addButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除一道菜后刷新界面
                        ((OpenTableActivity)(getActivity())).setDishCount(deleteDishId, 0);
                        Intent intent = new Intent();
                        intent.setAction("update");
                        getActivity().sendBroadcast(intent);
                        dialog.dismiss();
                    }
                });
                dialog.addButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                
                return false;
            }
        });
                
        btnSubmit = (Button)getView().findViewById(R.id.btn_submit_order_confirm);
        
        btnSubmit.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                List<OrderDish> orderDishList = new ArrayList<OrderDish>();
                List<Dish> dishList = getDishes();
                OrderDish orderDish;
                for(int i = 0; i < dishList.size(); i++) {
                    orderDish = new OrderDish();
                    Dish dish = dishList.get(i);
                    orderDish.setOrderId(dish.getId());
                    orderDish.setNumber(getDishCount(dish.getId()));
                    orderDishList.add(orderDish);
                }
                
                //TODO 这里缺少备注，桌字Id和人数暂时写死，也没有开台成功对话框，merge就有了。
                Order order = new Order();
                order.setUserId(UserLogic.getInstance().getUser().getId());
                order.setTableId(3);
                order.setCustomerNumber(3);
                order.setDishes(orderDishList);
                
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
                        L.e(this, exception);
                        Toast.makeText(getActivity(), R.string.submit_failed, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
