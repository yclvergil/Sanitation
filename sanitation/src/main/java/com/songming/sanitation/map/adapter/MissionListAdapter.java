package com.songming.sanitation.map.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.map.model.MapFragmentListModel;
import com.songming.sanitation.map.model.MapModel;
import com.songming.sanitation.map.service.MyLocationService;

//线路适配器
public class MissionListAdapter extends BaseAdapter {

	private List<MapFragmentListModel> data;
	private Context context;
	private LayoutInflater inflater;

	public MissionListAdapter(Context context, List<MapFragmentListModel> data) {
		this.data = data;
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (data != null) {
			return data.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.mission_list_item, null);
			holder.item_layout = (LinearLayout) convertView
					.findViewById(R.id.item_layout);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.mileage = (TextView) convertView.findViewById(R.id.mileage);
			holder.whenused = (TextView) convertView
					.findViewById(R.id.whenused);
			holder.isok = (ImageView) convertView.findViewById(R.id.isok);
			holder.iscarried = (ImageView) convertView
					.findViewById(R.id.iscarried);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MapFragmentListModel model = data.get(position);
		if (model != null) {
			// holder.name.setText(model.getName());
			
			String time = model.getDiliveryTime();
			if (time != null & time.length() > 8) {
				time = time.substring(time.length() - 8, time.length());
			}
			holder.time.setText(time);

			if (model.getStationtimes() == model.getStatusnum()) {
				holder.isok.setVisibility(View.VISIBLE);
				holder.item_layout.setBackgroundColor(context.getResources()
						.getColor(R.color.title_bar_28c3b1));
				holder.iscarried.setImageResource(R.drawable.missin_item_ioc);
			} else {
				holder.isok.setVisibility(View.INVISIBLE);
				holder.item_layout.setBackgroundColor(context.getResources()
						.getColor(R.color.app_bg_color));
				holder.iscarried.setImageResource(R.drawable.missin_item_ioc);
			}
			String id_m = SharedPreferencesUtils.getStringValue(context,
					MyLocationService.MYLOCATIONSERVICE_STRING, "");
			if (id_m.equals(model.getUserId() + model.getCarId()
					+ model.getBatch()+ model.getTripTimes())) {
				holder.isok.setVisibility(View.INVISIBLE);
				holder.item_layout.setBackgroundColor(context.getResources()
						.getColor(R.color.white));
				holder.iscarried.setImageResource(R.drawable.missin_item_ioc1);
			}
			String sd = model.getDistriType();
			if ("null".equals(sd) || sd == null) {
				sd = "";
			}

			holder.name.setText(sd + "");
			holder.mileage.setText("全程约" + model.getTotalDistances() + "公里");
			holder.whenused.setText("预计用时" + model.getTotalTimes() + "分钟");
		}
		return convertView;
	}

	class ViewHolder {
		private LinearLayout item_layout;
		private TextView name; // 线路名称
		private TextView time;// 时间
		private TextView mileage;// 大约里程
		private TextView whenused;// 大约用时
		private ImageView isok;// 是否已经完成
		private ImageView iscarried;// 是否正在进行中
	}
}
