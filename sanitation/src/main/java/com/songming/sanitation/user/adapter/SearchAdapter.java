package com.songming.sanitation.user.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.user.model.UserDto;

/**
 * 联系人适配器
 * 
 * @author Rave
 * 
 */
public class SearchAdapter extends BaseAdapter {

	private List<UserDto> datalist;
	private Context context;

	public SearchAdapter(Context context, List<UserDto> datalist) {

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

		return datalist.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.contacts_item, null);
			holder = new ViewHolder();

			holder.ctname = (TextView) convertView.findViewById(R.id.ctname);
			holder.catalog = (TextView) convertView.findViewById(R.id.catalog);
			holder.ctdetail = (TextView) convertView
					.findViewById(R.id.ctdetail);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.catalog.setVisibility(View.GONE);
		holder.ctname.setText(datalist.get(position).getName());
		if (!StringUtilsExt.isEmpty(datalist.get(position).getPhone())) {
			holder.ctdetail.setText(datalist.get(position).getPhone());
		}

		return convertView;
	}

	class ViewHolder {

		TextView ctname;// 标题
		TextView ctdetail;// 内容
		TextView catalog;// 内容
	}

}
