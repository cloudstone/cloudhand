package com.cloudstone.cloudhand.view;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FoodItem extends RelativeLayout {
	private TextView tvNameView;
    private TextView tvPriceView;
    private TextView tvCountView;
    private Button btnIncr;
    private Button btnDesc;


	public FoodItem(Context context) {
		super(context);
        init(context);
	}
        
	private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_food_item, this, true);
        
        tvNameView = (TextView) findViewById(R.id.text_name);
        tvPriceView = (TextView) findViewById(R.id.text_price);
        tvCountView = (TextView) findViewById(R.id.text_count);
        btnIncr = (Button) findViewById(R.id.btn_incr);
        btnDesc = (Button) findViewById(R.id.btn_desc);
        btnIncr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChanged(FoodItem.this, true);
                }
            }
        });
        btnDesc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChanged(FoodItem.this, false);
                }
            }
        });
    }
    
    public void renderCount(int count) {
        tvCountView.setText(String.valueOf(count));
    }
    
    public void render(Food food, int count) {
        tvNameView.setText(food.getName());
        tvPriceView.setText(food.getPrice() + "");
        renderCount(count);
    }
    
    private FoodItemListener listener;
    public void setListener(FoodItemListener l) {
        this.listener = l;
    }
    
    public interface FoodItemListener {
        public void onChanged(FoodItem view, boolean incr);
    }

}
