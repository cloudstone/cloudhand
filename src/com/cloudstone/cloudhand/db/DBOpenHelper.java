package com.cloudstone.cloudhand.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    
    private static final String DB_NAME = "cloudhand";
    private static final int VERSION = 2;

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
        String createTableSql = "create table if not exists tables(id integer, name varchar(64), type integer, capacity integer, " +
                "minCharge double, tipMode integer, tip double, orderId integer, status integer)";
        db.execSQL(createTableSql);
    }

}
