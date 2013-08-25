package com.cloudstone.cloudhand.view;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Dish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DishItem extends RelativeLayout {
    
    private TextView nameView;
    private TextView priceView;
    private TextView countView;
    private Button btnIncr;
    private Button btnDesc;

    public DishItem(Context context) {
        super(context);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_dish_list_item, this, true);
        
        nameView = (TextView) findViewById(R.id.tv_dish_name);
        priceView = (TextView) findViewById(R.id.tv_price);
        countView = (TextView) findViewById(R.id.tv_count);
        btnIncr = (Button) findViewById(R.id.btn_incr);
        btnDesc = (Button) findViewById(R.id.btn_decr);
        
        btnIncr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChanged(DishItem.this, true);
                }
            }
        });
        
        btnDesc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChanged(DishItem.this, false);
                }
            }
        });
    }
    
    public void renderCount(int count) {
        countView.setText(String.valueOf(count));
    }
    
    public void render(Dish dish, int count) {
        nameView.setText(dish.getName());
        priceView.setText(dish.getPrice() + "");
        renderCount(count);
    }
    
    private DishItemListener listener;
    public void setListener(DishItemListener l) {
        this.listener = l;
    }
    
    public interface DishItemListener {
        public void onChanged(DishItem view, boolean incr);
    }

}