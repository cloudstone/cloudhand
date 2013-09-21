package com.cloudstone.cloudhand.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.activity.OpenTableActivity;
import com.cloudstone.cloudhand.activity.TableInfoActivity;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.data.OrderDish;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.dialog.ClearTableDialogFragment;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.network.api.GetOrderApi;
import com.cloudstone.cloudhand.network.api.ListTableApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.util.L;
import com.cloudstone.cloudhand.view.BaseDishItem;

public class OpenTableSubmittedFragment extends BaseFragment {
    private TextView totalPriceView;
    private Order order;
    private List<OrderDish> orderDishList;
    private ListView dishListView;
    private BaseAdapter adapter;
    private Button btnClearTable;
    private List<Table> tables;
    int tableId;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_open_table_submitted, container, false);
    }
    
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        totalPriceView = (TextView)getView().findViewById(R.id.tv_total_price);
        dishListView = (ListView)getView().findViewById(R.id.listview_dish);
        
        tableId = ((OpenTableActivity)(getActivity())).getTableId();
        new ListTableApi().asyncCall(new IApiCallback<List<Table>>() {

            @Override
            public void onSuccess(List<Table> result) {
                tables = result;
                int orderId = 0;
                for(int i = 0; i < tables.size();i++) {
                    Table table = tables.get(i);
                    if(table.getId() == tableId) {
                        orderId = table.getOrderId();
                        break;
                    }
                }
                new GetOrderApi(orderId).asyncCall(new IApiCallback<Order>() {
                    
                    @Override
                    public void onSuccess(Order result) {
                        order = result;
                        orderDishList = order.getDishes();
                        render();
                    }
                    
                    @Override
                    public void onFinish() {
                    }
                    
                    @Override
                    public void onFailed(ApiException exception) {
                    }
                });
                btnClearTable = (Button)getView().findViewById(R.id.btn_clear_table);
                btnClearTable.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        ClearTableDialogFragment dialog = new ClearTableDialogFragment();
                        bundle.putInt("tableId", tableId);
                        dialog.setArguments(bundle);
                        dialog.show(getFragmentManager(), "clearTableDialogFragment");
                    }
                });
            }

            @Override
            public void onFailed(ApiException exception) {
                Toast.makeText(getActivity(), R.string.error_list_table_info_failed, Toast.LENGTH_SHORT).show();
                L.e(TableInfoActivity.class, exception);
            }

            @Override
            public void onFinish() {
            }

        });
    }
    
    private class InnerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orderDishList.size();
        }

        @Override
        public OrderDish getItem(int position) {
            return orderDishList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OpenTableActivity openTableActivity = ((OpenTableActivity)(getActivity()));
            BaseDishItem view = (BaseDishItem) convertView;
            if (view == null) {
                view = createView();
            }
            OrderDish orderDish = getItem(position);
            Dish dish = openTableActivity.getDishes().getById(orderDish.getId());
            
            StringBuilder sb = new StringBuilder();
            String[] remarks = orderDish.getRemarks();
            for(int i = 0;i < remarks.length;i++) {
                sb.append(remarks[i]);
            }
            
            view.render(dish, (int)orderDish.getNumber(), sb.toString());
            bindView(view, position);
            return view;
        }
        
        private BaseDishItem createView() {
            BaseDishItem view = new BaseDishItem(getActivity());
            return view;
        }
        
        private void bindView(BaseDishItem view, int position) {
            Dish dish = getItem(position);
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                view.setTag(holder);
            }
            holder.setDish(dish);
        }
        
    }
    
    private class ViewHolder {
        private Dish dish;

        public Dish getDish() {
            return dish;
        }

        public void setDish(Dish dish) {
            this.dish = dish;
        }
        
    }
    
    //更新界面
    protected void render() {
        adapter = new InnerAdapter();
        dishListView.setAdapter(adapter);
        totalPriceView.setText(order.getOriginPrice() + "");
    }
    
}