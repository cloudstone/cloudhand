package com.cloudstone.cloudhand.logic;

import java.util.List;

import android.content.Context;

import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.storage.DishNoteStorage;
import com.cloudstone.cloudhand.storage.TableStorage;

public class DishNoteLogic extends BaseLogic {
    
    private static DishNoteLogic me;
    
    private DishNoteLogic() {
    }
    
    public static DishNoteLogic getInstance() {
        if (me == null) {
            synchronized (DishNoteLogic.class) {
                if (me == null) {
                    me = new DishNoteLogic();
                }
            }
        }
        return me;
    }
    
    //向数据库插入用户名
    public void insertDishNote(Context context, List<DishNote> dishNotes) {
        DishNoteStorage dishNoteStorage = new DishNoteStorage(context);
        dishNoteStorage.insertDishNote(dishNotes);
    }
    
    //清空数据库的用户名
    public void clearTable(Context context) {
        DishNoteStorage dishNoteStorage = new DishNoteStorage(context);
        dishNoteStorage.clearDishNote();
    }
    
    //获取数据库的用户名
    public List<DishNote> getAllDishTable(Context context) {
        DishNoteStorage dishNoteStorage = new DishNoteStorage(context);
        return dishNoteStorage.getAllDishNote();
    }
    
//    //创建数据库的用户表
//    public void createTableTable(Context context) {
//        TableStorage tableStorage = new TableStorage(context);
//        tableStorage.createTableTable();
//    }
    
    //删除数据库的用户表
    public void dropDishNoteTable(Context context) {
        DishNoteStorage dishNoteStorage = new DishNoteStorage(context);
        dishNoteStorage.dropDishNoteTable();
    } 
}
