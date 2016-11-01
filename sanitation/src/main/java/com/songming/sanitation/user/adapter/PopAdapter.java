package com.songming.sanitation.user.adapter;

import java.util.List;

import com.songming.sanitation.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PopAdapter extends BaseAdapter {

	private List<Integer> datalist;
	private Context context;
	private ViewHolder holder;

	public PopAdapter(Context context, List<Integer> datalist) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.datalist = datalist;

	}

	// public void setData(List<NursingHomeDTO> datalist){
	// this.datalist = datalist;
	// }

	@Override
	public int getCount() {

		if (datalist != null) {
			return datalist.size();
		} else {
			return 40;
		}

	}

	@Override
	public Object getItem(int position) {
		// return datalist.get(position);
		return 40;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.checkup_pop_item, null);
			holder = new ViewHolder();

			holder.site = (TextView) convertView
					.findViewById(R.id.pop_item_textview);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.site.setText("开福区");
		switch (position) {
		case 1:
			holder.site.setText("天心区");
			break;
		case 2:
			holder.site.setText("芙蓉区");
			break;
		case 4:
			holder.site.setText("岳麓区");
			break;
		case 8:
			holder.site.setText("天心区");
			break;

		default:
			break;
		}

		return convertView;

	}

	class ViewHolder {
		TextView site;// 地区
	}

}
