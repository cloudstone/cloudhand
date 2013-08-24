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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.MainActivity;
import com.cloudstone.cloudhand.data.User;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.logic.MiscLogic;
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
    private CheckBox cbRemember;
    
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
        cbRemember = (CheckBox)view.findViewById(R.id.cb_remember);
        
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
                
                //用户名自动选择上一次登录用户
                if(MiscLogic.getInstance().getCurrentUser() != "") {
                    for(int i = 0; i < tvUserName.getCount(); i++) {
                        if(tvUserName.getItemAtPosition(i).toString().equals
                                (MiscLogic.getInstance().getCurrentUser())) {
                            tvUserName.setSelection(i);
                            break;
                        }
                    }
                }
                
                //密码框自动填上密码
                tvPassword.setText(MiscLogic.getInstance()
                    .getPassword("_" + tvUserName.getItemAtPosition(0).toString()));
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
                        
                        //记住当前登录用户
                        MiscLogic.getInstance().saveCurrentUser(tvUserName.getSelectedItem().toString());
                        
                        //存入数据
                        if(cbRemember.isChecked()) {
                            MiscLogic.getInstance().savePassword("_" + tvUserName.getSelectedItem().toString() , tvPassword.getText().toString());
                        } else {
                            MiscLogic.getInstance().removePassword("_" + tvUserName.getSelectedItem().toString());
                        }
                        
                        dismiss();
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    protected void onAuthFailed() {
                        Toast.makeText(getActivity(), R.string.wrong_password, Toast.LENGTH_SHORT).show();
                        
                    }

                    @Override
                    protected void onError(ApiException e) {
                        L.e(this, e);
                        Toast.makeText(getActivity(), R.string.Logon_failed, Toast.LENGTH_SHORT).show();
                        
                    }
                    
                });
            }
        });
        
        //改变用户名下拉选型的选中项的事件
        tvUserName.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
                //改变密码框的值
                tvPassword.setText(MiscLogic.getInstance().getPassword("_" + tvUserName.getSelectedItem().toString()));
                //判断记住密码是否选中
                if(MiscLogic.getInstance().getPassword("_" + tvUserName.getSelectedItem().toString()) != "") {
                    cbRemember.setChecked(true);
                } else {
                    cbRemember.setChecked(false);
                }
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                
            }
        });
    }
    
    
    private void render() {
        //创建一个下拉框适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_base_dropdown_list,userNames); 
        //设置下拉列表风格
        adapter.setDropDownViewResource(R.layout.view_base_dropdown_list_line);
        
        //关联适配器到用户名下拉框
        tvUserName.setAdapter(adapter);
    }
    
}
