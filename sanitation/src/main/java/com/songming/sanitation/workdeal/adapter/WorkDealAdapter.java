package com.songming.sanitation.workdeal.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.songming.sanitation.R;
import com.songming.sanitation.workdeal.bean.TEventDto;

/**
 * 工单适配器
 * 
 * @author Rave
 * 
 */
public class WorkDealAdapter extends BaseAdapter {

	private List<TEventDto> datalist;
	private Context context;
	private DisplayImageOptions options;
	private int i;

	public WorkDealAdapter(Context context, List<TEventDto> datalist) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.datalist = datalist;

	}

	public void setData(List<TEventDto> datalist, int i) {
		// TODO Auto-generated method stub
		this.datalist = datalist;
		this.i = i;
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
					R.layout.workdeal_adapter, null);
			holder = new ViewHolder();

			holder.msgtitle = (TextView) convertView
					.findViewById(R.id.msgtitle);

			holder.isRead = (TextView) convertView.findViewById(R.id.isread);
			holder.msgdate = (TextView) convertView.findViewById(R.id.msgdate);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		TEventDto model = datalist.get(position);

		holder.msgtitle.setText(model.getEventTitle());
		if (i == 1) {
			holder.msgdate.setText(model.getCreateDate());
		} else {
			holder.msgdate.setText(model.getLastUpdateDate());
		}

		if (i == 2) {

			if ("1".equals(model.getIsAssign())) {
				holder.msgtitle.setTextColor(context.getResources().getColor(
						R.color.title_bar_28c3b1));

				holder.msgdate.setTextColor(context.getResources().getColor(
						R.color.title_bar_28c3b1));

				holder.isRead.setTextColor(context.getResources().getColor(
						R.color.title_bar_28c3b1));
				holder.isRead.setVisibility(View.VISIBLE);

				if ("0".equals(model.getIsRead())) {
					holder.isRead.setText("未查看");
				} else if ("1".equals(model.getIsRead())) {
					holder.isRead.setText("已查看");
				}

			} else if ("0".equals(model.getIsAssign())) {
				holder.isRead.setVisibility(View.GONE);

				holder.msgtitle.setTextColor(context.getResources().getColor(
						R.color.color_333333));
				holder.msgdate.setTextColor(context.getResources().getColor(
						R.color.color_808080));

				holder.isRead.setTextColor(context.getResources().getColor(
						R.color.color_333333));
			}
		} else {

			holder.isRead.setVisibility(View.GONE);
			
			holder.msgtitle.setTextColor(context.getResources().getColor(
					R.color.color_333333));
			holder.msgdate.setTextColor(context.getResources().getColor(
					R.color.color_808080));
		}
		return convertView;
	}

	class ViewHolder {

		TextView msgtitle;// 标题
		TextView msgdate;// 日期
		TextView isRead;
	}

}
