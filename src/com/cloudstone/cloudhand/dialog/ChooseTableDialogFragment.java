package com.cloudstone.cloudhand.dialog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.activity.TableInfoActivity;
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
public class ChooseTableDialogFragment extends BaseAlertDialogFragment {
    private Spinner spTableName;
    private EditText tvCustomerNumber;
    private Button btnConfirm;
    private Button btnCancel;
    private int tableId;
    
    private Map<String, Table> tableMap = new TreeMap<String, Table>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_choose_table, container, false);
        spTableName = (Spinner)view.findViewById(R.id.sp_table_name);
        tvCustomerNumber = (EditText)view.findViewById(R.id.edit_password);
        btnConfirm = (Button)view.findViewById(R.id.btn_confirm);
        btnCancel = (Button)view.findViewById(R.id.btn_cancel);
        
        if(getArguments() != null) {
            tableId = getArguments().getInt("tableId");
        }
        
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
                Iterator<Table> it = result.iterator();
                while (it.hasNext()) {
                    Table table = it.next();
                    if(table.getStatus() == 0) {
                        tableMap.put(table.getName(), table);
                    }
                }
                render();
                
                Iterator<Entry<String, Table>> iterator = tableMap.entrySet().iterator();
                int selection = 0;
                while (iterator.hasNext()) {
                    Entry<String, Table> entry = iterator.next();
                    if(entry.getValue().getId() == tableId) {
                        spTableName.setSelection(selection);
                        break;
                    }
                    selection++;
                }
            }

            @Override
            public void onFailed(ApiException exception) {
                Toast.makeText(getActivity(), R.string.error_list_table_info_failed, Toast.LENGTH_SHORT).show();
                L.e(ChooseTableDialogFragment.class, exception);
            }

            @Override
            public void onFinish() {
            }

        });
        
        //确定
        btnConfirm.setOnClickListener(new OnClickListener() {
            int tableId = 0;
            int customerNumber = 0;
            
            @Override
            public void onClick(View v) {
                
                String tableName = spTableName.getSelectedItem().toString();
                String inputCustomerNumber = tvCustomerNumber.getText().toString();
                
                //判断输入的桌子是否存在
                if(tableMap.get(tableName) != null) {
                    //判断输入的顾客人数是否为非0正整数
                    if(isInt(inputCustomerNumber)) {
                        customerNumber = Integer.parseInt(inputCustomerNumber);
                        tableId = tableMap.get(tableName).getId(); //获取输入的桌名的ID
                        new OccupyTableApi(tableId, customerNumber).asyncCall(new OccupyTableCalback() {
                            
                            @Override
                            public void onSuccess(Table result) {
                                //如果是从桌况进入点餐界面要刷新桌况
                                if(getActivity().getClass() == TableInfoActivity.class) {
                                    ((TableInfoActivity)(getActivity())).updateTables();
                                }
                                Bundle bundle = new Bundle();
                                bundle.putInt("tableId", tableId);
                                bundle.putInt("customerNumber", customerNumber);
                                Intent intent = new Intent();
                                intent.putExtras(bundle);
                                intent.setClass(getActivity(), OpenTableActivity.class);
                                startActivity(intent);
                                dismiss();
                            }
                            
                            @Override
                            public void onFinish() {}
                            
                            @Override
                            protected void onOccupied() {}
                            
                            @Override
                            protected void onError(ApiException exception) {
                                Toast.makeText(getActivity(), R.string.error_open_table_failed, Toast.LENGTH_SHORT).show();
                                L.e(ChooseTableDialogFragment.this, exception);
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), R.string.choose_table_customer_number_error, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.choose_table_table_nonentity_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        //取消
        btnCancel.setOnClickListener(new OnClickListener() {
            
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
    
    private void render() {
        String[] tableName = new String[tableMap.size()];
        Iterator<Entry<String, Table>> iterator = tableMap.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Entry<String, Table> entry = iterator.next();
            tableName[i] = entry.getValue().getName();
            i++;
        }
        //创建一个下拉框适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.view_base_dropdown_list, tableName);
        //设置下拉列表风格
        adapter.setDropDownViewResource(R.layout.view_base_dropdown_list_line);
        //关联适配器到用户名下拉框
        spTableName.setAdapter(adapter);
    }
    
}