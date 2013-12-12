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
        //判断是否勾选了无网络
        if(MiscLogic.getInstance().getNoNet()) {
            LoginDialogFragment.this.userNames = UserLogic.getInstance().getAllUser(getActivity()); //从数据库获取用户名
            LoginDialogFragment.this.render();
        } else {
            //获取用户名列表
            new ListUserNameApi().asyncCall(new IApiCallback<String[]>() {
                
                @Override
                public void onSuccess(String[] result) {
                    LoginDialogFragment.this.userNames = result;
                    LoginDialogFragment.this.render();
                    UserLogic.getInstance().insertUser(getActivity(), result); //将用户名写入数据库
                    
                    //用户名自动选择上一次登录用户
                    String currentUser = MiscLogic.getInstance().getCurrentUser();
                    if(!currentUser.equals("")) {
                        for(int i = 0; i < userNames.length; i++) {
                            if(userNames[i].equals(currentUser)) {
                                tvUserName.setSelection(i);
                                break;
                             }
                         }
                    }
                    
                    //密码框自动填上密码
                    String userName = tvUserName.getSelectedItem().toString();
                    String password = MiscLogic.getInstance().getPassword(userName);
                    tvPassword.setText(password);
                }
                
                @Override
                public void onFinish() {}
                
                @Override
                public void onFailed(ApiException exception) {
                    UIUtils.toast(getActivity(), R.string.error_list_user_names_failed);
                    L.e(LoginDialogFragment.class, exception);
                }
            });
        }
        
        //登录
        btnLogin.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                //判断是否勾选了无网络
                if(MiscLogic.getInstance().getNoNet()) {
                    ((MainActivity) getActivity()).setTvLoginStatus(tvUserName.getSelectedItem().toString()); //修改主界面的登录状态为用户名
                    dismiss();
                } else {
                    new LoginApi(tvUserName.getSelectedItem().toString(), tvPassword.getText().toString()).asyncCall(new LoginApiCallback() {

                        @Override
                        public void onSuccess(User result) {
                            getUserLogic().saveUser(result); //保存用户名
                            ((MainActivity) getActivity()).setTvLoginStatus(result.getName()); //修改主界面的登录状态为用户名
                            
                            String userName = tvUserName.getSelectedItem().toString();
                            String password = tvPassword.getText().toString();
                            //记住当前登录用户
                            getMiscLogic().saveCurrentUser(userName);
                            
                            //存入数据
                            if(cbRemember.isChecked()) {
                                getMiscLogic().savePassword(userName, password);
                            } else {
                                getMiscLogic().removePassword(userName);
                            }
                            
                            dismiss();
                        }

                        @Override
                        public void onFinish() {}

                        @Override
                        protected void onAuthFailed() {
                            UIUtils.toast(getActivity(), R.string.wrong_password);
                        }

                        @Override
                        protected void onError(ApiException e) {
                            L.e(this, e);
                            UIUtils.toast(getActivity(), R.string.Login_failed);
                        }
                    });
                }
            }
        });
        
        //改变用户名下拉选型的选中项的事件
        tvUserName.setOnItemSelectedListener(new OnItemSelectedListener() {

            //TODO argXXX
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v,
                    int arg2, long arg3) {
                String selectedName = tvUserName.getSelectedItem().toString();
                String password = getMiscLogic().getPassword(selectedName);
                //改变密码框的值
                tvPassword.setText(password);
                //判断记住密码是否选中
                cbRemember.setChecked(!password.equals(""));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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
