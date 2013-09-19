package com.cloudstone.cloudhand.fragment;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.constant.BroadcastConst;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.dialog.DishNoteDialogFragment;
import com.cloudstone.cloudhand.util.DishBag;
import com.cloudstone.cloudhand.util.L;
import com.cloudstone.cloudhand.view.DishItem;
import com.cloudstone.cloudhand.view.DishItem.DishItemListener;

/**
 * 
 * @author xhc
 *
 */
public class OpenTableBaseFragment extends BaseFragment {
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BroadcastConst.INIT_OPEN_TABLE)) {
                dishes = filter(((OpenTableActivity)(getActivity())).getDishes());
                render();
            }
            if(intent.getAction().equals(BroadcastConst.UPDATE_OPEN_TABLE)) {
                dishes = filter(((OpenTableActivity)(getActivity())).getDishes());
//                render();
            	adapter.notifyDataSetChanged();
            }
        }
    };
    
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastConst.INIT_OPEN_TABLE);
        filter.addAction(BroadcastConst.UPDATE_OPEN_TABLE);
        getActivity().registerReceiver(broadcastReceiver, filter);
    }
        
    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
    protected ListView dishListView;
    protected BaseAdapter adapter;
    protected DishBag dishes = new DishBag();
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dishListView = (ListView)getView().findViewById(R.id.listview_dish);
    }
    
    private void setDishCount(int dishId, int count) {
        ((OpenTableActivity)(getActivity())).setDishCount(dishId, count);
    }
    
    protected int getDishCount(int dishId) {
        return ((OpenTableActivity)(getActivity())).getDishCount(dishId);
    }
    
    protected class InnerAdapter extends BaseAdapter implements DishItemListener {
        
        @Override
        public void onChanged(DishItem view, boolean incr) {
            ViewHolder holder = (ViewHolder) view.getTag();
            Dish dish = holder.getDish();
            int count = getDishCount(dish.getId());
            if (incr) {
                count++;
            } else {
                if (count > 0) {
                    count--;
                }
            }
            setDishCount(dish.getId(), count);
            view.renderCount(count);
            //更新界面
            Intent intent = new Intent();
            intent.setAction(BroadcastConst.UPDATE_OPEN_TABLE);
            System.out.println(dishListView.getFirstVisiblePosition());
//            dishListView.setSelection(dishListView.getSelectedItemPosition());
            getActivity().sendBroadcast(intent);
        }

        @Override
        public int getCount() {
            return getDishes().size();
        }

        @Override
        public Dish getItem(int position) {
            return getDishes().getByPos(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public DishItem getView(int position, View convertView, ViewGroup parent) {
            L.i(this, "getView");
            OpenTableActivity openTableActivity = ((OpenTableActivity)(getActivity()));
            DishItem view = (DishItem) convertView;
            if (view == null) {
                view = createView();
            }
            Dish dish = getItem(position);
            StringBuilder sb = new StringBuilder();
            Set<Integer> dishNoteIdSet = openTableActivity.getDishNoteIdSet(dish.getId());
            dishNoteIdSet.addAll(dishNoteIdSet);
            List<DishNote> dishNotes = openTableActivity.getDishNotes();
            
            for(int i = 0; i < dishNotes.size(); i++) {
                if(dishNoteIdSet.contains(dishNotes.get(i).getId())) {
                    sb.append(dishNotes.get(i).getName()).append(";");
                }
                
            }
            
            if(sb.length() == 0) {
                sb.append(getString(R.string.no_remark));
            }
            
            view.render(dish, getDishCount(dish.getId()), sb.toString());
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
        
        @Override
        public void onCheckedChange(DishItem view) {
            ViewHolder holder = (ViewHolder) view.getTag();
            Dish dish = holder.getDish();
            DishNoteDialogFragment dialog = new DishNoteDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("dishId", dish.getId());
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "dishNoteDialogFragment");
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
    
    protected Dish getDish(int dishId) {
        DishBag dishes = this.getDishes();
        if (dishes != null) {
            return dishes.getById(dishId);
        }
        return null;
    }
    
    protected double getTotalPrice() {
        double total = 0;
        DishBag dishes = this.getDishes();
        
        Iterator<Dish> it = dishes.iterator();
        while(it.hasNext()) {
            Dish dish = it.next();
            int dishId = dish.getId();
            int count = getDishCount(dishId);
            if (count > 0) {
                total += dish.getPrice() * count;
            }
        }
        return total;
    }
    
    protected DishBag getDishes() {
        return dishes;
    }
    
    //更新界面
    protected void render() {
        adapter = new InnerAdapter();
        dishListView.setAdapter(adapter);
    }
    
    protected DishBag filter(DishBag dishes) {
        return dishes;
    }
    
}
