package com.cloudstone.cloudhand.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.MainActivity;
import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.logic.MiscLogic;
import com.cloudstone.cloudhand.logic.UserLogic;

/**
 * 
 * @author xhc
 *
 */
public class SettingDialogFragment extends BaseAlertDialogFragment {
    private Button btnConfirm;
    private Button btnCancle;
    private EditText editUrl;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_setting, container, false);
        btnConfirm = (Button)view.findViewById(R.id.button_confirm);
        btnCancle = (Button)view.findViewById(R.id.button_cancle);
        editUrl = (EditText)view.findViewById(R.id.edit_URL);
        if(MiscLogic.getInstance().isSetting()) {
        	editUrl.setText(MiscLogic.getInstance().getServerIP());
        } else {
        	editUrl.setText("192.168.0.101");
        }
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
            	MiscLogic.getInstance().saveServerIP(editUrl.getText().toString());
            	System.out.println(MiscLogic.getInstance().getServerIP());
            	System.out.println(MiscLogic.getInstance().getServerUrl());
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