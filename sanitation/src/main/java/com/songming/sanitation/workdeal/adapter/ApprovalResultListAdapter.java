package com.songming.sanitation.workdeal.adapter;

import java.util.List;

import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.message.model.MessagePushDTO;
import com.songming.sanitation.workdeal.bean.TJobAuditDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 审批结果列表适配器
 * @author Administrator
 *
 */
public class ApprovalResultListAdapter extends BaseAdapter{
	
	private Context context;
	private List<TJobAuditDto> list;

	public ApprovalResultListAdapter(Context context, List<TJobAuditDto> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setList(List<TJobAuditDto> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if(list == null){
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
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.approval_result_item, null);
			holder = new HoldView();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.result = (TextView) convertView.findViewById(R.id.result);
			convertView.setTag(holder);
		}else{
			holder = (HoldView) convertView.getTag();
		}
		TJobAuditDto dto = list.get(position);
		holder.name.setText(dto.getRemark());
		holder.time.setText(StringUtilsExt.formatStr(dto.getCreateDate(), "yyyy-MM-dd HH:mm"));
		int status = dto.getStatus();
		if(status == 1){
			holder.result.setText("已通过");
		}else{
			holder.result.setText("未通过");
		}
		return convertView;
	}

	class HoldView{
		TextView name;
		TextView time;
		TextView result;
	}
}
