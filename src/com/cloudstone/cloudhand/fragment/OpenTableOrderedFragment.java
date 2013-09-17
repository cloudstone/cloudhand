package com.cloudstone.cloudhand.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.data.OrderDish;
import com.cloudstone.cloudhand.dialog.DeleteDishDialogFragment;
import com.cloudstone.cloudhand.dialog.SubmitSuccessDialogFragment;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.logic.UserLogic;
import com.cloudstone.cloudhand.network.api.SubmitOrderApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.DishBag;
import com.cloudstone.cloudhand.util.L;

public class OpenTableOrderedFragment extends OpenTableBaseFragment {
    private TextView totalPriceView;
    private Button btnSubmit;
    
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
        
        btnSubmit = (Button)getView().findViewById(R.id.btn_submit_order_confirm);
        
        //下单
        btnSubmit.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                List<OrderDish> orderDishList = new ArrayList<OrderDish>();
                DishBag dishes = getDishes();
                OrderDish orderDish;
                for(int i = 0; i < dishes.size(); i++) {
                    orderDish = new OrderDish();
                    Dish dish = dishes.getByPos(i);
                    orderDish.setId(dish.getId());
                    orderDish.setNumber(getDishCount(dish.getId()));
                    
                    Set<Integer> dishNoteIdSet = ((OpenTableActivity)(getActivity())).getDishNoteIdSet(dish.getId());
                    if(dishNoteIdSet.size() > 0) {
                        List<DishNote> dishNotes = ((OpenTableActivity)(getActivity())).getDishNotes();
                        String[] remarks = new String[dishNoteIdSet.size()];
                        int k = 0;
                        
                        Iterator<Integer> iterator = dishNoteIdSet.iterator();
                        while(iterator.hasNext()){
                            int next = iterator.next();
                            for(int j = 0; j < dishNotes.size(); j++) {
                                if(dishNotes.get(j).getId() == next) {
                                    remarks[k] = dishNotes.get(j).getName();
                                    k++;
                                }
                            }
                        }
                        orderDish.setRemarks(remarks);
                    }
                    orderDishList.add(orderDish);
                }
                    
                if(orderDishList.size() == 0) {
                    Toast.makeText(getActivity(), R.string.no_order, Toast.LENGTH_SHORT).show();
                } else {
                    Order order = new Order();
                    order.setUserId(UserLogic.getInstance().getUser().getId());
                    order.setTableId(((OpenTableActivity)(getActivity())).getTableId());
                    order.setCustomerNumber(((OpenTableActivity)(getActivity())).getCustomerNumber());
                    order.setDishes(orderDishList);
                    
                    SubmitOrderApi submit = new SubmitOrderApi(order);
                    
                    submit.asyncCall(new IApiCallback<Order>() {
                        
                        @Override
                        public void onSuccess(Order result) {
                            SubmitSuccessDialogFragment dialog = new SubmitSuccessDialogFragment();
                            dialog.show(getFragmentManager(), "submitSuccessDialogFragment");
                        }
                        
                        @Override
                        public void onFinish() {
                            L.i(OpenTableOrderedFragment.class, "onFinish");
                        }
                        
                        @Override
                        public void onFailed(ApiException exception) {
                            L.i(OpenTableOrderedFragment.class, "onFailed");
                            L.e(this, exception);
                            Toast.makeText(getActivity(), R.string.submit_failed, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
