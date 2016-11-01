package com.songming.sanitation.map.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.songming.sanitation.R;
import com.songming.sanitation.map.model.LineItemModel;
import com.songming.sanitation.map.model.MapModel;

//任务适配器
public class MissionDetailListAdapter extends BaseAdapter {

	private List<LineItemModel> data;
	private Context context;
	private LayoutInflater inflater;

	public MissionDetailListAdapter(Context context, List<LineItemModel> data) {
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
			convertView = inflater.inflate(R.layout.mission_detail_list_item,
					null);

			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.address_text = (TextView) convertView
					.findViewById(R.id.address_text);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.phone_image = (ImageView) convertView
					.findViewById(R.id.phone_image);
			holder.phone_text = (TextView) convertView
					.findViewById(R.id.phone_text);
			holder.number_text = (TextView) convertView
					.findViewById(R.id.number_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final LineItemModel model = data.get(position);
		if (model != null) {
			holder.name.setText(model.getStationName());
			holder.address_text.setText(model.getStationAddress());
			holder.number_text.setText(model.getGoodstotalcount()+"件货物");
			holder.phone_text.setText(model.getStationuserphone());
			holder.phone_image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 进入系统拨号页面
					Intent phoneIntent = new Intent();
					phoneIntent.setAction(Intent.ACTION_DIAL);
					phoneIntent.setData(Uri.parse("tel:"
							+ model.getStationuserphone()));
					context.startActivity(phoneIntent);
				}
			});

		}
		return convertView;
	}

	class ViewHolder {

		private TextView name;// 网点名称
		private TextView address_text;// 网点位置
		private TextView number_text;// 货物数目
		private ImageView phone_image; // 电话图标
		private TextView phone_text; // 电话号码

	}
}
