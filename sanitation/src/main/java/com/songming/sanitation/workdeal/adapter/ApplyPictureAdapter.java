package com.songming.sanitation.workdeal.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.user.model.TUploadFileDto;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 审批申请详情图片适配器
 * 
 * @author Administrator
 * 
 */
public class ApplyPictureAdapter extends BaseAdapter {

	private Context context;
	private List<TUploadFileDto> list;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public ApplyPictureAdapter(Context context, List<TUploadFileDto> list) {
		super();
		this.context = context;
		this.list = list;
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

	public void setList(List<TUploadFileDto> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HoldView holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.applypicture_item, null);
			holder = new HoldView();
			holder.picture = (ImageView) convertView.findViewById(R.id.picture);
			convertView.setTag(holder);
		} else {
			holder = (HoldView) convertView.getTag();
		}
		String imgUrl = list.get(position).getFilePath();
		imageLoader.displayImage(Constants.IMAGE_URL + imgUrl, holder.picture, options);
		return convertView;
	}

	class HoldView {
		ImageView picture;
	}
}
