package com.songming.sanitation.manage.adapter;

import java.util.List;

import com.songming.sanitation.R;
import com.songming.sanitation.manage.adapter.PersonnelAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PersonnelSearchAdapter extends BaseAdapter {

	private List<Integer> datalist;
	private Context context;
	private ViewHolder holder;

	public PersonnelSearchAdapter(Context context, List<Integer> datalist) {
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
					R.layout.manage_scearch_item, null);
			holder = new ViewHolder();
			holder.ic = (ImageView) convertView
					.findViewById(R.id.search_item_ic);
			holder.name = (TextView) convertView
					.findViewById(R.id.search_item_name);

			holder.duty = (TextView) convertView
					.findViewById(R.id.search_item_duty);

			holder.entrance = (TextView) convertView
					.findViewById(R.id.search_item_entrance);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText("李南飞");
		holder.duty.setText("开福区环卫二部主管");
		switch (position) {
		case 8:
			holder.name.setText("李葱头");
			break;

		default:
			break;
		}

		return convertView;

	}

	class ViewHolder {
		ImageView ic;// 头像
		TextView name;// 名字
		TextView duty;// 地区职务显示
		TextView entrance;// 查看按钮
	}

}