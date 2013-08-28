package com.cloudstone.cloudhand.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;

/**
 * 
 * @author xhc
 *
 */
public class ClearTableFinishDialogFragment extends BaseAlertDialogFragment {
    private Button btnConfirm;
    private TextView tvMessage;
    private View ivIcon;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_base_confirm, container, false);
        btnConfirm = (Button)view.findViewById(R.id.btn_confirm);
        tvMessage = (TextView)view.findViewById(R.id.tv_message);
        ivIcon = (ImageView)view.findViewById(R.id.iv_icon);
        ivIcon.setBackgroundResource(R.drawable.icon_finish);
        tvMessage.setText(R.string.message_clear_table_finish);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        
    }
    
    @Override
    public void dismiss() {
    	super.dismiss();
    	Intent intent = new Intent();
        intent.setAction("update");
        getActivity().sendBroadcast(intent);
    }
    
}