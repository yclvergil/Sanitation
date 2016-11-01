package com.songming.sanitation.manage.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.songming.sanitation.R;
import com.songming.sanitation.user.model.TCarDto;

public class LookCarDetailsAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private List<TCarDto> list;

	public LookCarDetailsAdapter(Context context, List<TCarDto> list) {
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);

	}

	public void setData(List<TCarDto> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list != null) {
			return list.size();
		} else {

			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (list != null) {
			return list.get(position);
		} else {

			return 0;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.look_car_details_item,
					null);
			holder.name = (TextView) convertView
					.findViewById(R.id.details_name);
			holder.car = (TextView) convertView.findViewById(R.id.details_car);
			holder.money = (TextView) convertView
					.findViewById(R.id.details_money);
			holder.time = (TextView) convertView
					.findViewById(R.id.details_time);
			holder.type = (TextView) convertView
					.findViewById(R.id.details_type);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		TCarDto dto = list.get(position);

		if (dto.getManagerBy() != null) {
			holder.name.setText("经办人："+dto.getManagerBy());
		} else {
			holder.name.setText("");
		}

		if (dto.getCarNo() != null) {
			holder.car.setText(dto.getCarNo());
		} else {
			holder.car.setText("");
		}

		long type = dto.getMaintainType();

		if (type == 1) {
			holder.type.setText("维修");
		} else if (type == 3) {
			holder.type.setText("保养");
		} else if (type == 4) {
			holder.type.setText("加油");
		} else {
			holder.type.setText("");
		}

		long money = dto.getPrice();

		holder.money.setText(money + "");

		if (dto.getMaintainTime() != null) {
			holder.time.setText("保养时间："+dto.getMaintainTime());
		} else {
			holder.time.setText("保养时间：");
		}

		return convertView;
	}

	class ViewHolder {

		TextView name;// 经办人
		TextView car;// 所属车辆
		TextView money;// 保养费用
		TextView time;// 保养时间
		TextView type;// 保养类型

	}
}
