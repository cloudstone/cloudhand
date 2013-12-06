package com.cloudstone.cloudhand.dao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cloudstone.cloudhand.db.DBOpenHelper;
import com.cloudstone.cloudhand.service.UserService;

public class UserDao implements UserService {
    
    private DBOpenHelper dbOpenHelper; 

    @Override
    public void addUser(Object[] params) {
        SQLiteDatabase db = null;
        try {
            String sql = "insert table user(name, password) values(?, ?)";
            dbOpenHelper.getWritableDatabase().execSQL(sql, params);
        } catch (Exception e) {
            Log.i("addUser in UserDao", e.toString());
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }

    @Override
    public void clearUser() {
        SQLiteDatabase db = null;
        try {
            String sql = "delete from user)";
            dbOpenHelper.getWritableDatabase().execSQL(sql);
        } catch (Exception e) {
            Log.i("clearUser in UserDao", e.toString());
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }

}
