package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;

public class OpenTableActivity extends BaseActivity {
    private TextView tvDianCan, tvYiDidan, tvOther;
    
    private FragmentManager fm;
    private FragmentTransaction ft;
    
    private LinearLayout content;
    
    public List<Map<String, String>> data;

    ArrayList<String> mAllList = new ArrayList<String>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opentable);
        
        content = (LinearLayout)findViewById(R.id.content);
        tvDianCan = (TextView)findViewById(R.id.tab1);
        tvYiDidan = (TextView)findViewById(R.id.tab2);
        tvOther = (TextView)findViewById(R.id.tab3);
        
        if(data == null) {
        	data = getData();
        }
        
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        
        ft.replace(R.id.content, new OpenTable_Tab4());
        ft.commit();
        
        tvDianCan.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                changeColor(v);
                
                ft = fm.beginTransaction();
                
//                ft.replace(R.id.content, new OpenTable_Tab1(data));
                ft.replace(R.id.content, new OpenTable_Tab4());
                ft.commit();
            }
        });
        
        tvYiDidan.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                changeColor(v);
                
                ft = fm.beginTransaction();
                
                ft.replace(R.id.content, new OpenTable_Tab2());
                ft.commit();
            }
        });

        tvOther.setOnClickListener(new OnClickListener() {
    
            @Override
            public void onClick(View v) {
                changeColor(v);
        
                ft = fm.beginTransaction();
        
                ft.replace(R.id.content, new OpenTable_Tab3());
                ft.commit();
        }
        });
    }
    
    public static List<Map<String, String>> getData() {
        List<Map<String, String>> listMaps = new ArrayList<Map<String, String>>();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("name", "鱼香肉丝");
        map1.put("price", "42");
        map1.put("amount", "0");
        
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("name", "香菇鸡块");
        map2.put("price", "30");
        map2.put("amount", "0");
        
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("name", "老冰棍");
        map3.put("price", "1.5");
        map3.put("amount", "0");

        
        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("name", "wahaha");
        map4.put("price", "15");
        map4.put("amount", "0");

        
        Map<String, String> map5 = new HashMap<String, String>();
        map5.put("name", "gb");
        map5.put("price", "23");
        map5.put("amount", "0");
        
        Map<String, String> map6 = new HashMap<String, String>();
        map6.put("name", "gb了个gb");
        map6.put("price", "14");
        map6.put("amount", "0");
        
        Map<String, String> map7 = new HashMap<String, String>();
        map7.put("name", "攻暴鸭丁");
        map7.put("price", "23");
        map7.put("amount", "0");
        
        Map<String, String> map8 = new HashMap<String, String>();
        map8.put("name", "宫保鸡丁");
        map8.put("price", "35");
        map8.put("amount", "0");
        
        listMaps.add(map1);
        listMaps.add(map2);
        listMaps.add(map3);
        listMaps.add(map4);
        listMaps.add(map5);
        listMaps.add(map6);
        listMaps.add(map7);
        listMaps.add(map8);
        
        return listMaps;
        
    }
        
    
    private void changeColor(View v) {
        tvDianCan.setBackgroundResource(R.drawable.bg_main);
        tvYiDidan.setBackgroundResource(R.drawable.bg_main);
        tvOther.setBackgroundResource(R.drawable.bg_main);
        
        v.setBackgroundResource(R.drawable.cover2);
    }

}
