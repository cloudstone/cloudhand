/**
 * @(#)LoginActivity.java, 2013-7-6. 
 * 
 */
package com.cloudstone.cloudhand.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.dialog.LoginDialogFragment;

/**
 * 
 * @author xhc
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
        setContentView(R.layout.activity_main);
        
        btnLogin = (Button)findViewById(R.id.button_login);
        btnOpenTable = (Button)findViewById(R.id.button_login);
        btnTabelInfo = (Button)findViewById(R.id.button_open_table);
        btnSetting= (Button)findViewById(R.id.btn_setting);
        
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDialogFragment dialog = new LoginDialogFragment();
                dialog.show(getFragmentManager(), "loginDialog");
            }
        });
    }
}
