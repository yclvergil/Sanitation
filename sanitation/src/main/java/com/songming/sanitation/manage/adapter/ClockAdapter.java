package com.songming.sanitation.manage.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.songming.sanitation.R;
import com.songming.sanitation.manage.PersonalPositionActivity;
import com.songming.sanitation.manage.bean.SysStationRankDto;

public class ClockAdapter extends BaseAdapter {

	private List<SysStationRankDto> datalist;
	private Context context;
	private ViewHolder holder;

	public ClockAdapter(Context context, List<SysStationRankDto> datalist) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.datalist = datalist;

	}

	public void setData(List<SysStationRankDto> datalist) {
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
					R.layout.clock_item, null);
			holder = new ViewHolder();

			holder.site = (TextView) convertView.findViewById(R.id.clock_site);
			holder.clock_detail = (TextView) convertView
					.findViewById(R.id.clock_detail);
			holder.clock_sign = (TextView) convertView
					.findViewById(R.id.clock_sign);
			holder.search_item_entrance = (TextView) convertView
					.findViewById(R.id.search_item_entrance);
			holder.clock_p = (TextView) convertView.findViewById(R.id.clock_p);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.site.setText(datalist.get(position).getStaffName());

		String sign_detail = datalist.get(position).getSummaryText();
		if (sign_detail == null) {
			sign_detail = "";
		}
		holder.clock_detail.setText("（" + sign_detail + "）");

		final int isSign = datalist.get(position).getIsSign();
		if (isSign == 1) {
			holder.clock_sign.setText("已签到");
			holder.clock_sign.setTextColor(context.getResources().getColor(R.color.title_bar_28c3b1));
		} else {
			holder.clock_sign.setText("未签到");
			holder.clock_sign.setTextColor(context.getResources().getColor(R.color.color_333333));
		}
		
		holder.search_item_entrance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isSign == 1) {

					Intent intent = new Intent(context,
							PersonalPositionActivity.class);
					intent.putExtra("staffId", datalist.get(position)
							.getStaffId());
					context.startActivity(intent);
				} else {
					Toast.makeText(context,
							datalist.get(position).getStaffName() + "尚未签到！",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		holder.clock_p.setText(datalist.get(position).getStationName());

		return convertView;

	}

	class ViewHolder {
		TextView site;// 地区职务显示
		TextView clock_p;// 职称
		TextView clock_detail;// 签到情况
		TextView clock_sign;// 是否签到
		TextView search_item_entrance;// 查看地图

	}

}