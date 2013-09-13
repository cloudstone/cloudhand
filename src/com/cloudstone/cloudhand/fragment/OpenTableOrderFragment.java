package com.cloudstone.cloudhand.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.pinyin.ContrastPinyin;
import com.cloudstone.cloudhand.util.DishBag;

public class OpenTableOrderFragment extends OpenTableBaseFragment implements SearchView.OnQueryTextListener {
    private SearchView searchView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_open_table_order, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchView = (SearchView)getView().findViewById(R.id.searchview_dish);
        searchView.setFocusable(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
    }
    
    @Override
    public boolean onQueryTextChange(String keywords) {
        render();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    
    //模糊搜索过滤出一个菜单结果
    private DishBag searchItem(String keywords) {
        ContrastPinyin contrastPinyin = new ContrastPinyin();
        DishBag data = new DishBag();
        DishBag dishes = ((OpenTableActivity)(getActivity())).getDishes();
        for (int i = 0; i < dishes.getSize(); i++) {
            int index = 0;;
            if(contrastPinyin.isContain(keywords)) {
                index = dishes.getByPos(i).getName().indexOf(keywords);
            } else {
                String pinyin = contrastPinyin.getSpells(dishes.getByPos(i).getName());
                index = pinyin.indexOf(keywords.toLowerCase());
            }
            // 存在匹配的数据
            if (index != -1) {
                data.put(dishes.getByPos(i).getId(), dishes.getByPos(i));
            }
        }
        return data;
    }
    
    @Override
    protected DishBag getDishes() {
        return searchItem(searchView.getQuery().toString());
    }
    
    @Override
    protected void render() {
        adapter = new InnerAdapter();
        dishListView.setAdapter(adapter);
    }
    
}