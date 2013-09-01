package com.cloudstone.cloudhand.dialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.PerformanceTestCase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Filter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
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
import com.cloudstone.cloudhand.pinyin.ContrastPinyin;
import com.cloudstone.cloudhand.util.L;
import com.cloudstone.cloudhand.view.ChooseTableItem;
import com.cloudstone.cloudhand.view.ChooseTableItem.ChooseTableItemListener;

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
        
//        tvTableName.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//				System.out.println("onTextChanged");
////				searchItem(tvTableName.getText().toString());
////				render();
//				tvTableName.showDropDown();
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//					int arg3) {
//			}
//			
//			@Override
//			public void afterTextChanged(Editable arg0) {
//				tvTableName.showDropDown();
//			}
//		});
        
        return view;
    }
    
    public void searchItem(String keywords) {
    	ContrastPinyin contrastPinyin = new ContrastPinyin();
    	List<Table> result = new ArrayList<Table>();
    	
    	for (int i = 0; i < tableList.size(); i++) {
            int index = 0;;
            if(contrastPinyin.isContain(keywords)) {
                index = tableList.get(i).getName().indexOf(keywords);
            } else {
                String pinyin = contrastPinyin.getSpells(tableList.get(i).getName());
                index = pinyin.indexOf(keywords.toLowerCase());
            }
            // 存在匹配的数据
            if (index == -1) {
            	tableList.remove(i);
            }
            System.out.println(index);
        }
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
    	String[] tableName = new String[tableList.size()];
    	for(int i = 0; i < tableList.size(); i++) {
    		tableName[i] = tableList.get(i).getName();
    	}
        //创建一个下拉框适配器
    	ArrayAdapter<String> ada = new ArrayAdapter<String>(getActivity(), R.layout.view_base_dropdown_list_line, tableName);
//        TableAdapter adapter = new TableAdapter(getActivity(), R.layout.view_base_dropdown_list_line, tableList); 
    	TableAdapter adapter = new TableAdapter();
        //关联适配器到用户名下拉框
        tvTableName.setAdapter(ada);
    }
    
    private class TableAdapter extends BaseAdapter implements Filterable, ChooseTableItemListener{
    	

//        public TableAdapter(Context context, int textViewResourceId,
//				List<Table> objects) {
//			super(context, textViewResourceId, objects);
//		}

		@Override
        public int getCount() {
            return tableList.size();
        }

        @Override
        public Table getItem(int position) {
            return tableList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	ChooseTableItem view = (ChooseTableItem) convertView;
//            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
            if(view == null) {
//                linearLayout = new LinearLayout(getContext());
//                convertView = layoutInflater.inflate(resource, linearLayout, true);
            	view = createView();
            }
//            tvItem = new TextView(getContext());
//            tvItem = (TextView)convertView.findViewById(R.id.tv_name);
//            tvItem.setText(tableList.get(position).getName());
            
            Table table = getItem(position);
            view.render(table.getName());
            bindView(view, position);
            return view;
            
//            holder.setOnClickListener(new OnClickListener() {
//                
//                @Override
//                public void onClick(View v) {
//                    //TODO 这里有问题，总是得到最后一个值，还不知道怎么解决。
//                    //TODO 不知道怎么把已点的值传给外面的AutoCompleteTextView
//                    tvTableName.setText(tvItem.getText());
//                    tvItem.setVisibility(0x00000004);
//                }
//            });
        }
        
        private ChooseTableItem createView() {
        	ChooseTableItem view = new ChooseTableItem(getActivity());
        	view.setListener(this);
        	return view;
        }
        
        private void bindView(ChooseTableItem view, int position) {
            Table table = getItem(position);
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                view.setTag(holder);
            }
            holder.setTable(table);
        }

		@Override
		public void onClick(ChooseTableItem view) {
			ViewHolder holder = (ViewHolder) view.getTag();
			Table table = holder.getTable();
			tvTableName.setText(table.getName());
		}

		@Override
		public android.widget.Filter getFilter() {
			// TODO Auto-generated method stub
			return null;
		}
        
    }
    
    private class ViewHolder {
        private Table table;

        public Table getTable() {
            return table;
        }

        public void setTable(Table table) {
            this.table = table;
        }
        
    }
    
}
