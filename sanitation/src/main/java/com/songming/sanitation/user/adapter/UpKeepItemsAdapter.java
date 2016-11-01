package com.songming.sanitation.user.adapter;

import java.util.List;

import com.songming.sanitation.R;
import com.songming.sanitation.user.adapter.UpKeepCarAdapter.ViewHolder;
import com.songming.sanitation.user.model.TCarDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 维修项目适配器
 * 
 * @author Administrator
 * 
 */
public class UpKeepItemsAdapter extends BaseAdapter {
	private List<TCarDto> datalist;
	private Context Context;
	private ViewHolder holder;

	public UpKeepItemsAdapter(Context Context, List<TCarDto> datalist) {
		this.Context = Context;
		this.datalist = datalist;
	}

	public void setData(List<TCarDto> datalist) {
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

	/**
	 * 下面是重要代码
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater _LayoutInflater = LayoutInflater.from(Context);
		holder = null;
		if (convertView == null) {
			convertView = _LayoutInflater.inflate(R.layout.upkeep_spinner_item,
					null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView
					.findViewById(R.id.spinner_item_tv);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(datalist.get(position).getDictLabel().toString());

		return convertView;
	}

	class ViewHolder {
		TextView tv;// 维修项目
	}
}
