package com.cloudstone.cloudhand.dialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;
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
public class ChooseTableDialogFragment extends BaseAlertDialogFragment {
    private AutoCompleteTextView tvTableName;
    private EditText tvCustomerNumber;
    private Button btnConfirm;
    private Button btnCancle;
    
    private List<Table> tableList = new ArrayList<Table>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_choose_table, container, false);
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
                Iterator<Table> it = result.iterator();
                while (it.hasNext()) {
                    Table table = it.next();
                    if(table.getStatus() == 0) {
                        tableList.add(table);
                    }
                }
                render();
            }

            @Override
            public void onFailed(ApiException exception) {
                Toast.makeText(getActivity(), R.string.error_list_table_info_failed, Toast.LENGTH_SHORT).show();
                L.e(ChooseTableDialogFragment.class, exception);
            }

            @Override
            public void onFinish() {}

        });
        
        //确定
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                int tableId = 0;
                String tableName = tvTableName.getText().toString();
                String inputCustomerNumber = tvCustomerNumber.getText().toString();
                //获取输入的桌名的ID
                for(int i = 0;i < tableList.size();i++) {
                    if(tableName.equals(tableList.get(i).getName())) {
                        tableId = tableList.get(i).getId();
                        break;
                    }
                }
                //判断输入的桌子是否存在
                if(tableId > 0) {
                    //判断输入的顾客人数是否为非0正整数
                    if(isInt(inputCustomerNumber)) {
                        int customerNumber = Integer.parseInt(inputCustomerNumber);
                        new OccupyTableApi(tableId, customerNumber).asyncCall(new OccupyTableCalback() {
                            
                            @Override
                            public void onSuccess(Table result) {
                                Intent intent = new Intent();
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
    
    private void render() {
        //创建一个下拉框适配器
        TableAdapter adapter = new TableAdapter(getActivity(),
                R.layout.view_base_dropdown_list_line, tableList); 
        //关联适配器到用户名下拉框
        tvTableName.setAdapter(adapter);
    }
    
    private class TableAdapter extends ArrayAdapter<Table> {
        private LinearLayout linearLayout;
        private int resource;
        private List<Table> data = new ArrayList<Table>();
        private LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        private TextView tvItem;
        
        public TableAdapter(Context context, int textViewResourceId,
                List<Table> data) {
            super(context, textViewResourceId, data);
            this.data = data;
            this.resource = textViewResourceId;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Table getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            
            if(convertView == null) {
                linearLayout = new LinearLayout(getContext());
                convertView = layoutInflater.inflate(resource, linearLayout, true);
            }
            tvItem = new TextView(getContext());
            tvItem = (TextView)convertView.findViewById(R.id.tv_name);
            tvItem.setText(data.get(position).getName());
            
            tvItem.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    //TODO 这里有问题，总是得到最后一个值，还不知道怎么解决。
                    //TODO 不知道怎么把已点的值传给外面的AutoCompleteTextView
                    tvTableName.setText(tvItem.getText());
                    tvItem.setVisibility(0x00000004);
                }
            });
            return convertView;
        }
        
    }
    
}
