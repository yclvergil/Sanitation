package com.songming.sanitation.message.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.songming.sanitation.R;
import com.songming.sanitation.map.model.MapModel;

public class MessageListAdapter extends BaseAdapter {

	private List<MapModel> data;
	private Context context;
	private LayoutInflater inflater;

	public MessageListAdapter(Context context, List<MapModel> data) {
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
			convertView = inflater.inflate(R.layout.message_list_item, null);

			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.start_view = convertView.findViewById(R.id.start_view);
			holder.view = convertView.findViewById(R.id.view);

			holder.layout2 = (LinearLayout) convertView
					.findViewById(R.id.layout2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MapModel model = data.get(position);
		// if (model != null) {
		holder.start_view.setVisibility(View.GONE);
		holder.view.setVisibility(View.VISIBLE);
		holder.layout2.setVisibility(View.GONE);
		if (position == 0) {
			holder.start_view.setVisibility(View.VISIBLE);
			holder.view.setVisibility(View.GONE);
		} else if (position == (data.size() - 1)) {
			holder.layout2.setVisibility(View.VISIBLE);
		}
		// holder.title.setText(model.getNumber());
		// holder.content.setText(model.getDuration());
		// }
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView content;
		View start_view;
		View view;
		LinearLayout layout2;
	}
}
