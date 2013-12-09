package com.cloudstone.cloudhand.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    
    private static final String DB_Name = "cloudhand";
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_Name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table usertable(_id integer primary key autoincrement,username varchar(20),password varchar(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
    
    //创建用户名表
    private void createUserNameTable(SQLiteDatabase db){  
        
        //创建表SQL语句  
        String stu_table="create table usertable(_id integer primary key autoincrement,username varchar(20),password varchar(20))";  
     
        //执行SQL语句   
        db.execSQL(stu_table);
  } 

}
