package com.songming.sanitation.user.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.user.model.TPatrolDto;
import com.songming.sanitation.user.model.TUploadFileDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckUpDetailsAdapter extends BaseAdapter {

	private List<TPatrolDto> datalist;
	private Context context;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;

	public CheckUpDetailsAdapter(Context context, List<TPatrolDto> datalist) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.datalist = datalist;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.checkup_details_image)
				.showImageForEmptyUri(R.drawable.checkup_details_image)
				.showImageOnFail(R.drawable.checkup_details_image)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(0)).build();// 设置圆角
		
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));

	}

	public void setData(List<TPatrolDto> datalist) {
		this.datalist = datalist;
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
					R.layout.checkup_details_item, null);
			holder = new ViewHolder();

			holder.image = (ImageView) convertView
					.findViewById(R.id.details_item_image);
			holder.time = (TextView) convertView
					.findViewById(R.id.details_item_time);
			holder.site = (TextView) convertView
					.findViewById(R.id.details_item_site);
			holder.address = (TextView) convertView
					.findViewById(R.id.details_item_address);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (datalist.get(position).getContent() != null) {
			holder.site.setText(datalist.get(position).getContent());
		}
		if (datalist.get(position).getAddress() != null) {
			holder.address.setText(datalist.get(position).getAddress());
		}
		String stime = datalist.get(position).getCreateDate();
		holder.time.setText(StringUtilsExt.formatStr(stime, "HH:mm"));
		List<TUploadFileDto> files = datalist.get(position).getFiles();
		if (files.size() > 0) {

			String imgUrl = datalist.get(position).getFiles().get(0)
					.getFilePath();
			imageLoader.displayImage(Constants.IMAGE_URL + imgUrl,
					holder.image, options);	
		}

		return convertView;

	}

	class ViewHolder {

		ImageView image;// 图片
		TextView site;// 打扫情况
		TextView time;// 时间
		TextView address;// 地点
	}

}
