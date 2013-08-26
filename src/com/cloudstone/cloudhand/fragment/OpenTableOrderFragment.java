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
        List<Dish> dishes = searchItem(keywords);
        updateLayout(dishes);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    
    public void updateLayout(List<Dish> dishes) {
        adapter = new InnerAdapter(dishes);
        listView.setAdapter(adapter);
    }
    
    //模糊搜索过滤出一个菜单结果
    private List<Dish> searchItem(String keywords) {
        ContrastPinyin contrastPinyin = new ContrastPinyin();
        List<Dish> dishes = new ArrayList<Dish>();
        for (int i = 0; i < ((OpenTableActivity)(getActivity())).getDishes().size(); i++) {
            int index = 0;;
            if(contrastPinyin.isContain(keywords)) {
                index = ((OpenTableActivity)(getActivity())).getDishes().get(i).getName().indexOf(keywords);
            } else {
                String pinyin = contrastPinyin.getSpells(((OpenTableActivity)(getActivity())).getDishes().get(i).getName());
                index = pinyin.indexOf(keywords.toLowerCase());
            }
            // 存在匹配的数据
            if (index != -1) {
                dishes.add(((OpenTableActivity)(getActivity())).getDishes().get(i));
            }
        }
        return dishes;
    }
    
    @Override
    protected void render() {
        adapter = new InnerAdapter(((OpenTableActivity)(getActivity())).getDishes());
        listView.setAdapter(adapter);
        onQueryTextChange(searchView.getQuery().toString());
    }
    
}