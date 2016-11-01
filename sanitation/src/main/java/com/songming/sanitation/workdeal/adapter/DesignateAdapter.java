package com.songming.sanitation.workdeal.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.songming.sanitation.R;
import com.songming.sanitation.user.model.UserDto;

/**
 * 工单指派人员列表适配器
 * 
 * @author Administrator
 * 
 */
public class DesignateAdapter extends BaseAdapter {

	private List<UserDto> datalist;
	private Context context;
	private List<String> select;
	private int mSelect = -1;

	/** 选定多个 */
	public void setSelect(List<String> select) {
		this.select = select;
		notifyDataSetChanged();
	}

	/** 选定单个 */
	public void setSelect(int position) {
		if (mSelect != position) {
			mSelect = position;
		}
		notifyDataSetChanged();
	}

	private boolean isContains(List<String> select, String str) {
		if (select == null || select.size() == 0) {
			return false;
		}
		for (int i = 0; i < select.size(); i++) {
			if (select.get(i).equals(str)) {
				return true;
			}
		}
		return false;

	}

	public DesignateAdapter(Context context, List<UserDto> datalist) {

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
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.designate_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.designate_name);
			holder.confirm = (ImageView) convertView
					.findViewById(R.id.designate_item_confirm);
			holder.ic = (ImageView) convertView.findViewById(R.id.designate_ic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (isContains(select, position + "")) {
			holder.confirm.setVisibility(View.VISIBLE);
			holder.name.setTextColor(context.getResources().getColor(
					R.color.title_bar_28c3b1));
		} else {
			holder.confirm.setVisibility(View.INVISIBLE);
			holder.name.setTextColor(context.getResources().getColor(
					R.color.color_808080));
		}

		// if (position == mSelect) {
		// holder.confirm.setVisibility(View.VISIBLE);
		// holder.name.setTextColor(context.getResources().getColor(
		// R.color.title_bar_28c3b1));
		// } else {
		// holder.confirm.setVisibility(View.INVISIBLE);
		// holder.name.setTextColor(context.getResources().getColor(
		// R.color.color_808080));
		// }

		holder.name.setText(datalist.get(position).getName());

		return convertView;

	}

	class ViewHolder {
		ImageView ic;// 头像
		ImageView confirm;// 确定图标
		TextView name;// 名字
	}

}