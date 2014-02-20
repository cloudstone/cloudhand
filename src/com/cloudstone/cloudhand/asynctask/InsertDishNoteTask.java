package com.cloudstone.cloudhand.asynctask;

import java.util.List;

import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.logic.DishNoteLogic;

import android.content.Context;
import android.os.AsyncTask;

public class InsertDishNoteTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private List<DishNote> dishNotes;
    
    public InsertDishNoteTask(Context context, List<DishNote> dishNotes) {
        this.context = context;
        this.dishNotes = dishNotes;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        DishNoteLogic.getInstance().insertDishNote(context, dishNotes); //将桌况写入数据库
        return null;
    }

}
