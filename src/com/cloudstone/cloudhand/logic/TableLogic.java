package com.cloudstone.cloudhand.logic;

import java.util.List;

import android.content.Context;

import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.storage.TableStorage;

public class TableLogic extends BaseLogic {
    
    private static TableLogic me;
    
    private TableLogic() {
    }
    
    public static TableLogic getInstance() {
        if (me == null) {
            synchronized (TableLogic.class) {
                if (me == null) {
                    me = new TableLogic();
                }
            }
        }
        return me;
    }
    
    //向数据库插入用户名
    public void insertTable(Context context, List<Table> tables) {
        TableStorage tableStorage = new TableStorage(context);
        tableStorage.insertTable(tables);
    }
    
    //清空数据库的用户名
    public void clearTable(Context context) {
        TableStorage tableStorage = new TableStorage(context);
        tableStorage.clearTable();
    }
    
    //获取数据库的用户名
    public List<Table> getAllTable(Context context) {
        TableStorage tableStorage = new TableStorage(context);
        return tableStorage.getAllTable();
    }
    
//    //创建数据库的用户表
//    public void createTableTable(Context context) {
//        TableStorage tableStorage = new TableStorage(context);
//        tableStorage.createTableTable();
//    }
    
    //删除数据库的用户表
    public void dropTableTable(Context context) {
        TableStorage tableStorage = new TableStorage(context);
        tableStorage.dropTableTable();
    } 
}
