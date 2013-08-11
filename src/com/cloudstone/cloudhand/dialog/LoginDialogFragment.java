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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.MainActivity;
import com.cloudstone.cloudhand.data.User;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.exception.HttpStatusError;
import com.cloudstone.cloudhand.network.api.ListUserNameApi;
import com.cloudstone.cloudhand.network.api.LoginApi;
import com.cloudstone.cloudhand.network.api.LoginApi.LoginApiCallback;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;
import com.cloudstone.cloudhand.util.UIUtils;

/**
 * @author xuhongfeng
 *
 */
public class LoginDialogFragment extends BaseAlertDialogFragment {
    private AutoCompleteTextView textView;
    private EditText password;
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
        textView = (AutoCompleteTextView)view.findViewById(R.id.login_dialog_userName);
        password = (EditText)view.findViewById(R.id.login_dialog_password);
        btnLogin = (Button)view.findViewById(R.id.btnLogin);
        
        btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new LoginApi(textView.getText().toString(), password.getText().toString()).asyncCall(new LoginApiCallback() {

					@Override
					public void onSuccess(User result) {
						MainActivity.tvLoginStatus.setText(result.getName());
						dismiss();
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						System.out.println("onFinish");
					}

					@Override
					protected void onAuthFailed() {
						// TODO Auto-generated method stub
						System.out.println("onAuthFailed");
					}

					@Override
					protected void onError(ApiException e) {
						// TODO Auto-generated method stub
						System.out.println("onError");
					}
				});
				
			}
		});
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }
    
    private void render() {
        textView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, userNames));
    }
}
