package com.cloudstone.cloudhand.view;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Dish;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseDishItem extends LinearLayout {
    
    private TextView nameView;
    private TextView priceView;
    private TextView countView;
    private TextView tvRemark;

    public BaseDishItem(Context context) {
        super(context);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_dish_list_item_submitted, this, true);
        
        nameView = (TextView) findViewById(R.id.tv_dish_name);
        priceView = (TextView) findViewById(R.id.tv_price);
        countView = (TextView) findViewById(R.id.tv_count);
        tvRemark = (TextView) findViewById(R.id.tv_remark);
    }
    
    public void renderCount(int count) {
        countView.setText(String.valueOf(count));
    }
    
    public void render(Dish dish, int count, String remark) {
    	if(dish == null) {
    		nameView.setText("aaaa");
            priceView.setText("bbbb");
            tvRemark.setText("cccc");
    	} else {
    		nameView.setText(dish.getName());
            priceView.setText(dish.getPrice() + "");
            tvRemark.setText(remark);
    	}
        renderCount(count);
    }
    
    private DishItemListener listener;
    public void setListener(DishItemListener l) {
        this.listener = l;
    }
    
    public interface DishItemListener {
        public void onChanged(BaseDishItem view, boolean incr);
        public void onCheckedChange(BaseDishItem view);
    }

}