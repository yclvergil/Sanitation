package com.songming.sanitation.workdown.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.songming.sanitation.R;
import com.songming.sanitation.user.model.PushMessageDto;
import com.songming.sanitation.workdeal.bean.TEventDetailDto;
import com.songming.sanitation.workdeal.bean.TEventDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IncidentListAdapter extends BaseAdapter {
	private List<TEventDto> datalist;
	private Context context;
	private DisplayImageOptions options;

	public IncidentListAdapter(Context context, List<TEventDto> datalist) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.datalist = datalist;

	}

	public void setData(List<TEventDto> datalist) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.incident_list_item, null);
			holder = new ViewHolder();

			holder.site = (TextView) convertView.findViewById(R.id.site);
			holder.headline = (TextView) convertView
					.findViewById(R.id.headline);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.headline.setText(datalist.get(position).getAddress());
		holder.site.setText(datalist.get(position).getEventTitle());

		// NursingHomeDTO model = datalist.get(position);
		//
		// if (model != null) {
		// holder.pension_pensionname.setText(model.getName() + "");
		// if ("1".equals(model.getType())) {
		// holder.pension_attr.setText("公立");
		// } else {
		// holder.pension_attr.setText("私立");
		// }
		//
		// holder.pension_price.setText(model.getPrice() + "");
		//
		// if ("1".equals(model.getType())) {
		// holder.pension_percent.setText("95%");
		// } else if ("2".equals(model.getType())) {
		// holder.pension_percent.setText("85%");
		// } else if ("3".equals(model.getType())) {
		// holder.pension_percent.setText("75%");
		// } else {
		// holder.pension_percent.setText("65%");
		// }
		//
		// holder.pension_comm.setText("好评");
		//
		//
		// }

		return convertView;

	}

	class ViewHolder {

		TextView headline;// 标题
		TextView site;// 地址
	}

}