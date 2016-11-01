package com.songming.sanitation.sign.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.songming.sanitation.R;
import com.songming.sanitation.manage.bean.StaffDto;
import com.songming.sanitation.user.model.UserDto;

public class SignAdapter extends BaseAdapter {

	private List<StaffDto> datalist;
	private Context context;

	public SignAdapter(Context context, List<StaffDto> datalist) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.datalist = datalist;

	}

	public void setData(List<StaffDto> datalist) {
		this.datalist = datalist;
	}

	@Override
	public int getCount() {

		if (datalist != null) {
			return datalist.size();
		} else {
			return 0;
		}

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

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.sign_item, null);
			holder = new ViewHolder();

			holder.ic = (ImageView) convertView.findViewById(R.id.sign_item_ic);

			holder.name = (TextView) convertView
					.findViewById(R.id.sign_item_name);

			holder.status = (TextView) convertView
					.findViewById(R.id.sign_item_status);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(datalist.get(position).getName());
		
		long index = datalist.get(position).getIsSign();
		if(index == 0){
			holder.status.setText("未签到");
			holder.status.setTextColor(context.getResources().getColor(R.color.color_333333));
		}else{
			holder.status.setText("已签到");
			holder.status.setTextColor(context.getResources().getColor(R.color.title_bar_28c3b1));
		}
			
		return convertView;

	}

	class ViewHolder {
		ImageView ic;// 图片头像
		TextView name;// 名字
		TextView status;// 签到状态

	}

}