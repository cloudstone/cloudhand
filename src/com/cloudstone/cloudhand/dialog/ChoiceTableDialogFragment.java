package com.cloudstone.cloudhand.dialog;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.ListTableApi;
import com.cloudstone.cloudhand.network.api.OccupyTableApi;
import com.cloudstone.cloudhand.network.api.OccupyTableApi.OccupyTableCalback;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;

/**
 * 
 * @author xhc
 *
 */
public class ChoiceTableDialogFragment extends BaseAlertDialogFragment {
    private AutoCompleteTextView tvTableName;
    private EditText tvCustomerNumber;
    private Button btnConfirm;
    private Button btnCancle;
    
    private String[] tableNames;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_choice_table, container, false);
        tvTableName = (AutoCompleteTextView)view.findViewById(R.id.text_table_name);
        tvCustomerNumber = (EditText)view.findViewById(R.id.edit_password);
        btnConfirm = (Button)view.findViewById(R.id.button_confirm);
        btnCancle = (Button)view.findViewById(R.id.button_cancle);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
      //获取桌况
        new ListTableApi().asyncCall(new IApiCallback<List<Table>>() {

            @Override
            public void onSuccess(List<Table> result) {
                tableNames = new String[result.size()];
                for(int i = 0;i < result.size();i++) {
                    tableNames[i] = result.get(i).getName();
                    System.out.println(tableNames[i]);
                }
                render();
            }

            @Override
            public void onFailed(ApiException exception) {
                System.out.println("onFailed");
                Toast.makeText(getActivity(), R.string.error_list_table_info_failed, 0).show();
                L.e(ChoiceTableDialogFragment.class, exception);
            }

            @Override
            public void onFinish() {
                
            }

        });
        
        
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                int tableId = 0;
                for(int i = 0;i < tableNames.length;i++) {
                    if(tvTableName.getText().toString().equals(tableNames[i])) {
                        tableId = i + 1;
                        break;
                    }
                }
                System.out.println("tableId -->" + tableId);
                if(tableId > 0) {
                    if(isInt(tvCustomerNumber.getText().toString())) {
                        int customerNumber = Integer.parseInt(tvCustomerNumber.getText().toString());
                        if(customerNumber != 0){
                            new OccupyTableApi(tableId,customerNumber).asyncCall(new OccupyTableCalback() {
                                
                                @Override
                                public void onSuccess(Table result) {
                                    System.out.println("onSuccess");
                                }
                                
                                @Override
                                public void onFinish() {
                                    
                                }
                                
                                @Override
                                protected void onOccupied() {
                                    
                                }
                                
                                @Override
                                protected void onError(ApiException e) {
                                    
                                }
                            });
                            
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), OpenTableActivity.class);
                            startActivity(intent);
                            dismiss();
                        } else {
                            Toast.makeText(getActivity(), R.string.choice_table_error3, 0).show();
                        }
                        
                    } else {
                        Toast.makeText(getActivity(), R.string.choice_table_error2, 0).show();
                    }
                    
                } else {
                    Toast.makeText(getActivity(), R.string.choice_table_error1, 0).show();
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
    
    private boolean isInt(String str) { 
        try { 
            Integer.parseInt(str) ; 
            return true ; 
        } catch (NumberFormatException e) { 
            return false;
        }
    }
    
    private void render() {
        //创建一个下拉框适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_base_dropdown_list, tableNames); 
        //设置下拉列表风格
        adapter.setDropDownViewResource(R.layout.view_base_dropdown_list_line);
        
        //关联适配器到用户名下拉框
        tvTableName.setAdapter(adapter);
    }
}
