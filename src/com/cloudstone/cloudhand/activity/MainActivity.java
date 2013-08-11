/**
 * @(#)LoginActivity.java, 2013-7-6. 
 * 
 */
package com.cloudstone.cloudhand.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

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
    public static TextView tvLoginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnOpenTable = (Button)findViewById(R.id.btnOpenTable);
        btnTabelInfo = (Button)findViewById(R.id.btnTabelInfo);
        btnSetting= (Button)findViewById(R.id.btnSetting);
        
        tvLoginStatus = (TextView)findViewById(R.id.tvLoginStatus);
        
        
        
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDialogFragment dialog = new LoginDialogFragment();
                dialog.show(getFragmentManager(), "loginDialog");
            }
        });
        
        btnOpenTable.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, OpenTableActivity.class);
                MainActivity.this.startActivity(intent);
            }
            
        });
    }
}
