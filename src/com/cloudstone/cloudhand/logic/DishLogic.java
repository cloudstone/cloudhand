package com.cloudstone.cloudhand.logic;

import java.util.List;

import android.content.Context;

import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.storage.DishStorage;
import com.cloudstone.cloudhand.storage.TableStorage;

public class DishLogic extends BaseLogic {
    
    private static DishLogic me;
    
    private DishLogic() {
    }
    
    public static DishLogic getInstance() {
        if (me == null) {
            synchronized (DishLogic.class) {
                if (me == null) {
                    me = new DishLogic();
                }
            }
        }
        return me;
    }
    
    //向数据库插入菜单
    public void insertDish(Context context, List<Dish> dish) {
        DishStorage dishStorage = new DishStorage(context);
        dishStorage.insertDishTable(dish);
    }
    
    //清空数据库的菜单
    public void clearDish(Context context) {
        DishStorage dishStorage = new DishStorage(context);
        dishStorage.clearDishTable();
    }
    
    //获取数据库的菜单
    public List<Dish> getAllDish(Context context) {
        DishStorage dishStorage = new DishStorage(context);
        return dishStorage.getAllDishTable();
    }
    
//    //创建数据库的菜单表
//    public void createTableTable(Context context) {
//        TableStorage tableStorage = new TableStorage(context);
//        tableStorage.createTableTable();
//    }
    
    //删除数据库的菜单表
    public void dropDishTable(Context context) {
        DishStorage dishStorage = new DishStorage(context);
        dishStorage.dropDishTable();
    } 
}
