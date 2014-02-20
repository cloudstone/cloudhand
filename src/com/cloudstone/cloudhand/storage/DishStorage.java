package com.cloudstone.cloudhand.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.db.DBOpenHelper;
import com.cloudstone.cloudhand.util.L;

public class DishStorage extends BaseStorage {
    private DBOpenHelper dbOpenHelper;
    
    public DishStorage(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }
    
    public void insertDishTable(List<Dish> dish) {
        SQLiteDatabase db = null;
        try {
            clearDishTable();
            db = dbOpenHelper.getWritableDatabase();
            Iterator<Dish> it = dish.iterator();
            while(it.hasNext()) {
                Dish dishFlag = it.next();
                db.execSQL("insert into dish values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{dishFlag.getId(), dishFlag.getName(),
                        dishFlag.getPrice(), dishFlag.getMemberPrice(), dishFlag.getUnit(),
                        dishFlag.getSpicy(), dishFlag.isSpecialPrice()?1:0, dishFlag.isNonInt()?1:0,
                                dishFlag.getDesc(), dishFlag.getImageId(), dishFlag.getStatus(), dishFlag.getPinyin(), dishFlag.isSoldout()?1:0});
            }
        } catch (Exception e) {
            L.e("insertDishTable in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }

    public void clearDishTable() {
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            String createDishSql = "create table if not exists dish(id integer, name varchar(64), price double, memberPrice double, " +
                    "unit integer, spicy integer, specialPrice integer, nonInt integer, desc varchar(64)," +
                    " imageId varchar(64), status integer, pinyin varchar(64), soldout integer)";
            db.execSQL(createDishSql);
            String sql = "delete from dish";
            db.execSQL(sql);
        } catch (Exception e) {
            L.e("clearDishTable in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }
    
    public List<Dish> getAllDishTable() {
        SQLiteDatabase db = null;
        List<Dish> dish = new ArrayList<Dish>();
        try {
            db = dbOpenHelper.getReadableDatabase();
            String sql = "select * from dish";
            Cursor cursor = db.rawQuery(sql, null);
            Dish dishFlag = null;
            while (cursor.moveToNext()) {
                dishFlag = new Dish();
                dishFlag.setId(cursor.getInt((cursor.getColumnIndex("id"))));
                dishFlag.setName(cursor.getString((cursor.getColumnIndex("name"))));
                dishFlag.setPrice(cursor.getDouble((cursor.getColumnIndex("price"))));
                dishFlag.setMemberPrice(cursor.getDouble((cursor.getColumnIndex("memberPrice"))));
                dishFlag.setUnit(cursor.getInt((cursor.getColumnIndex("unit"))));
                dishFlag.setSpicy(cursor.getInt((cursor.getColumnIndex("spicy"))));
                dishFlag.setSpecialPrice(cursor.getInt((cursor.getColumnIndex("specialPrice"))) == 0?false:true);
                dishFlag.setNonInt(cursor.getInt((cursor.getColumnIndex("nonInt"))) == 0?false:true);
                dishFlag.setDesc(cursor.getString((cursor.getColumnIndex("desc"))));
                dishFlag.setImageId(cursor.getString((cursor.getColumnIndex("imageId"))));
                dishFlag.setStatus(cursor.getInt((cursor.getColumnIndex("status"))));
                dishFlag.setPinyin(cursor.getString((cursor.getColumnIndex("pinyin"))));
                dishFlag.setSoldout(cursor.getInt((cursor.getColumnIndex("soldout"))) == 0?false:true);
                dish.add(dishFlag);
            }
        } catch (Exception e) {
            L.e("getAllDishTable in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
            return dish;
        }
    }
    
    public void dropDishTable() {
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            String sql = "DROP TABLE IF EXISTS dish";
            db.execSQL(sql);
        } catch (Exception e) {
            L.e("dropDishTable in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }
}
