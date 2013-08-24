/**
 * @(#)LoginActivity.java, 2013-7-6. 
 * 
 */
package com.cloudstone.cloudhand.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.dialog.LoginDialogFragment;
import com.cloudstone.cloudhand.dialog.LogoutDialogFragment;
import com.cloudstone.cloudhand.logic.UserLogic;

/**
 * 
 * @author xhc
 *
 */
public class MainActivity extends BaseActivity {
    private Button btnLogin;
    private Button btnOpenTable;
    private Button btnTabelInfo;
    private Button btnSettings;
    private TextView tvLoginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnOpenTable = (Button)findViewById(R.id.btn_open_table);
        btnTabelInfo = (Button)findViewById(R.id.btn_table_info);
        btnSettings= (Button)findViewById(R.id.btn_settings);
        tvLoginUser = (TextView)findViewById(R.id.tv_login_user);
        
        //初始化登录用户
        if(UserLogic.getInstance().isLogin()) {
            tvLoginUser.setText(UserLogic.getInstance().getUser().getName());
        } else {
            tvLoginUser.setText(getString(R.string.tip_not_login));
        }
        
        //登录
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserLogic.getInstance().isLogin()) {
                    //显示询问是否注销对话框
                    LogoutDialogFragment dialog = new LogoutDialogFragment();
                    dialog.show(getFragmentManager(), "logoutDialog");
                } else {
                    showLoginDialog();
                }
            }
        });
        
        //开台
        btnOpenTable.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, OpenTableActivity.class);
                startActivity(intent);
            }
        });
    }
    
    //显示登录对话框
    public void showLoginDialog() {
        LoginDialogFragment dialog = new LoginDialogFragment();
        dialog.show(getFragmentManager(), "loginDialog");
    }
    
    //修改登录用户
    public void setTvLoginStatus(String userName) {
        tvLoginUser.setText(userName);
    }
}
