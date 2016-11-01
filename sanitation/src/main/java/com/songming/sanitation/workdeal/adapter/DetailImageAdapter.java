package com.songming.sanitation.workdeal.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.workdeal.bean.ImageDto;

public class DetailImageAdapter extends BaseAdapter {
	// 上下文对象
	private Context context;
	private List<ImageDto> picture;// 图片集合
	private DisplayImageOptions options;
	private ImageLoader imageLoader;

	public DetailImageAdapter(Context context, List<ImageDto> picture) {
		this.context = context;
		this.picture = picture;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.checkup_details_image)
				.showImageForEmptyUri(R.drawable.checkup_details_image)
				.showImageOnFail(R.drawable.checkup_details_image)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(false)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(0)).build();// 设置圆角
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}

	public int getCount() {

		if (picture == null) {
			return 0;
		}
		return picture.size();
	}

	public Object getItem(int item) {
		return item;
	}

	public long getItemId(int id) {
		return id;
	}

	// 创建View方法
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.detailimageadapter, null);
			holder.pictrue = (ImageView) convertView
					.findViewById(R.id.detailiimage);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 加载图片URL
		if (picture.size() > 0) {
			imageLoader.displayImage(Constants.IMAGE_URL
					+ picture.get(position).getFilePath(), holder.pictrue, options);
		}

		return convertView;
	}

	class ViewHolder {

		ImageView pictrue;// 相片
	}
}
