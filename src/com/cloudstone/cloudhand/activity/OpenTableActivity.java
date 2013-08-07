package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.pinyin.ContrastPinyin;

//implements SearchView.OnQueryTextListener 
public class OpenTableActivity extends TabActivity {
	private ListView listView;
	private SearchView searchView;
    private List<String> names;
    private ListAdapter adapter;
    private List<String> mAllList = new ArrayList<String>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        initActionbar();
        names = loadData();
        listView = (ListView)findViewById(R.id.list);
//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_expandable_list_item_1,names);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
//        listView.setTextFilterEnabled(true);
//        searchView.setOnQueryTextListener(this);
//        searchView.setSubmitButtonEnabled(false);
        
        TabHost tabHost = getTabHost();
        
        LayoutInflater inflater = LayoutInflater.from(this);
        
        View view1 = inflater.inflate(R.layout.activity_opentable_tab1, tabHost.getTabContentView(), true);
        View view2 = inflater.inflate(R.layout.activity_opentable_tab2, tabHost.getTabContentView(), true);
        View view3 = inflater.inflate(R.layout.activity_opentable_tab3, tabHost.getTabContentView(), true);
        
        TabSpec t1 = tabHost.newTabSpec("t1");
        TabSpec t2 = tabHost.newTabSpec("t2");
        TabSpec t3 = tabHost.newTabSpec("t3");
        
        t1.setIndicator("点菜");
        t2.setIndicator("已点");
        t3.setIndicator("其它");
        
        t1.setContent(R.id.tab1);
        t2.setContent(R.id.tab2);
        t3.setContent(R.id.tab3);
        
        tabHost.addTab(t1);
        tabHost.addTab(t2);
        tabHost.addTab(t3);
    }
    
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        
//        return true;
//    }
//    
//    @Override
//    public boolean onQueryTextChange(String newText) {
////        if(TextUtils.isEmpty(newText)) {
////            listView.clearTextFilter();
////        } else {
////            listView.setFilterText(newText.toString());
////        }
////        return false;
//        Object[] obj = searchItem(newText);
//        updateLayout(obj);
//        return false;
//
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String arg0) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//    
//    public void initActionbar() {
//        // 自定义标题栏
//        getActionBar().setDisplayShowHomeEnabled(false);
//        getActionBar().setDisplayShowTitleEnabled(false);
//        getActionBar().setDisplayShowCustomEnabled(true);
//        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View mTitleView = mInflater.inflate(R.layout.activity_opentable_tab1,
//                null);
//        getActionBar().setCustomView(
//                mTitleView,
//                new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,
//                        LayoutParams.WRAP_CONTENT));
//        searchView = (SearchView) mTitleView.findViewById(R.id.search_view);
//    }

    public List<String> loadData() {
        mAllList.add("aa");
        mAllList.add("ddfa");
        mAllList.add("qw");
        mAllList.add("sd");
        mAllList.add("fd");
        mAllList.add("cf");
        mAllList.add("re");
        mAllList.add("re");
        mAllList.add("gb了个gb");
        mAllList.add("攻暴鸭丁");
        mAllList.add("宫保鸡丁");
        return mAllList;
    }
    
//    public Object[] searchItem(String name) {
//        ContrastPinyin cp = new ContrastPinyin();
//        ArrayList<String> mSearchList = new ArrayList<String>();
//        for (int i = 0; i < mAllList.size(); i++) {
//            
//            String pinyin = cp.getSpells(mAllList.get(i));
//            int index = pinyin.indexOf(name);
//            // 存在匹配的数据
//            if (index != -1) {
//                mSearchList.add(mAllList.get(i));
//            }
//        }
//        return mSearchList.toArray();
//    }
//
//    public void updateLayout(Object[] obj) {
//        listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
//                android.R.layout.simple_expandable_list_item_1, obj));
//    }
//
}
