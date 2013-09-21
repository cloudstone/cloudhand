package com.cloudstone.cloudhand.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;

/**
 * 
 * @author xhc
 *
 */
public class SubmitSuccessDialogFragment extends DialogFragment {
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
        ivIcon.setBackgroundResource(R.drawable.ic_success);
        tvMessage.setText(R.string.submit_success);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                ((OpenTableActivity)(getActivity())).finish();
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