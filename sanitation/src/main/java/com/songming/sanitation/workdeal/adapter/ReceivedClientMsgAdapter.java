package com.songming.sanitation.workdeal.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.songming.sanitation.R;
import com.songming.sanitation.workdeal.ReceivedBossMsgDetailActivity;
import com.songming.sanitation.workdeal.ReceivedClientMsgDetailsActivity;
import com.songming.sanitation.workdeal.bean.TJobAuditDetailDto;

public class ReceivedClientMsgAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private List<TJobAuditDetailDto> list;

	public ReceivedClientMsgAdapter(Context context,
			List<TJobAuditDetailDto> list) {
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	public void setData(List<TJobAuditDetailDto> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		if (list != null) {
			return list.size();
		} else {

			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (list != null) {
			return list.get(position);
		} else {

			return 0;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.received_client_msg_item,
					null);
			holder = new ViewHolder();
			holder.received_title = (TextView) convertView
					.findViewById(R.id.received_title);
			holder.received_content = (TextView) convertView
					.findViewById(R.id.received_content);
			holder.received_name = (TextView) convertView
					.findViewById(R.id.received_name);
			holder.received_time = (TextView) convertView
					.findViewById(R.id.received_time);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		final TJobAuditDetailDto dto = list.get(position);

		if (dto.getJobTitle() != null) {
			holder.received_title.setText("汇报标题："
					+ dto.getJobTitle().toString());
		} else {
			holder.received_title.setText("汇报标题：");
		}

//		if (dto.getJobContent() != null) {
//			holder.received_content.setText("\t\t"
//					+ dto.getJobContent().toString());
//		} else {
//			holder.received_content.setText("无内容！");
//		}

		if (dto.getCreateName() != null) {
			holder.received_name.setText("汇报人："
					+ dto.getCreateName().toString());
		} else {
			holder.received_name.setText("汇报人：");
		}

		if (dto.getJobDate() != null) {
			holder.received_time.setText("汇报时间：" + dto.getJobDate().toString());
		} else {
			holder.received_time.setText("汇报时间：");
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Bundle bundle = new Bundle();

				Intent intent = new Intent(context,
						ReceivedClientMsgDetailsActivity.class);

				bundle.putSerializable("dto", dto);
				intent.putExtras(bundle);
				context.startActivity(intent);

			}
		});

		return convertView;
	}

	class ViewHolder {
		TextView received_title;
		TextView received_content;
		TextView received_name;
		TextView received_time;

	}

}
