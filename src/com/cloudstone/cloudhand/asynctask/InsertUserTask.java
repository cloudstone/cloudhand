package com.cloudstone.cloudhand.asynctask;

import com.cloudstone.cloudhand.logic.UserLogic;

import android.content.Context;
import android.os.AsyncTask;

public class InsertUserTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private String[] userList;
    
    public InsertUserTask(Context context, String[] userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        UserLogic.getInstance().insertUser(context, userList); //将用户名写入数据库
        return null;
    }

}
