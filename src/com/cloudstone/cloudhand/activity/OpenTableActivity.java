package com.cloudstone.cloudhand.activity;

import com.cloudstone.cloudhand.R;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class OpenTableActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.test);
        
        /*TabHost tabHost = getTabHost();
		
		LayoutInflater lay = LayoutInflater.from(this);
		View v1 = lay.inflate(R.layout.tab1, tabHost.getTabContentView(), true);
		View v2 = lay.inflate(R.layout.tab2, tabHost.getTabContentView(), true);
		View v3 = lay.inflate(R.layout.tab3, tabHost.getTabContentView(), true);
		
		TabSpec t1 = tabHost.newTabSpec("t1");
		TabSpec t2 = tabHost.newTabSpec("t2");
		TabSpec t3 = tabHost.newTabSpec("t3");
		
		t1.setIndicator("xuan1");
		t2.setIndicator("xuan2");
		t3.setIndicator("xuan3");
		
		t1.setContent(R.id.tab1);
		t2.setContent(R.id.tab2);
		t3.setContent(R.id.tab3);
		
		tabHost.addTab(t1);
		tabHost.addTab(t2);
		tabHost.addTab(t3);*/
	}

}
