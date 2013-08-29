package com.cloudstone.cloudhand.view;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Table;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TableItem extends LinearLayout {
    
    private TextView nameView;
    private TextView statusView;
    

    public TableItem(Context context) {
        super(context);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_table_info_dropdown_list_line, this, true);
        nameView = (TextView) findViewById(R.id.tv_table_name);
        statusView = (TextView) findViewById(R.id.tv_table_status);
    }
    
    public void render(Table table) {
        nameView.setText(table.getName());
        switch (table.getStatus()) {
        case 0:
            statusView.setText(R.string.empty);
                break;
        case 1:
            statusView.setText(R.string.occupied);
                break;
        default:
            statusView.setText(R.string.submited);
            break;
        }
    }

}
