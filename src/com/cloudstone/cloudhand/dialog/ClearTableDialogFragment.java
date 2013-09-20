package com.cloudstone.cloudhand.dialog;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.TableInfoActivity;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.ClearTableApi;
import com.cloudstone.cloudhand.network.api.GetOrderApi;
import com.cloudstone.cloudhand.network.api.ClearTableApi.ClearTableCalback;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;

/**
 * 
 * @author xhc
 *
 */
public class ClearTableDialogFragment extends BaseAlertDialogFragment {
    private Button btnConfirm;
    private Button btnCancel;
    private ImageView ivIcon;
    private TextView tvMessage;
    private int tableId;
    
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
        btnCancel = (Button)view.findViewById(R.id.btn_cancel);
        ivIcon = (ImageView)view.findViewById(R.id.iv_icon);
        tvMessage = (TextView)view.findViewById(R.id.tv_message);
        
        ivIcon.setBackgroundResource(R.drawable.ic_ask);
        tvMessage.setText(R.string.message_clear_table);
        
        tableId = getArguments().getInt("tableId");
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        btnConfirm.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                new ClearTableApi(tableId).asyncCall(new ClearTableCalback() {
                    
                    @Override
                    public void onSuccess(Table result) {
                        ((TableInfoActivity)(getActivity())).updateTables();
                        ClearTableSuccessDialogFragment dialog = new ClearTableSuccessDialogFragment();
                        dialog.show(getFragmentManager(), "clearTableSuccessDialogFragment");
                        dismiss();
                    }
                    
                    @Override
                    public void onFinish() {}
                                       
                    @Override
                    protected void onError(ApiException exception) {
                        Toast.makeText(getActivity(), R.string.error_clear_table_failed, Toast.LENGTH_SHORT).show();
                        L.e(ClearTableDialogFragment.this, exception);
                    }
                    
                    @Override
                    protected void onCleared() {}
                });
            }
        });
        
        btnCancel.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
            	int orderId = 0;
            	List<Table> tables = ((TableInfoActivity)(getActivity())).getTables();
            	for(int i = 0; i < tables.size();i++) {
            		Table table = tables.get(i);
            		if(table.getId() == tableId) {
            			orderId = table.getOrderId();
            			break;
            		}
            	}
            	System.out.println("here1");
                new GetOrderApi(orderId).asyncCall(new IApiCallback<Order>() {
					
					@Override
					public void onSuccess(Order result) {
						System.out.println("onSuccess");
						
					}
					
					@Override
					public void onFinish() {
						System.out.println("onFinish");
					}
					
					@Override
					public void onFailed(ApiException exception) {
						System.out.println("onFailed");
					}
				});
                dismiss();
            }
        });
    }
    
}