package com.cloudstone.cloudhand.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.view.DishItem;
import com.cloudstone.cloudhand.view.DishItem.DishItemListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * 
 * @author xhc
 *
 */
public class BaseFragment extends Fragment {
    protected ListView listView;
    protected BaseAdapter adapter;
    
    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            render();
        }
    };
    
    
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("update");
        getActivity().registerReceiver(broadcastReceiver, filter);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
    
    protected void setDishCount(int dishId, int count) {
        ((OpenTableActivity)(getActivity())).getDishCountMap().put(dishId, count);
    }
    
    protected int getDishCount(int dishId) {
        if (!((OpenTableActivity)(getActivity())).getDishCountMap().containsKey(dishId)) {
            ((OpenTableActivity)(getActivity())).getDishCountMap().put(dishId, 0);
        }
        return ((OpenTableActivity)(getActivity())).getDishCountMap().get(dishId);
    }
    
    protected Dish getDish(int dishId) {
        if (((OpenTableActivity)(getActivity())).getDishes() != null) {
            for (Dish dish:((OpenTableActivity)(getActivity())).getDishes()) {
                if (dish.getId() == dishId) {
                    return dish;
                }
            }
        }
        return null;
    }
    
    protected class InnerAdapter extends BaseAdapter implements DishItemListener {
        List<Dish> data = new ArrayList<Dish>();
        
        public InnerAdapter(List<Dish> data) {
            this.data = data;
        }
            
        @Override
        public void onChanged(DishItem view, boolean incr) {
            ViewHolder holder = (ViewHolder) view.getTag();
            Dish dish = holder.getDish();
            int count = getDishCount(dish.getId());
            if (incr) {
                count++;
                setDishCount(dish.getId(), count);
            } else {
                if (count > 0) {
                    count--;
                    setDishCount(dish.getId(), count);
                }
            }
            view.renderCount(count);
            //更新界面
            update();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Dish getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public DishItem getView(int position, View convertView, ViewGroup parent) {
            DishItem view = (DishItem) convertView;
            if (view == null) {
                view = createView();
            }
            Dish dish = getItem(position);
            view.render(dish, getDishCount(dish.getId()));
            bindView(view, position);
            return view;
        }
        
        private DishItem createView() {
            DishItem view = new DishItem(getActivity());
            view.setListener(this);
            return view;
        }
        
        private void bindView(DishItem view, int position) {
            Dish dish = getItem(position);
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                view.setTag(holder);
            }
            holder.setDish(dish);
        }
    }
    
    private class ViewHolder {
        private Dish dish;

        public Dish getDish() {
            return dish;
        }

        public void setDish(Dish dish) {
            this.dish = dish;
        }
        
    }
    
    //更新界面
    protected void render() {}
    
    //加减按钮点击事件
    protected void update() {}

}
