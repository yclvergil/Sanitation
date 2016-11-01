package com.songming.sanitation.user.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.ext.ListViewExt;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.user.PhotoUploadActivity;
import com.songming.sanitation.user.model.TPatrolDto;

public class CheckUploadAdapter extends BaseAdapter {

	private List<TPatrolDto> datalist;
	private Context context;
	private DisplayImageOptions options;

	private InsideListviewAdapter insideListviewAdapter;

	public CheckUploadAdapter(Context context, List<TPatrolDto> datalist) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.datalist = datalist;

	}

	public void setDatalist(List<TPatrolDto> datalist) {
		this.datalist = datalist;
	}

	@Override
	public int getCount() {

//		if (datalist != null) {
//			return datalist.size();
//		} else {
//			return 0;
//		}
		return 1;
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
					R.layout.checkupload_adapter, null);
			holder = new ViewHolder();

			holder.checkdate = (TextView) convertView
					.findViewById(R.id.checkdate);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.insidelistview = (ListViewExt) convertView
					.findViewById(R.id.insidelistview);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.checkdate.setText("今天");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String date = StringUtilsExt.formatStr(datalist.get(position)
//				.getCreateDate(), "yyyy-MM-dd");
//		if (date.equals(sdf.format(new Date()))) {
//			holder.checkdate.setText("今天");
//		} else {
//			date = StringUtilsExt.formatStr(datalist.get(position)
//					.getCreateDate(), "MM-dd");
//			holder.checkdate.setText(date);
//		}

		// 设置第一个item的相机图片按钮
		if (position == 0) {
			holder.image.setVisibility(View.VISIBLE);
			holder.image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context,
							PhotoUploadActivity.class);
					context.startActivity(intent);
				}
			});
		} else {
			holder.image.setVisibility(View.GONE);
		}

		insideListviewAdapter = new InsideListviewAdapter(context, datalist);
		holder.insidelistview.setAdapter(insideListviewAdapter);

		return convertView;

	}

	class ViewHolder {

		TextView checkdate;// 日期
		ListViewExt insidelistview;// 嵌套listview
		ImageView image;
	}

}
