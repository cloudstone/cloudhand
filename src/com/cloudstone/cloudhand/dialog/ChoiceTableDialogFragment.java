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
        btnConfirm = (Button)view.findViewById(R.id.btn_confirm);
        btnCancle = (Button)view.findViewById(R.id.btn_cancle);
        
        //输入桌名文本框被点时自动获取桌子列表
        tvTableName.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if(tvTableName.getText().toString().equals("")) {
                    tvTableName.showDropDown();
                }
            }
        });
        
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        //获取桌况
        new ListTableApi().asyncCall(new IApiCallback<List<Table>>() {

            @Override
            public void onSuccess(List<Table> result) {
                //过滤掉非空闲状态的桌子
                for(int i = 0;i < result.size();i++) {
                    if(result.get(i).getStatus() != 0) {
                        result.remove(i);
                    }
                }
                
                tableNames = new String[result.size()];
                for(int i = 0;i < result.size();i++) {
                    tableNames[i] = result.get(i).getName();
                }
                render();
            }

            @Override
            public void onFailed(ApiException exception) {
                Toast.makeText(getActivity(), R.string.error_list_table_info_failed, 0).show();
                L.e(ChoiceTableDialogFragment.class, exception);
            }

            @Override
            public void onFinish() {
                
            }

        });
        
        //确定
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                int tableId = 0;
                //获取输入的桌名的ID
                for(int i = 0;i < tableNames.length;i++) {
                    if(tvTableName.getText().toString().equals(tableNames[i])) {
                        tableId = i + 1;
                        break;
                    }
                }
                //判断输入的桌子是否存在
                if(tableId > 0) {
                    //判断输入的顾客人数是否为非0正整数
                    if(isInt(tvCustomerNumber.getText().toString())) {
                        int customerNumber = Integer.parseInt(tvCustomerNumber.getText().toString());
                        new OccupyTableApi(tableId,customerNumber).asyncCall(new OccupyTableCalback() {
                            
                            @Override
                            public void onSuccess(Table result) {
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), OpenTableActivity.class);
                                startActivity(intent);
                                dismiss();
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
                        
                    } else {
                        Toast.makeText(getActivity(), R.string.choice_table_customer_number_error, Toast.LENGTH_SHORT).show();
                    }
                    
                } else {
                    Toast.makeText(getActivity(), R.string.choice_table_table_nonentity_error, Toast.LENGTH_SHORT).show();
                }
                
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
    
    //判断一个字符串是否为非0正整数
    private boolean isInt(String str) { 
        try { 
            if(Integer.parseInt(str) > 0) {
                return true; 
            } else {
                return false;
            }
        } catch (NumberFormatException e) { 
            return false;
        }
    }
    
    private void render() {
        //创建一个下拉框适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_base_dropdown_list_line, tableNames); 
        //设置下拉列表风格
        adapter.setDropDownViewResource(R.layout.view_base_dropdown_list_line);
        
        //关联适配器到用户名下拉框
        tvTableName.setAdapter(adapter);
    }
}
