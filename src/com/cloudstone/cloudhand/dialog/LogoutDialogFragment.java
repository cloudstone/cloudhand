package com.cloudstone.cloudhand.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
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
public class LogoutDialogFragment extends BaseAlertDialogFragment {
    private Button btnConfirm;
    private Button btnCancle;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_logout, container, false);
        btnConfirm = (Button)view.findViewById(R.id.button_confirm);
        btnCancle = (Button)view.findViewById(R.id.button_cancle);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                UserLogic.getInstance().logout(); //清空登录信息
                ((MainActivity) getActivity()).setTvLoginStatus(getString(R.string.tip_not_login));
                ((MainActivity) getActivity()).showLoginDialog(); //显示登录对话框
                dismiss();
            }
        });
        
            btnCancle.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    
}