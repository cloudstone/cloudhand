package com.cloudstone.cloudhand.dialog;

import java.util.ArrayList;
import java.util.List;

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
import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.ListDishNoteApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.view.DishNoteItem;
import com.cloudstone.cloudhand.view.DishNoteItem.DishNoteItemListener;

public class DishNoteDialogFragment extends DialogFragment {
    private Button btnConfirm;
    private Button btnCancle;
    
    private ListView listDishNote;
    private BaseAdapter adapter;
    
    private List<Integer> dishNoteIdList = new ArrayList<Integer>();
    private int dishId;
    
    private List<DishNote> dishNotes = new ArrayList<DishNote>();
    
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
        
        dishNotes = ((OpenTableActivity)(getActivity())).getDishNotes();
        
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                setDishNote(dishId, dishNoteIdList);
                //发送更新菜单界面的广播
                Intent intent = new Intent();
                intent.setAction("update");
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
            return dishNotes.size();
        }

        @Override
        public DishNote getItem(int position) {
            return (DishNote)dishNotes.get(position);
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
            for(int i = 0;i < getDishNote(dishId).size();i++) {
                if(dishNote.getId() == getDishNote(dishId).get(i)) {
                    isChecked = true;
                    dishNoteIdList.add(dishNote.getId());
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
                    dishNoteIdList.add(dishNote.getId());
                } else {
                    for(int i = 0; i < dishNoteIdList.size();i++) {
                        if(dishNoteIdList.get(i) == dishNote.getId()) {
                            dishNoteIdList.remove(i);
                        }
                    }
                    
                }
            }
        }
        
    }
    
    private void setDishNote(int dishId, List<Integer> dishNotes) {
        ((OpenTableActivity)(getActivity())).setDishNoteIdList(dishId, dishNotes);
    }
    
    protected List<Integer> getDishNote(int dishId) {
        return ((OpenTableActivity)(getActivity())).getDishNoteIdList(dishId);
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
