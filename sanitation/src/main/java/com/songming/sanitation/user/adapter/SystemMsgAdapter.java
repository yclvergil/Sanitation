package com.songming.sanitation.user.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.songming.sanitation.R;
import com.songming.sanitation.user.model.PushMessageDto;
import com.songming.sanitation.user.model.UserDto;

/**
 * 养老院适配器
 * 
 * @author Rave
 * 
 */
public class SystemMsgAdapter extends BaseAdapter {

	private List<PushMessageDto> datalist;
	private Context context;
	private DisplayImageOptions options;
	private LatLng latLng;

	private double mLat1;
	private double mLon1;

	public SystemMsgAdapter(Context context, List<PushMessageDto> datalist) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.datalist = datalist;

	}

	// public void setData(List<NursingHomeDTO> datalist){
	// this.datalist = datalist;
	// }

	@Override
	public int getCount() {

		if (datalist == null) {
			return 0;
		}
		return datalist.size();

	}

	public void setData(List<PushMessageDto> datalist) {
		this.datalist = datalist;
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
					R.layout.systemmsg_adapter, null);
			holder = new ViewHolder();

			holder.msgtitle = (TextView) convertView
					.findViewById(R.id.msgtitle);
			holder.msgdate = (TextView) convertView.findViewById(R.id.msgdate);
			holder.msgtime = (TextView) convertView.findViewById(R.id.msgtime);
			holder.msgctx = (TextView) convertView.findViewById(R.id.msgctx);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.msgtitle.setText(datalist.get(position).getTitle());
		holder.msgdate.setText(datalist.get(position).getCreateDate());
		holder.msgctx.setText(datalist.get(position).getDescription());
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

		ImageView title_image;
		TextView msgtitle;// 标题
		TextView msgdate;// 日期
		TextView msgtime;// 时间
		TextView msgctx; // 内容
	}

}
