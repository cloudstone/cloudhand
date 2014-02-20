package com.cloudstone.cloudhand.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
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

public class GlobalValue {
    private static GlobalValue mInstance;
    private Context context;
    private int orderId;

    public static GlobalValue getIns() {
        if (mInstance == null)
            mInstance = new GlobalValue();
        return mInstance;
    }
    public GlobalValue() {
        
    }
    public GlobalValue(Context context) {
        this.context = context;
    }
    
    public GlobalValue(Context context, int orderId) {
        this.context = context;
        this.orderId = orderId;
    }
    
    //菜品列表数据
    private DishBag dishes = new DishBag();
    //菜品备注列表数据
    private List<DishNote> dishNotes = new ArrayList<DishNote>();
    //已下单
    private Order order;

    public DishBag getDishes() {
        return dishes;
    }
    public void setDishes(DishBag dishes) {
        this.dishes = dishes;
    }
    public List<DishNote> getDishNotes() {
        return dishNotes;
    }
    public void setDishNotes(List<DishNote> dishNotes) {
        this.dishNotes = dishNotes;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    
}
