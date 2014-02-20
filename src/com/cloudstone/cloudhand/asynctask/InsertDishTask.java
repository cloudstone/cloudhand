package com.cloudstone.cloudhand.asynctask;

import java.util.List;

import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.logic.DishLogic;

import android.content.Context;
import android.os.AsyncTask;

public class InsertDishTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private List<Dish> dish;
    
    public InsertDishTask(Context context, List<Dish> dish) {
        this.context = context;
        this.dish = dish;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        DishLogic.getInstance().insertDish(context, dish); //将桌况写入数据库
        return null;
    }

}
