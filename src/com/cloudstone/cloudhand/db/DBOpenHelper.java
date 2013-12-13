package com.cloudstone.cloudhand.db;

import java.util.List;

import com.cloudstone.cloudhand.constant.Const;
import com.cloudstone.cloudhand.data.OrderDish;
import com.cloudstone.cloudhand.data.Table;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    
    private static final String DB_NAME = "cloudhand";
    private static final int VERSION = 4;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }
    
    public DBOpenHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserSql = "create table if not exists user(name varchar(64))";
        db.execSQL(createUserSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1 && newVersion == 2) {
            String createTableSql = "create table if not exists tables(id integer, name varchar(64), type integer, capacity integer, " +
                    "minCharge double, tipMode integer, tip double, orderId integer, status integer)";
            db.execSQL(createTableSql);
        }
        if(oldVersion == 2 && newVersion == 3) {
            String createDishSql = "create table if not exists dish(id integer, name varchar(64), price double, memberPrice double, " +
                    "unit integer, spicy integer, specialPrice integer, nonInt integer, desc varchar(64)," +
                    " imageId varchar(64), status integer, pinyin varchar(64), soldout integer)";
            db.execSQL(createDishSql);
        }
        
        if(oldVersion == 3 && newVersion == 4) {
            String createUserSql = "create table if not exists dishNote(id integer, name varchar(64))";
            db.execSQL(createUserSql);
        }
    }

}
