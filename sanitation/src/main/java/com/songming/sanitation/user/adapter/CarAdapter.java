package com.songming.sanitation.user.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.songming.sanitation.R;
import com.songming.sanitation.user.model.TStaffCarRefDto;

public class CarAdapter extends BaseAdapter {
	private List<TStaffCarRefDto> datalist;
	private Context Context;
	private ViewHolder holder;

	public CarAdapter(Context Context, List<TStaffCarRefDto> datalist) {
		this.Context = Context;
		this.datalist = datalist;
	}

	public void setDatalist(List<TStaffCarRefDto> datalist) {
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

	/**
	 * 下面是重要代码
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater _LayoutInflater = LayoutInflater.from(Context);
		holder = null;
		if (convertView == null) {
			convertView = _LayoutInflater.inflate(R.layout.car_item, null);
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.car_time);
			holder.car_type = (TextView) convertView
					.findViewById(R.id.car_type);
			holder.confirm = (TextView) convertView.findViewById(R.id.car_btn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		if (datalist.get(position).getIsRef() == 1) {
//			holder.confirm.setBackgroundResource(R.drawable.car_frame);
//			holder.confirm.setText("已关联");
//		} else {
			holder.confirm.setBackgroundResource(R.drawable.car_frame_not);
			holder.confirm.setText("再次关联");
//		}

		holder.time.setText(datalist.get(position).getCreateDate());
		String carType = datalist.get(position).getCarType();
		String carNo = datalist.get(position).getCarNo();
		String carCode = datalist.get(position).getCarCode();
		holder.car_type.setText(carType + "-" + carNo + " " + carCode);

		return convertView;
	}

	class ViewHolder {
		TextView time;// 事件,日期
		TextView car_type;// 车辆类别,车牌号
		TextView confirm;// 确定按键

	}
}
