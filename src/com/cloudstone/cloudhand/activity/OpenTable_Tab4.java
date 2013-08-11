package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.data.Food;
import com.cloudstone.cloudhand.view.FoodItem;
import com.cloudstone.cloudhand.view.FoodItem.FoodItemListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class OpenTable_Tab4 extends BaseFragment {
	
	private List<Food> food = new ArrayList<Food>();
    private Map<Integer, Integer> foodCountMap = new HashMap<Integer, Integer>();
    private SearchView searchView;
    private ListView  listView;
    private BaseAdapter adapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	listView = (ListView)getView().findViewById(R.id.foodItem);
    	render();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_adapter_opentable, container, false);
    }
    
    private void render() {
        adapter = new InnerAdapter();
        listView.setAdapter(adapter);
    }
    
    private class InnerAdapter extends BaseAdapter implements FoodItemListener {

		@Override
		public int getCount() {
			return food.size();
		}

		@Override
		public Food getItem(int position) {
			return food.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			FoodItem view = (FoodItem) convertView;
            if (view == null) {
                view = createView();
            }
            Food food = getItem(position);
            view.render(food, getFoodCount(food.getId()));
            bindView(view, position);
            return view;
		}
		
		private FoodItem createView() {
			FoodItem view = new FoodItem(getActivity());
            view.setListener(this);
            return view;
        }
		
		private void bindView(FoodItem view, int position) {
            Food food = getItem(position);
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                view.setTag(holder);
            }
            holder.setFood(food);
        }

		@Override
		public void onChanged(FoodItem view, boolean incr) {
			// TODO Auto-generated method stub
			
		}
        
    }
    
    private class ViewHolder {
        private Food food;

        public Food getFood() {
            return food;
        }

        public void setFood(Food food) {
            this.food = food;
        }
        
    }
    
    private void setFoodCount(int foodId, int count) {
    	foodCountMap.put(foodId, count);
    }
    
    private int getFoodCount(int foodId) {
        if (!foodCountMap.containsKey(foodId)) {
        	foodCountMap.put(foodId, 0);
        }
        return foodCountMap.get(foodId);
    }
    
    public void initData() {
        Food newFood = new Food(0, "鱼香肉丝", 42);
        food.add(newFood);
        
        newFood = new Food(1, "鱼香肉丝", 35);
        food.add(newFood);
        
        newFood = new Food(2, "芝麻鱼球", 45);
        food.add(newFood);
        
        newFood = new Food(3, "熬黄花鱼", 32);
        food.add(newFood);
        
        newFood = new Food(4, "泰山赤鳞鱼", 52);
        food.add(newFood);
        
        newFood = new Food(5, "北京烤鸭", 188);
        food.add(newFood);
        
        newFood = new Food(6, "素锅烤鸭", 34.5);
        food.add(newFood);
        
        newFood = new Food(7, "纸包鸡", 36.5);
        food.add(newFood);
        
        newFood = new Food(8, "攻暴鸭丁", 55);
        food.add(newFood);
        
        newFood = new Food(9, "宫保鸡丁", 48);
        food.add(newFood);
        
        newFood = new Food(10, "铁狮子头", 79);
        food.add(newFood);
        
        newFood = new Food(11, "换心乌贼", 32.8);
        food.add(newFood);
        
        newFood = new Food(12, "奶汤银肺", 25);
        food.add(newFood);
        
    }

}
