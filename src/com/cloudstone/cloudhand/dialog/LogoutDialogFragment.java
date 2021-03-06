package com.cloudstone.cloudhand.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.MainActivity;
import com.cloudstone.cloudhand.logic.UserLogic;

/**
 * 
 * @author xhc
 *
 */
public class LogoutDialogFragment extends BaseAlertDialogFragment {
    private ImageView ivIcon;
    private TextView tvMessage;
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
        View view = inflater.inflate(R.layout.dialog_base_confirm_cancle, container, false);
        
        btnConfirm = (Button)view.findViewById(R.id.btn_confirm);
        btnCancle = (Button)view.findViewById(R.id.btn_cancle);
        ivIcon = (ImageView)view.findViewById(R.id.iv_icon);
        tvMessage = (TextView)view.findViewById(R.id.tv_message);
        
        ivIcon.setBackgroundResource(R.drawable.ic_ask);
        tvMessage.setText(R.string.message_logout);
        
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        //确定
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                UserLogic.getInstance().logout(); //清空登录信息
                ((MainActivity) getActivity()).setTvLoginStatus(getString(R.string.tip_not_login));
                ((MainActivity) getActivity()).showLoginDialog(); //显示登录对话框
                dismiss();
            }
        });
        
        //取消
        btnCancle.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    
}