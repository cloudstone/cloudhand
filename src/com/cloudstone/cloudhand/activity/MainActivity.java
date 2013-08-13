/**
 * @(#)LoginActivity.java, 2013-7-6. 
 * 
 */
package com.cloudstone.cloudhand.activity;

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
    private Button btnSetting;
    private TextView tvLoginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnLogin = (Button)findViewById(R.id.button_login);
        btnOpenTable = (Button)findViewById(R.id.button_open_table);
        btnTabelInfo = (Button)findViewById(R.id.button_table_info);
        btnSetting= (Button)findViewById(R.id.btn_setting);
        tvLoginStatus = (TextView)findViewById(R.id.text_login_status);
        
        //初始化登录状态
        if(UserLogic.getInstance().isLogin()) {
            tvLoginStatus.setText(UserLogic.getInstance().getUser().getName());
        } else {
            tvLoginStatus.setText(getString(R.string.tip_not_login));
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
    }
    
    //显示登录对话框
    public void showLoginDialog() {
        LoginDialogFragment dialog = new LoginDialogFragment();
        dialog.show(getFragmentManager(), "loginDialog");
    }
    
    public void setTvLoginStatus(String userName) {
        tvLoginStatus.setText(userName);
    }
}
