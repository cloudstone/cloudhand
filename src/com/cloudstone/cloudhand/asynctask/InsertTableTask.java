package com.cloudstone.cloudhand.asynctask;

import java.util.List;

import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.logic.TableLogic;
import com.cloudstone.cloudhand.logic.UserLogic;

import android.content.Context;
import android.os.AsyncTask;

public class InsertTableTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private List<Table> tables;
    
    public InsertTableTask(Context context, List<Table> tables) {
        this.context = context;
        this.tables = tables;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        TableLogic.getInstance().insertTable(context, tables); //将桌况写入数据库
        return null;
    }

}
