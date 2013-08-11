package com.cloudstone.cloudhand.adapter;

import java.util.List;
import java.util.Map;

import com.cloudstone.cloudhand.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class superSimpleAdapter extends SimpleAdapter {
	private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局


	public superSimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		mInflater = LayoutInflater.from(context);
	}
	
	/*存放控件*/
	public final class ViewHolder{
	    public TextView name;
	    public TextView price;
	    public TextView amount;
	    public Button add;
	    public Button sub;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView != null) {
			convertView = mInflater.inflate(R.layout.listview, null);
			holder = new ViewHolder();
			/*得到各个控件的对象*/
			holder.name = (TextView) convertView.findViewById(R.id.adapter_name);
			holder.price = (TextView) convertView.findViewById(R.id.adapter_price);
			holder.amount = (TextView) convertView.findViewById(R.id.adapter_amount);
			holder.add = (Button) convertView.findViewById(R.id.adapter_add);
			holder.sub = (Button) convertView.findViewById(R.id.adapter_sub);
			convertView.setTag(holder);//绑定ViewHolder对象

		} else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
          }

		return super.getView(position, convertView, parent);
	}

}
