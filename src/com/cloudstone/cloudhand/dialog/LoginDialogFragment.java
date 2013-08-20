/**
 * @(#)LoginDialogFragment.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.cloudhand.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.MainActivity;
import com.cloudstone.cloudhand.data.User;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.logic.UserLogic;
import com.cloudstone.cloudhand.network.api.ListUserNameApi;
import com.cloudstone.cloudhand.network.api.LoginApi;
import com.cloudstone.cloudhand.network.api.LoginApi.LoginApiCallback;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;
import com.cloudstone.cloudhand.util.UIUtils;

/**
 * 
 * @author xhc
 *
 */
public class LoginDialogFragment extends BaseAlertDialogFragment {
    private Spinner tvUserName;
    private EditText tvPassword;
    private Button btnLogin;
    
    private String[] userNames = new String[0];
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_login, container, false);
        tvUserName = (Spinner)view.findViewById(R.id.sp_user_name);
        tvPassword = (EditText)view.findViewById(R.id.ev_password);
        btnLogin = (Button)view.findViewById(R.id.btn_login);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        //获取用户名列表
        new ListUserNameApi().asyncCall(new IApiCallback<String[]>() {
            
            @Override
            public void onSuccess(String[] result) {
                LoginDialogFragment.this.userNames = result;
                LoginDialogFragment.this.render();
            }
            
            @Override
            public void onFinish() {
            }
            
            @Override
            public void onFailed(ApiException exception) {
                UIUtils.toast(getActivity(), R.string.error_list_user_names_failed);
                L.e(LoginDialogFragment.class, exception);
            }
        });
        
        //登录
        btnLogin.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                new LoginApi(tvUserName.getSelectedItem().toString(), tvPassword.getText().toString()).asyncCall(new LoginApiCallback() {

                    @Override
                    public void onSuccess(User result) {
                        UserLogic.getInstance().saveUser(result); //保存用户名
                        ((MainActivity) getActivity()).setTvLoginStatus(result.getName()); //修改主界面的登录状态为用户名
                        dismiss();
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    protected void onAuthFailed() {
                    	Toast.makeText(getActivity(), R.string.wrong_password, 0).show();
                        
                    }

                    @Override
                    protected void onError(ApiException e) {
                    	L.e(this, e);
                        Toast.makeText(getActivity(), R.string.Logon_failed, 0).show();
                        
                    }
                    
                });
            }
        });
    }
    
    private void render() {
        //创建一个下拉框适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item,userNames); 
        //设置下拉列表风格
        adapter.setDropDownViewResource(R.layout.view_dropdown_item_line);
        
        //关联适配器到用户名下拉框
        tvUserName.setAdapter(adapter);
    }
}
