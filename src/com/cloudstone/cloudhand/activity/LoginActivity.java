/**
 * @(#)LoginActivity.java, 2013-7-6. 
 * 
 */
package com.cloudstone.cloudhand.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.cloudstone.cloudhand.R;

/**
 * @author xuhongfeng
 *
 */
public class LoginActivity extends BaseActivity {
	Button btnLogin;
	Button btnKaiTai;
	Button btnTabelInfo;
	Button btnSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnKaiTai = (Button)findViewById(R.id.btnKaiTai);
        btnTabelInfo = (Button)findViewById(R.id.btnTabelInfo);
        btnSet= (Button)findViewById(R.id.btnSet);
        
        btnLogin.setOnClickListener(new btnOnClickListener());
    }
    
    class btnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if(v == btnLogin) {
				LayoutInflater li = LayoutInflater.from(LoginActivity.this);
		        View view = li.inflate(R.layout.login_dialog, null);
		        AlertDialog builder = new AlertDialog.Builder(LoginActivity.this).setTitle(  
		        		"登录").setPositiveButton("确定", null).setNegativeButton("取消",null).create();  
		        builder.setView(view);
		        builder.show();
			}
			
		}
    	
    }
}
