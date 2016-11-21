package com.songming.sanitation.workdown.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.songming.sanitation.R;
import com.songming.sanitation.user.model.PushMessageDto;
import com.songming.sanitation.workdeal.bean.TEventDetailDto;
import com.songming.sanitation.workdeal.bean.TEventDto;

import android.content.Context;
import android.util.Log;
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
	private final int cyear;
	private final int cmonth;
	private final int cday;

	public IncidentListAdapter(Context context, List<TEventDto> datalist) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.datalist = datalist;
		Calendar now = Calendar.getInstance();
		cyear = now.get(Calendar.YEAR);
		cmonth = now.get(Calendar.MONTH)+1;
		cday = now.get(Calendar.DAY_OF_MONTH);
		Log.d("IncidentListAdapter", cyear + "年" + cmonth + "月" + cday + "日");


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
		TEventDto tEventDto = datalist.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.incident_list_item, null);
			holder = new ViewHolder();

			holder.site = (TextView) convertView.findViewById(R.id.site);
			holder.headline = (TextView) convertView
					.findViewById(R.id.headline);
			holder.nowDate = (TextView) convertView.findViewById(R.id.tv_now_date);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//日期显示
		String lastUpdateDate = tEventDto.getLastUpdateDate();
		int year = Integer.parseInt(lastUpdateDate.substring(0, 4));
		int month = Integer.parseInt(lastUpdateDate.substring(5, 7));
		int day = Integer.parseInt(lastUpdateDate.substring(8, 10));
		if (cyear == year && cmonth == month) {
			if (cday == day) {
				//今天
				holder.nowDate.setText("今天");
				holder.nowDate.setTextColor(context.getResources().getColor(R.color.red3));
			} else if (cday - 1 == day) {
				//昨天
				holder.nowDate.setText("昨天");
				holder.nowDate.setTextColor(context.getResources().getColor(R.color.red1));

			} else if (cday - 2 == day) {
				//前天
				holder.nowDate.setText("前天");
				holder.nowDate.setTextColor(context.getResources().getColor(R.color.red1));

			} else {
				//日期
				holder.nowDate.setText(year+"年"+month+"月"+day+"日");
				holder.nowDate.setTextColor(context.getResources().getColor(R.color.gray));
			}
		} else {
			//日期
			holder.nowDate.setText(year+"年"+month+"月"+day+"日");
			holder.nowDate.setTextColor(context.getResources().getColor(R.color.gray));
		}
		holder.headline.setText(tEventDto.getEventTitle());
		holder.site.setText(tEventDto.getAddress());


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
		TextView nowDate;
		TextView headline;// 标题
		TextView site;// 地址
	}

}