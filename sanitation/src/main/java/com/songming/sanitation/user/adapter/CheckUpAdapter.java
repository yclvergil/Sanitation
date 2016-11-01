package com.songming.sanitation.user.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.songming.sanitation.R;
import com.songming.sanitation.user.model.UserDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckUpAdapter extends BaseAdapter {

	private List<UserDto> datalist;
	private Context context;
	private DisplayImageOptions options;

	public CheckUpAdapter(Context context, List<UserDto> datalist) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.datalist = datalist;

	}

	public void setData(List<UserDto> datalist) {
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
		 return datalist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.checkup_item, null);
			holder = new ViewHolder();

			holder.ic = (ImageView) convertView
					.findViewById(R.id.checkup_item_ic);
			holder.name = (TextView) convertView
					.findViewById(R.id.checkup_item_name);
			holder.time = (TextView) convertView
					.findViewById(R.id.checkup_item_time);
			holder.site = (TextView) convertView
					.findViewById(R.id.checkup_item_duty);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(datalist.get(position).getName());
		long stationId = datalist.get(position).getStationId();
		holder.site.setText(getTitleName(stationId));

		return convertView;

	}

	class ViewHolder {

		ImageView ic;// 头像
		TextView name;// 名字
		TextView site;// 地址职位
		TextView time;// 时间
	}
	
	private String getTitleName(Long stationId) {
		String headText = "";
		if (stationId == 1) {
			headText = "总经理";
		} else if (stationId == 2) {
			headText = "车队长";
		} else if (stationId == 3) {
			headText = "主管";
		} else if (stationId == 4) {
			headText = "区域经理";
		} else if (stationId == 5) {
			headText = "司机";
		} else if (stationId == 6) {
			headText = "总部";
		} else {
			headText = "其他";
		}
		return headText;
	}

}
