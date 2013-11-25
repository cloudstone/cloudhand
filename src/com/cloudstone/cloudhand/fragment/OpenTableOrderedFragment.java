package com.cloudstone.cloudhand.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.cloudstone.cloudhand.activity.MainActivity;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.activity.TableInfoActivity;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.data.OrderDish;
import com.cloudstone.cloudhand.dialog.BaseDialog;
import com.cloudstone.cloudhand.dialog.DeleteDishDialogFragment;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.logic.UserLogic;
import com.cloudstone.cloudhand.network.api.SubmitOrderAgainApi;
import com.cloudstone.cloudhand.network.api.SubmitOrderApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.DishBag;
import com.cloudstone.cloudhand.util.L;

public class OpenTableOrderedFragment extends OpenTableBaseFragment {
    private TextView totalPriceView;
    private Button btnSubmit;
    private TextView tvTableName;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_open_table_ordered, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvTableName = (TextView)getView().findViewById(R.id.tv_table_name);
        tvTableName.setText(((OpenTableActivity)(getActivity())).getIntent().getStringExtra("tableName"));
        
        //换桌
        tvTableName.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TableInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("tabNumber", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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
                Submit();
            }
        });
    }
    
    //下单
    private void Submit() {
        BaseDialog dialog = new BaseDialog(getActivity());
        dialog.setIcon(R.drawable.ic_ask);
        dialog.setMessage(R.string.message_submit);
        dialog.addButton(R.string.confirm, new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                    Order order;
                    //根据是否有已下单选项卡执行初次提交订单或者加菜
                    if(((OpenTableActivity)(getActivity())).getFlag()) {
                        order = ((OpenTableActivity)(getActivity())).getOrder();
                        order.setDishes(orderDishList);
                        SubmitOrderAgainApi submit = new SubmitOrderAgainApi(order);
                        submit.asyncCall(new IApiCallback<Order>() {
                            
                            @Override
                            public void onSuccess(Order result) {
                                //弹出下单成功对话框
                                BaseDialog dialog = new BaseDialog(getActivity());
                                dialog.setIcon(R.drawable.ic_success);
                                dialog.setMessage(R.string.submit_success);
                                dialog.addButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                    
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getActivity().finish();
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
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
                    } else {
                        order = new Order();
                        order.setUserId(UserLogic.getInstance().getUser().getId());
                        order.setTableId(((OpenTableActivity)(getActivity())).getTableId());
                        order.setCustomerNumber(((OpenTableActivity)(getActivity())).getCustomerNumber());
                        order.setDishes(orderDishList);
                        SubmitOrderApi submit = new SubmitOrderApi(order);
                        submit.asyncCall(new IApiCallback<Order>() {
                            
                            @Override
                            public void onSuccess(Order result) {
                                //弹出下单成功对话框
                                BaseDialog dialog = new BaseDialog(getActivity());
                                dialog.setIcon(R.drawable.ic_success);
                                dialog.setMessage(R.string.submit_success);
                                dialog.addButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                    
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getActivity().finish();
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
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
    }
    
    @Override
    protected void renderTotalPrice() {
        double totalPrice = getTotalPrice();
        totalPriceView.setText(totalPrice + "");
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
