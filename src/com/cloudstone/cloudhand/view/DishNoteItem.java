package com.cloudstone.cloudhand.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.DishNote;

public class DishNoteItem extends LinearLayout {
    
    private TextView nameView;
    private CheckBox cbDishNote;

    public DishNoteItem(Context context) {
        super(context);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_dish_note_dropdown_list_line, this, true);
        nameView = (TextView) findViewById(R.id.tv_dish_note_name);
        cbDishNote = (CheckBox) findViewById(R.id.cb_dish_note);
        
        cbDishNote.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.onCheckedChange(DishNoteItem.this, cbDishNote.isChecked());
                }
            }
        });
        
    }
    
    public void render(DishNote dishNote, boolean isChecked) {
        nameView.setText(dishNote.getName());
        cbDishNote.setChecked(isChecked);
    }
    
    private DishNoteItemListener listener;
    public void setListener(DishNoteItemListener l) {
        this.listener = l;
    }
    
    public interface DishNoteItemListener {
        public void onCheckedChange(DishNoteItem view, boolean isChecked);
    }

}
