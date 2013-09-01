package com.cloudstone.cloudhand.fragment;

import java.util.List;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.dialog.DishNoteDialogFragment;
import com.cloudstone.cloudhand.view.DishItem;
import com.cloudstone.cloudhand.view.DishItem.DishItemListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


/**
 * 
 * @author xhc
 *
 */
public class OpenTableBaseFragment extends BaseFragment {
    protected ListView dishListView;
    protected BaseAdapter adapter;
    
    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("update")) {
                render();
            }
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
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
    
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
            intent.setAction("update");
            getActivity().sendBroadcast(intent);
        }

        @Override
        public int getCount() {
            return getDishes().size();
        }

        @Override
        public Dish getItem(int position) {
            return getDishes().get(position);
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
            String dishNote = "";
            List<Integer> dishNoteIdList = ((OpenTableActivity)(getActivity())).getDishNoteIdList(dish.getId());
            List<DishNote> dishNotes = ((OpenTableActivity)(getActivity())).getDishNotes();
            for(int i = 0; i < dishNoteIdList.size(); i++) {
                for(int j = 0; j < dishNotes.size(); j++) {
                    if(dishNoteIdList.get(i) == dishNotes.get(j).getId()) {
                        dishNote += dishNotes.get(j).getName() + ";";
                    }
                }
            }
            if(dishNote.equals("")) {
                dishNote = getString(R.string.no_remark);
            }
            view.render(dish, getDishCount(dish.getId()), dishNote);
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
    
    protected List<Dish> getDishes() {
        return null;
    }
    
    //更新界面
    protected void render() {}
    
}
