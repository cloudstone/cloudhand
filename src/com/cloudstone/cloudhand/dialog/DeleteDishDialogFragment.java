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
public class DeleteDishDialogFragment extends DialogFragment {
    private Button btnConfirm;
    private Button btnCancle;
    private ImageView ivIcon;
    private TextView tvMessage;
    
    private int dishId;
    
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
        tvMessage.setText(R.string.message_delete_dish);
        
        dishId = getArguments().getInt("dishId");
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                //删除一道菜后刷新界面
                ((OpenTableActivity)(getActivity())).setDishCount(dishId, 0);
                Intent intent = new Intent();
                intent.setAction("update");
                getActivity().sendBroadcast(intent);
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