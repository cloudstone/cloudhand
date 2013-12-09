package com.cloudstone.cloudhand.storage;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cloudstone.cloudhand.db.DBOpenHelper;
import com.cloudstone.cloudhand.util.L;

public class UserStorage extends BaseStorage {
    private DBOpenHelper dbOpenHelper;
    
    public UserStorage(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }
    
    public void insertUser(String[] params) {
        SQLiteDatabase db = null;
        try {
            createUserTable();
            clearUser();
            db = dbOpenHelper.getWritableDatabase();
            for(int i = 0; i < params.length; i++) {
                String sql = String.format("insert into user values('%s')", params[i]);
                System.out.println(sql);
                db.execSQL(sql);
            }
        } catch (Exception e) {
            L.e("addUser in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }

    public void clearUser() {
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            String sql = "delete from user";
            db.execSQL(sql);
        } catch (Exception e) {
            L.e("clearUser in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }
    
    public String[] getAllUser() {
        SQLiteDatabase db = null;
        List<String> user = new ArrayList<String>();
        try {
            db = dbOpenHelper.getReadableDatabase();
            String sql = "select * from user";
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                user.add(cursor.getString(cursor.getColumnIndex("name")));
            }
        } catch (Exception e) {
            L.e("getUser in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
            return user.toArray(new String[user.size()]);
        }
    }
    
    public void createUserTable() {
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            String sql = "create table if not exists user(name varchar(64))";
            db.execSQL(sql);
        } catch (Exception e) {
            L.e("createUserTable in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }
    
    public void dropUserTable() {
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            String sql = "DROP TABLE IF EXISTS user";
            db.execSQL(sql);
        } catch (Exception e) {
            L.e("dropUserTable in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }
}
