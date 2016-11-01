package com.songming.sanitation.user.adapter;

import java.util.List;

import com.songming.sanitation.R;
import com.songming.sanitation.user.adapter.PhotoUploadAdapter.ViewHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class UpKeepAdapter extends BaseAdapter {
	// 上下文对象
	private Context context;
	private Handler handler;
	private List<Bitmap> picture;// 图片集合
	private static final int DELETEPICTURE = 99;

	// 图片数组
	// private Integer[] imgs = { R.color.black,R.color.black, R.color.black};

	public void setPicture(List<Bitmap> picture) {
		this.picture = picture;
	}

	public UpKeepAdapter(Context context, List<Bitmap> picture, Handler handler) {
		this.context = context;
		this.picture = picture;
		this.handler = handler;
	}

	public int getCount() {

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
					R.layout.upkeep_item, null);
			holder.pictrue = (ImageView) convertView.findViewById(R.id.picture);
			holder.deletepicture = (ImageView) convertView
					.findViewById(R.id.deletepicture);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == (picture.size() - 1)) {
			holder.deletepicture.setVisibility(View.INVISIBLE);
		} else {
			holder.deletepicture.setVisibility(View.VISIBLE);
		}

		holder.pictrue
				.setImageBitmap(picture.get(picture.size() - 1 - position));// 为ImageView设置图片资源

		holder.deletepicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.obj = picture.size() - 1 - position;
				msg.what = DELETEPICTURE;
				handler.sendMessage(msg);
			}
		});

		return convertView;
	}

	class ViewHolder {

		ImageView pictrue;// 相片
		ImageView deletepicture;// 删除按钮
	}
}