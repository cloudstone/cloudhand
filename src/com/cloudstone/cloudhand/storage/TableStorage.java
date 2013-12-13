package com.cloudstone.cloudhand.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.db.DBOpenHelper;
import com.cloudstone.cloudhand.util.L;

public class TableStorage extends BaseStorage {
    private DBOpenHelper dbOpenHelper;
    
    public TableStorage(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }
    
    public void insertTable(List<Table> tables) {
        SQLiteDatabase db = null;
        try {
            clearTable();
            db = dbOpenHelper.getWritableDatabase();
            Iterator<Table> it = tables.iterator();
            while(it.hasNext()) {
                Table table = it.next();
                db.execSQL("insert into tables values (?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{table.getId(), table.getName(), table.getType(), table.getCapacity(), table.getMinCharge(),
                        table.getTipMode(), table.getTip(), table.getOrderId(), table.getStatus()});
            }
        } catch (Exception e) {
            L.e("insertTable in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }

    public void clearTable() {
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            String sql = "delete from tables";
            db.execSQL(sql);
        } catch (Exception e) {
            L.e("clearTable in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }
    
    public List<Table> getAllTable() {
        SQLiteDatabase db = null;
        List<Table> tables = new ArrayList<Table>();
        try {
            System.out.println(dbOpenHelper.getReadableDatabase().getVersion());
            db = dbOpenHelper.getReadableDatabase();
            String sql = "select * from tables";
            Cursor cursor = db.rawQuery(sql, null);
            Table table = null;
            //  private int id;
            //  private String name;
            //  private int type;//类型
            //  private int capacity;//最多人数
            //  private double minCharge;//最低消费
            //  private int tipMode;//服务费收取模式
            //  private double tip;
            //  private int orderId;
            //  private int status;
            while (cursor.moveToNext()) {
                table = new Table();
                table.setId(cursor.getInt((cursor.getColumnIndex("id"))));
                table.setName(cursor.getString((cursor.getColumnIndex("name"))));
                table.setType(cursor.getInt((cursor.getColumnIndex("type"))));
                table.setCapacity(cursor.getInt((cursor.getColumnIndex("capacity"))));
                table.setMinCharge(cursor.getDouble((cursor.getColumnIndex("minCharge"))));
                table.setTipMode(cursor.getInt((cursor.getColumnIndex("tipMode"))));
                table.setTip(cursor.getDouble((cursor.getColumnIndex("tip"))));
                table.setOrderId(cursor.getInt((cursor.getColumnIndex("orderId"))));
                table.setStatus(cursor.getInt((cursor.getColumnIndex("status"))));
                tables.add(table);
            }
        } catch (Exception e) {
            L.e("getAllTable in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
            return tables;
        }
    }
    
//    public void createTableTable() {
//        SQLiteDatabase db = null;
//        try {
//            db = dbOpenHelper.getWritableDatabase();
//            String sql = "create table if not exists table(id integer, name varchar(64), type integer, capacity integer, " +
//                    "minCharge double, tipMode, integer, tip double, orderId int, status int)";
//            db.execSQL(sql);
//        } catch (Exception e) {
//            L.e("createTableTable in UserStorage error", e);
//        } finally {
//            if(db != null) {
//                db.close();
//            }
//        }
//    }
    
    public void dropTableTable() {
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            String sql = "DROP TABLE IF EXISTS tables";
            db.execSQL(sql);
        } catch (Exception e) {
            L.e("dropTableTable in UserStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }
}
