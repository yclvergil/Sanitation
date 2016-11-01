package com.songming.sanitation.user.adapter;

import java.util.List;

import com.songming.sanitation.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusNumberAdapter extends BaseAdapter {
	private List datalist;
	private Context Context;
	private ViewHolder holder;

	public BusNumberAdapter(Context Context, List datalist) {
		this.Context = Context;
		this.datalist = datalist;
	}

	@Override
	public int getCount() {
		if (datalist != null) {
			return datalist.size();
		} else {
			return 20;
		}
	}

	@Override
	public Object getItem(int position) {
		return 10;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 下面是重要代码
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater _LayoutInflater = LayoutInflater.from(Context);
		holder = null;
		if (convertView == null) {
			convertView = _LayoutInflater.inflate(R.layout.category_item, null);
			holder = new ViewHolder();
			holder.car = (TextView) convertView.findViewById(R.id.car);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.car.setText("车牌号" + position);
		return convertView;
	}

	class ViewHolder {
		TextView car;
	}
}
