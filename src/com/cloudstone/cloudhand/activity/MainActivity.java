/**
 * @(#)LoginActivity.java, 2013-7-6. 
 * 
 */
package com.cloudstone.cloudhand.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.dialog.LoginDialogFragment;

/**
 * @author xuhongchuan
 *
 */
public class MainActivity extends BaseActivity {
	private Button btnLogin;
	private Button btnOpenTable;
	private Button btnTabelInfo;
	private Button btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnOpenTable = (Button)findViewById(R.id.btnOpenTable);
        btnTabelInfo = (Button)findViewById(R.id.btnTabelInfo);
        btnSetting= (Button)findViewById(R.id.btnSetting);
        
        
        
        btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LoginDialogFragment dialog = new LoginDialogFragment();
				dialog.show(getFragmentManager(), "loginDialog");
			}
		});
    }
    
//    class btnOnClickListener implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			if(v == btnLogin) {
//				LayoutInflater li = LayoutInflater.from(MainActivity.this);
//		        View view = li.inflate(R.layout.style_login_dialog, null);
//		        AlertDialog builder = new AlertDialog.Builder(MainActivity.this).create();  
//		        builder.setView(view);
//		        builder.show();
//			}
//		}
//    	
//    }
}
