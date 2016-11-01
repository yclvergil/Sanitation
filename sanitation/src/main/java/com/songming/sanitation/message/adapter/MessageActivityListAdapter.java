package com.songming.sanitation.message.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.message.model.MessagePushDTO;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MessageActivityListAdapter extends BaseAdapter {

	private List<MessagePushDTO> data;
	private Context context;
	private LayoutInflater inflater;
	private DisplayImageOptions options;

	public MessageActivityListAdapter(Context context, List<MessagePushDTO> data) {
		this.data = data;
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showImageOnLoading(R.drawable.app_head)
				.showImageForEmptyUri(R.drawable.app_head)
				.showImageOnFail(R.drawable.app_head).cacheInMemory(false)
				.cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.displayer(new RoundedBitmapDisplayer(80)).build();// 设置圆角
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
			convertView = inflater.inflate(R.layout.message_activty_list_item,
					null);
			holder.user_image = (ImageView) convertView
					.findViewById(R.id.user_image);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.message_new_ic = (ImageView) convertView
					.findViewById(R.id.message_new_ic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MessagePushDTO model = data.get(position);

		if (model != null) {
			if ("2".equals(model.getSeeState())) {
				holder.message_new_ic.setVisibility(View.VISIBLE);
			} else {
				holder.message_new_ic.setVisibility(View.GONE);
			}
			// ImageLoader.getInstance().displayImage(
			// Constants.SERVER_IP + "/"
			// + model.getHeadUrl(),
			// holder.imagehead, options);
			holder.content.setText(model.getContent());
		}
		return convertView;
	}

	class ViewHolder {
		TextView content;
		ImageView user_image;
		ImageView message_new_ic;
	}
}
