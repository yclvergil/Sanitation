package com.songming.sanitation.workdeal.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.songming.sanitation.R;
import com.songming.sanitation.workdeal.CheckUpPictureActivity;
import com.songming.sanitation.workdeal.bean.ImageDto;
import com.songming.sanitation.workdeal.bean.TEventDetailDto;

/**
 * 工单适配器
 * 
 * @author Rave
 * 
 */
public class WorkDealDetailAdapter extends BaseAdapter {

	private List<TEventDetailDto> datalist;
	private Context context;
	private long stationId;
	private int whatsup;
	private TEventDetailDto model;
	private GridView gv;// 图片gridview
	private DetailImageAdapter adapter;// gridview适配器
	private List<ImageDto> files = new ArrayList<ImageDto>();

	public WorkDealDetailAdapter(Context context,
			List<TEventDetailDto> datalist, long stationId, int whatsup) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.datalist = datalist;
		this.stationId = stationId;
		this.whatsup = whatsup;
	}

	public void setData(List<TEventDetailDto> datalist) {
		// TODO Auto-generated method stub
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
		// return datalist.get(position);
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
					R.layout.workdealdetail_adapter, null);
			holder = new ViewHolder();

			holder.accomplish_proceed_name = (TextView) convertView
					.findViewById(R.id.accomplish_proceed_name);
			holder.accomplish_proceed_time = (TextView) convertView
					.findViewById(R.id.accomplish_proceed_time);
			holder.accomplish_proceed_phone = (ImageView) convertView
					.findViewById(R.id.accomplish_proceed_phone);
			holder.accomplish_proceed_details = (TextView) convertView
					.findViewById(R.id.workstatus);
			
			holder.gv = (GridView) convertView.findViewById(R.id.gridview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		model = datalist.get(position);

		// if (position == 0) {
		// holder.accomplish_proceed_details.setVisibility(View.GONE);
		// holder.accomplish_proceed_phone.setVisibility(View.GONE);
		// if (whatsup == 888) {
		// holder.accomplish_proceed_status.setText("事件形成");
		// } else {
		// holder.accomplish_proceed_status.setText("工单形成");
		// }
		// } else if (position == (datalist.size() - 1)) {
		//
		// // 接口没有返回电话，先隐藏
		// if (model.getExecutePhone() != null) {
		// holder.accomplish_proceed_phone.setVisibility(View.VISIBLE);
		// }else{
		// holder.accomplish_proceed_phone.setVisibility(View.GONE);
		// }
		// holder.accomplish_proceed_phone
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// Intent it = new Intent(Intent.ACTION_CALL, Uri
		// .parse("tel:" + model.getExecutePhone()));
		// context.startActivity(it);
		//
		// }
		// });
		// if (whatsup == 888) {
		// holder.accomplish_proceed_status.setText("事件完成");
		// } else {
		// holder.accomplish_proceed_status.setText("工单完成");
		// }
		//
		// holder.accomplish_proceed_details.setText(model.getDescription());
		// } else {
		// // 接口没有返回电话，先隐藏
		// if (model.getExecutePhone() != null) {
		// holder.accomplish_proceed_phone.setVisibility(View.VISIBLE);
		// }else{
		// holder.accomplish_proceed_phone.setVisibility(View.GONE);
		// }
		// if (whatsup == 888) {
		// holder.accomplish_proceed_status.setText("事件进行中");
		// } else {
		// holder.accomplish_proceed_status.setText("工单进行中");
		// }
		// holder.accomplish_proceed_phone
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent it = new Intent(Intent.ACTION_CALL, Uri
		// .parse("tel:" + model.getExecutePhone()));
		// context.startActivity(it);
		//
		// }
		// });
		// holder.accomplish_proceed_details.setText("派发给"
		// + model.getExecuteName() + "，" + model.getDescription());
		// }

		// holder.accomplish_proceed_time.setText(model.getCreateDate());

		holder.accomplish_proceed_phone
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it = new Intent(Intent.ACTION_CALL, Uri
								.parse("tel:" + model.getExecutePhone()));
						context.startActivity(it);

					}
				});

		if(model.getStatus() == 2){
			holder.accomplish_proceed_name.setText("派发给：" + model.getExecuteName());
			holder.accomplish_proceed_details.setText("正在处理中");
			holder.gv.setVisibility(View.GONE);
		}else if(model.getStatus() == 3){
			holder.accomplish_proceed_name.setText(model.getExecuteName());
			holder.accomplish_proceed_details.setText("已确认完成");
			holder.gv.setVisibility(View.VISIBLE);
		}

		
		final List<ImageDto> picture = model.getFiles();
		adapter = new DetailImageAdapter(context, picture);
		// 为GridView设置适配器
		holder.gv.setAdapter(adapter);
		holder.gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				files.clear();
				Intent intent = new Intent(context,
						CheckUpPictureActivity.class);
				files.addAll(picture);

				intent.putExtra("files", (Serializable) files);

				context.startActivity(intent);
			}
		});

		return convertView;
	}

	class ViewHolder {

		TextView accomplish_proceed_name;// 状态
		TextView accomplish_proceed_time;// 日期时间
		TextView accomplish_proceed_details;// 描述
		ImageView accomplish_proceed_phone;// 电话图片
		GridView gv;
	}

}
