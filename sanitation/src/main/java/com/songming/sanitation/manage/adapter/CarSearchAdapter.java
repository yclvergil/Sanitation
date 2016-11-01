package com.songming.sanitation.manage.adapter;

import java.util.List;

import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.manage.adapter.PersonnelSearchAdapter.ViewHolder;
import com.songming.sanitation.user.model.UserDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CarSearchAdapter extends BaseAdapter {

	private List<UserDto> datalist;
	private Context context;
	private ViewHolder holder;
	private int num = 0;

	public CarSearchAdapter(Context context, List<UserDto> datalist) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.datalist = datalist;

	}

	public void setData(List<UserDto> datalist) {
		this.datalist = datalist;
	}

	@Override
	public int getCount() {

		if (datalist == null) {
			return 0;
		}
		return datalist.size();

	}

	@Override
	public Object getItem(int position) {
		// return datalist.get(position);
		return datalist.get(position);
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

		if (!StringUtilsExt.isEmpty(datalist.get(position).getPhone())) {
			holder.name.setText(datalist.get(position).getName());
			holder.duty.setText("");
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
