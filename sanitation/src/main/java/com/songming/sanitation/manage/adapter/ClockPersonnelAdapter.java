package com.songming.sanitation.manage.adapter;

import java.util.List;

import com.songming.sanitation.R;
import com.songming.sanitation.manage.adapter.ClockAdapter.ViewHolder;
import com.songming.sanitation.manage.bean.StaffDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ClockPersonnelAdapter extends BaseAdapter {

	private List<StaffDto> datalist;
	private Context context;
	private ViewHolder holder;

	public ClockPersonnelAdapter(Context context, List<StaffDto> datalist) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.datalist = datalist;

	}

	public void setData(List<StaffDto> datalist) {
		this.datalist = datalist;
	}

	@Override
	public int getCount() {

		if (datalist == null) {
			return 0;
		} else {
			return datalist.size();
		}

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
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.clock_personnel_item, null);
			holder = new ViewHolder();

			holder.ic = (ImageView) convertView
					.findViewById(R.id.clock_item_ic);

			holder.name = (TextView) convertView
					.findViewById(R.id.clock_item_name);

			holder.site = (TextView) convertView
					.findViewById(R.id.clock_item_duty);

			holder.status = (TextView) convertView
					.findViewById(R.id.clock_item_status);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.name.setText(datalist.get(position).getName());
		holder.site.setText(datalist.get(position).getOrgName());
		
		int isSign = datalist.get(position).getIsSign();
		//1：已签到，0：未签到
		if(isSign == 1){
			holder.status.setText("已签到");
			holder.status.setTextColor(context.getResources().getColor(
					R.color.title_bar_28c3b1));
		}else{
			holder.status.setText("未签到");
			holder.status.setTextColor(context.getResources().getColor(
					R.color.color_333333));
		}

		return convertView;

	}

	class ViewHolder {
		ImageView ic;// 图片头像
		TextView site;// 地区职务显示
		TextView name;// 姓名
		TextView status;// 签到状态是否已经签到；

	}

}