package com.songming.sanitation.user.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.user.PhotoUploadActivity;
import com.songming.sanitation.user.model.TPatrolDto;
import com.songming.sanitation.user.model.TUploadFileDto;

/**
 * 巡查情况列表适配器
 * 
 * @author Rave
 * 
 */
public class InsideListviewAdapter extends BaseAdapter {

	private List<TPatrolDto> datalist;
	private Context context;
	private DisplayImageOptions options;

	public InsideListviewAdapter(Context context, List<TPatrolDto> datalist) {

		this.context = context;
		this.datalist = datalist;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.insideadapter3)
				.showImageForEmptyUri(R.drawable.insideadapter3)
				.showImageOnFail(R.drawable.insideadapter3).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(0)).build();// 设置圆角
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
					R.layout.insidelistview_adapter, null);
			holder = new ViewHolder();
			holder.insidetime = (TextView) convertView
					.findViewById(R.id.insidetime);
			holder.insidepoint = (TextView) convertView
					.findViewById(R.id.insidepoint);
			holder.insidectx = (TextView) convertView
					.findViewById(R.id.insidectx);
			holder.image = (ImageView) convertView.findViewById(R.id.image);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String time = StringUtilsExt.formatStr(datalist.get(position)
				.getCreateDate(), "HH:mm");
		holder.insidetime.setText(time);
		String address = datalist.get(position).getAddress();
		if (address == null) {
			address = "";
		}
		holder.insidepoint.setText(address);
		String content = datalist.get(position).getContent();
		if (content == null) {
			content = "";
		}
		holder.insidectx.setText(content);

		List<TUploadFileDto> files = datalist.get(position).getFiles();
		if (files.size() > 0) {
			String imgUrl = files.get(0).getFilePath();
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(Constants.IMAGE_URL + imgUrl,
					holder.image, options);
		}

		return convertView;
	}

	class ViewHolder {
		TextView insidetime;// 时间
		TextView insidepoint;// 地址
		TextView insidectx; // 内容
		ImageView image; // 照片
	}

}
