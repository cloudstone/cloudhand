package com.cloudstone.cloudhand.dialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.OccupyTableApi;
import com.cloudstone.cloudhand.network.api.OccupyTableApi.OccupyTableCalback;
import com.cloudstone.cloudhand.util.L;

/**
 * 
 * @author xhc
 *
 */
@SuppressLint("ValidFragment")
public class InputCustomerNumberDialogFragment extends BaseAlertDialogFragment {
    private Button btnConfirm;
    private Button btnCancle;
    private EditText tvInput;
    
    private int tableId;
    
    public InputCustomerNumberDialogFragment(int tableId) {
        this.tableId = tableId;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_input_customer_number, container, false);
        btnConfirm = (Button)view.findViewById(R.id.btn_confirm);
        btnCancle = (Button)view.findViewById(R.id.btn_cancle);
        tvInput = (EditText)view.findViewById(R.id.tv_customet_number);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        btnConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //判断输入的顾客人数是否为非0正整数
                String inputCustomerNumber = tvInput.getText().toString();
                if(isInt(inputCustomerNumber)) {
                    int customerNumber = Integer.parseInt(inputCustomerNumber);
                    new OccupyTableApi(tableId,customerNumber).asyncCall(new OccupyTableCalback() {
                        
                        @Override
                        public void onSuccess(Table result) {
                            //打开点餐界面
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), OpenTableActivity.class);
                            startActivity(intent);
                            
                            //关闭桌况界面
                            intent = new Intent();
                            intent.setAction("tableInfoDismiss");
                            getActivity().sendBroadcast(intent);
                            
                            dismiss();
                        }
                        
                        @Override
                        public void onFinish() {}
                        
                        @Override
                        protected void onOccupied() {}
                        
                        @Override
                        protected void onError(ApiException exception) {
                            Toast.makeText(getActivity(), R.string.error_open_table_failed, Toast.LENGTH_SHORT).show();
                            L.e(InputCustomerNumberDialogFragment.this, exception);
                        }
                    });
                    
                } else {
                    Toast.makeText(getActivity(), R.string.choose_table_customer_number_error, Toast.LENGTH_SHORT).show();
                }
                
            }
           
        });
        
        btnCancle.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    
    //判断一个字符串是否为非0正整数
    private boolean isInt(String str) { 
        try { 
            return Integer.parseInt(str) > 0;
        } catch (NumberFormatException e) { 
            return false;
        }
    }
    
}