package com.cloudstone.cloudhand.view;

import com.cloudstone.cloudhand.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChooseTableItem extends LinearLayout {
    
    private TextView nameView;

    public ChooseTableItem(Context context) {
        super(context);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_base_dropdown_list_line, this, true);
        nameView = (TextView) findViewById(R.id.tv_name);
        
        nameView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(ChooseTableItem.this);
                }
            }
        });
    }
    
    public void render(String tableName) {
        nameView.setText(tableName);
    }
    
    private ChooseTableItemListener listener;
    public void setListener(ChooseTableItemListener l) {
        this.listener = l;
    }
    
    public interface ChooseTableItemListener {
        public void onClick(ChooseTableItem view);
    }
}
