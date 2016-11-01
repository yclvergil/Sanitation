package com.songming.sanitation.workdeal.adapter;

import java.util.List;

import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.workdeal.bean.TJobAuditDetailDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 审批申请列表适配器
 * @author Administrator
 *
 */
public class ApprovalApplyListAdapter extends BaseAdapter{
	
	private Context context;
	private List<TJobAuditDetailDto> list;

	public ApprovalApplyListAdapter(Context context, List<TJobAuditDetailDto> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setList(List<TJobAuditDetailDto> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.approval_apply_item, null);
			holder = new HoldView();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.event = (TextView) convertView.findViewById(R.id.event);
			holder.result = (TextView) convertView.findViewById(R.id.result);
			convertView.setTag(holder);
		}else{
			holder = (HoldView) convertView.getTag();
		}
		TJobAuditDetailDto dto = list.get(position);
		holder.name.setText(dto.getApplyBy());
		holder.time.setText(StringUtilsExt.formatStr(dto.getCreateDate(), "yyyy-MM-dd HH:mm"));
		holder.event.setText(dto.getRemark());
		int status = dto.getStatus();
		String result = "";
		if(status == 1){
			result = "已审批同意";
		}else if(status == 2){
			result = "已审批不同意";
		}else{
			result = "未审批";
		}
		holder.result.setText(result);
		return convertView;
	}

	class HoldView{
		TextView name;
		TextView time;
		TextView event;
		TextView result;
	}
}
