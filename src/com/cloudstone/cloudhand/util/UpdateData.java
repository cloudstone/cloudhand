package com.cloudstone.cloudhand.util;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.asynctask.InsertDishNoteTask;
import com.cloudstone.cloudhand.asynctask.InsertDishTask;
import com.cloudstone.cloudhand.constant.BroadcastConst;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.logic.DishLogic;
import com.cloudstone.cloudhand.logic.DishNoteLogic;
import com.cloudstone.cloudhand.logic.MiscLogic;
import com.cloudstone.cloudhand.network.api.GetOrderApi;
import com.cloudstone.cloudhand.network.api.ListDishApi;
import com.cloudstone.cloudhand.network.api.ListDishNoteApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;

public class UpdateData {
    private Context context;
    private int orderId;
    
    private UpdateData(Context context) {
        this.context = context;
    }
    
    public UpdateData(Context context, int orderId) {
        this.context = context;
        this.orderId = orderId;
    }
    
    private int tableId;
    private String tableName;
    
    public UpdateData(Context context, int tableId, String tableName) {
        this.context = context;
        this.tableId = tableId;
        this.tableName = tableName;
    }
    
    public UpdateData(Context context, int orderId, int tableId, String tableName) {
        this.context = context;
        this.orderId = orderId;
        this.tableId = tableId;
        this.tableName = tableName;
    }
    /**
     * 更新数据
     */
    public void updateData() {
        if(GlobalValue.getIns() != null) {
            GlobalValue.getIns().getDishes().clear();
        }
        if(GlobalValue.getIns().getDishNotes() != null) {
            GlobalValue.getIns().getDishNotes().clear();
        }
        if(GlobalValue.getIns().getOrder() != null) {
            GlobalValue.getIns().getOrder().getDishes().clear();
            GlobalValue.getIns().setOrder(null);
        }
        
        if(MiscLogic.getInstance().getNoNet()) {
            List<Dish> dish = DishLogic.getInstance().getAllDish(context);
            Iterator<Dish> it = dish.iterator();
            while(it.hasNext()) {
                GlobalValue.getIns().getDishes().put(it.next());
            }
            GlobalValue.getIns().setDishNotes(DishNoteLogic.getInstance().getAllDishTable(context));
            Bundle bundle = new Bundle();
            bundle.putInt("orderId", orderId);
            bundle.putString("tableName", tableName);
            bundle.putInt("tableId", tableId);
            Intent submitIntent = new Intent();
            submitIntent.putExtras(bundle);
            submitIntent.setClass(context, OpenTableActivity.class);
            context.startActivity(submitIntent);
        } else {
            //获取菜单列表
            new ListDishApi().asyncCall(new IApiCallback<List<Dish>>() {
                
                @Override
                public void onSuccess(List<Dish> result) {
                    InsertDishTask insertDishTask = new InsertDishTask(context, result);
                    insertDishTask.execute();
                    Iterator<Dish> it = result.iterator();
                    while(it.hasNext()) {
                        GlobalValue.getIns().getDishes().put(it.next());
                    }
                    //发送更新菜单界面的广播
                    Intent intent = new Intent();
                    intent.setAction(BroadcastConst.INIT_OPEN_TABLE);
                    context.sendBroadcast(intent);
                }
                
                @Override
                public void onFinish() {
                }
                
                @Override
                public void onFailed(ApiException exception) {
                    Toast.makeText(context, R.string.error_list_dishes_failed, Toast.LENGTH_SHORT).show();
                    L.e(context, exception);
                }
            });
            
            //获取备注列表
            new ListDishNoteApi().asyncCall(new IApiCallback<List<DishNote>>() {
                
                @Override
                public void onSuccess(List<DishNote> result) {
                    InsertDishNoteTask insertDishTask = new InsertDishNoteTask(context, result);
                    insertDishTask.execute();
                    GlobalValue.getIns().setDishNotes(result);
                }
                
                @Override
                public void onFinish() {
                }
                
                @Override
                public void onFailed(ApiException exception) {}
            });
            
            //获取已下单
            new GetOrderApi(orderId).asyncCall(new IApiCallback<Order>() {
                
                @Override
                public void onSuccess(Order result) {
                    GlobalValue.getIns().setOrder(result);
                }
                
                @Override
                public void onFinish() {
                    Bundle bundle = new Bundle();
                    bundle.putInt("orderId", orderId);
                    bundle.putString("tableName", tableName);
                    bundle.putInt("tableId", tableId);
                    Intent intent = new Intent();
                    intent.setAction(BroadcastConst.DISMISS_PROGRESS_BAR);
                    context.sendBroadcast(intent);
                    Intent submitIntent = new Intent();
                    submitIntent.putExtras(bundle);
                    submitIntent.setClass(context, OpenTableActivity.class);
                    context.startActivity(submitIntent);
                }
                
                @Override
                public void onFailed(ApiException exception) {
                }
            });
        }
    }
}
