package com.cloudstone.cloudhand.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.db.DBOpenHelper;
import com.cloudstone.cloudhand.util.L;

public class DishNoteStorage extends BaseStorage {
    private DBOpenHelper dbOpenHelper;
    
    public DishNoteStorage(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }
    
    public void insertDishNote(List<DishNote> dishNotes) {
        SQLiteDatabase db = null;
        try {
            clearDishNote();
            db = dbOpenHelper.getWritableDatabase();
            Iterator<DishNote> it = dishNotes.iterator();
            while(it.hasNext()) {
                DishNote dishNote = it.next();
                db.execSQL("insert into dishNote values (?, ?)", new Object[]{dishNote.getId(), dishNote.getName()});
            }
        } catch (Exception e) {
            L.e("insertDishNote in DishNOteStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }

    public void clearDishNote() {
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            String createDishNoteSql = "create table if not exists dishNote(id integer, name varchar(64))";
            db.execSQL(createDishNoteSql);
            String sql = "delete from dishNote";
            db.execSQL(sql);
        } catch (Exception e) {
            L.e("clearDishNote in DishNoteStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }
    
    public List<DishNote> getAllDishNote() {
        SQLiteDatabase db = null;
        List<DishNote> dishNotes = new ArrayList<DishNote>();
        try {
            db = dbOpenHelper.getReadableDatabase();
            String sql = "select * from dishNote";
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                DishNote dishNote = new DishNote();
                dishNote.setId(cursor.getInt(cursor.getColumnIndex("id")));
                dishNote.setName(cursor.getString(cursor.getColumnIndex("name")));
                dishNotes.add(dishNote);
            }
        } catch (Exception e) {
            L.e("getAllDishNote in DishNoteStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
            return dishNotes;
        }
    }
    
    public void dropDishNoteTable() {
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            String sql = "DROP TABLE IF EXISTS dishNote";
            db.execSQL(sql);
        } catch (Exception e) {
            L.e("dropDishNoteTable in DishNoteStorage error", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }
}
