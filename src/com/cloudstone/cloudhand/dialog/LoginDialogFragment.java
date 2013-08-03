/**
 * @(#)LoginDialogFragment.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.cloudhand.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.ListUserNameApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;
import com.cloudstone.cloudhand.util.UIUtils;

/**
 * @author xuhongfeng
 *
 */
public class LoginDialogFragment extends BaseAlertDialogFragment {
    private AutoCompleteTextView textView;
    
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
