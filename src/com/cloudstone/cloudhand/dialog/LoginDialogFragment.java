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

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.MainActivity;
import com.cloudstone.cloudhand.data.User;
import com.cloudstone.cloudhand.exception.ApiException;
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
        tvUserName = (Spinner)view.findViewById(R.id.text_user_name_dialog);
        btnLogin = (Button)view.findViewById(R.id.button_login_dialog);
        tvPassword = (EditText)view.findViewById(R.id.text_password_dialog);
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
                        //修改主界面的登录用户
                        ((MainActivity) getActivity()).setTvLoginStatus(result.getName());
                    }

                    @Override
                    public void onFinish() {
                        dismiss();
                    }

                    @Override
                    protected void onAuthFailed() {
                        // TODO Auto-generated method stub
                        
                    }

                    @Override
                    protected void onError(ApiException e) {
                        // TODO Auto-generated method stub
                        
                    }
                    
                });
            }
        });
    }
    
    private void render() {
        //创建一个下拉框适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,userNames); 
        //设置下拉列表风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //关联适配器到用户名下拉框
        tvUserName.setAdapter(adapter);
    }
}
