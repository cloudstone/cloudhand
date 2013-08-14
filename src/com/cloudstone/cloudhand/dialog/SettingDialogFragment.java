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
import com.cloudstone.cloudhand.logic.URLLogic;
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
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
            	System.out.println("saveURL");
            	URLLogic.getInstance().saveURL(editUrl.getText().toString());
            	System.out.println(URLLogic.getInstance().isSetting());
            	System.out.println(new UrlConst().BASE_URL);
                dismiss();
            }
        });
        
        btnCancle.setOnClickListener(new OnClickListener() {
        
            @Override
            public void onClick(View v) {
            	URLLogic.getInstance().clearURLInfo();
            	System.out.println(new UrlConst().BASE_URL);
                dismiss();
            }
        });
    }
    
}