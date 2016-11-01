package com.songming.sanitation.user.adapter;

import java.util.List;

import com.songming.sanitation.R;
import com.songming.sanitation.user.model.TCarDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 车辆类别适配器
 * 
 * @author Administrator
 * 
 */
public class CarTypeAdapter extends BaseAdapter {
	private List<TCarDto> datalist;
	private Context Context;
	private ViewHolder holder;

	public CarTypeAdapter(Context Context, List<TCarDto> datalist) {
		this.Context = Context;
		this.datalist = datalist;
	}

	public void setData(List<TCarDto> datalist) {
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
			convertView = _LayoutInflater.inflate(R.layout.category_item, null);
			holder = new ViewHolder();
			holder.car = (TextView) convertView.findViewById(R.id.car);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String carTypeName = datalist.get(position).getCarTypeName();
		String carNo = datalist.get(position).getCarNo();
		String carCode = datalist.get(position).getCarCode();
		holder.car.setText(carTypeName + "-" + carNo + " " + carCode);
		return convertView;
	}

	class ViewHolder {
		TextView car;
	}
}
