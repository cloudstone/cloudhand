package com.cloudstone.cloudhand.dialog;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.constant.BroadcastConst;
import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.util.GlobalValue;
import com.cloudstone.cloudhand.view.DishNoteItem;
import com.cloudstone.cloudhand.view.DishNoteItem.DishNoteItemListener;

public class DishNoteDialogFragment extends DialogFragment {
    private Button btnConfirm;
    private Button btnCancle;
    
    private ListView listDishNote;
    private BaseAdapter adapter;
    
    private int dishId; //被点击的菜的id
    private Set<Integer> checkedDishNoteIdSet = new HashSet<Integer>(); //这样菜中被选中的备注id集合
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_dish_note, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        listDishNote = (ListView) getView().findViewById(R.id.listview_dish_note);
        
        btnConfirm = (Button) getView().findViewById(R.id.btn_confirm);
        btnCancle = (Button) getView().findViewById(R.id.btn_cancle);
        
        dishId = getArguments().getInt("dishId");
        
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                setDishNote(dishId, checkedDishNoteIdSet);
                //发送更新菜单界面的广播
                Intent intent = new Intent();
                intent.setAction(BroadcastConst.UPDATE_OPEN_TABLE);
                getActivity().sendBroadcast(intent);
                dismiss();
            }
        });
        
        btnCancle.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        
        render();
    }
    
    private class InnerAdapter extends BaseAdapter implements DishNoteItemListener {
        
        @Override
        public int getCount() {
            return (GlobalValue.getIns().getDishNotes().size());
        }

        @Override
        public DishNote getItem(int position) {
            return (DishNote)(GlobalValue.getIns().getDishNotes().get(position));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DishNoteItem view = (DishNoteItem) convertView;
            if (view == null) {
                view = createView();
            }
            DishNote dishNote = getItem(position);
            boolean isChecked = false;
            Iterator<Integer> iterator = getDishNote(dishId).iterator();
            while(iterator.hasNext()){
                 if(dishNote.getId() == iterator.next()) {
                     isChecked = true;
                     checkedDishNoteIdSet.add(dishNote.getId());
                     break;
                 }
            }
            view.render(dishNote, isChecked);
            bindView(view, position);
            return view;
        }
        
        private DishNoteItem createView() {
            DishNoteItem view = new DishNoteItem(getActivity());
            view.setListener(this);
            return view;
        }
        
        private void bindView(DishNoteItem view, int position) {
            DishNote dishnote = getItem(position);
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                view.setTag(holder);
            }
            holder.setDishNote(dishnote);
        }

        @Override
        public void onCheckedChange(DishNoteItem view, boolean isChecked) {
            ViewHolder holder = (ViewHolder) view.getTag();
            if(holder != null) {
                DishNote dishNote = holder.getDishNote();
                if(isChecked) {
                    checkedDishNoteIdSet.add(dishNote.getId());
                } else {
                    Iterator<Integer> iterator = checkedDishNoteIdSet.iterator();
                    Set<Integer> removeSet = new HashSet<Integer>();
                    while(iterator.hasNext()) {
                        int dishNoteId = iterator.next();
                        if(dishNoteId == dishNote.getId()) {
                            removeSet.add(dishNoteId);
                        }
                    }
                    checkedDishNoteIdSet.removeAll(removeSet);
                }
            }
        }
        
    }
    
    private void setDishNote(int dishId, Set<Integer> dishNotes) {
        ((OpenTableActivity)(getActivity())).setDishNoteIdSet(dishId, dishNotes);
    }
    
    protected Set<Integer> getDishNote(int dishId) {
        return ((OpenTableActivity)(getActivity())).getDishNoteIdSet(dishId);
    }
    
    private class ViewHolder {
        private DishNote dishNote;

        public DishNote getDishNote() {
            return dishNote;
        }

        public void setDishNote(DishNote dishNote) {
            this.dishNote = dishNote;
        }
        
    }
    
    private void render() {
        adapter = new InnerAdapter(); 
        listDishNote.setAdapter(adapter);
    }

}
