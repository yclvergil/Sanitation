package com.songming.sanitation.workdeal.adapter;

import java.util.List;

import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.message.model.MessagePushDTO;
import com.songming.sanitation.workdeal.bean.TJobAuditDetailDto;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 审批结果子项列表适配器
 * 
 * @author Administrator
 * 
 */
public class ApprovalResultItemAdapter extends BaseAdapter {

	private Context context;
	private List<TJobAuditDetailDto> list;

	public ApprovalResultItemAdapter(Context context,
			List<TJobAuditDetailDto> list) {
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
					R.layout.approvalresult_items, null);
			holder = new HoldView();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.result = (TextView) convertView.findViewById(R.id.result);
			holder.suggestion = (TextView) convertView
					.findViewById(R.id.suggestion);
			convertView.setTag(holder);
		} else {
			holder = (HoldView) convertView.getTag();
		}
		TJobAuditDetailDto dto = list.get(position);
		holder.name.setText(dto.getExecuteName());
		int status = dto.getStatus();
		String result = "";
		if (status == 1) {
			result = "已审批同意";
			holder.result.setTextColor(context.getResources().getColor(
					R.color.title_bar_28c3b1));
		} else if (status == 2) {
			result = "已审批不同意";
			holder.result.setTextColor(context.getResources().getColor(
					R.color.red));
		} else {
			result = "未审批";
			holder.result.setTextColor(context.getResources().getColor(
					R.color.title_bar_28c3b1));
		}
		holder.result.setText(result);
		if(!StringUtilsExt.isEmpty(dto.getAuditDate())){
			holder.time.setText(StringUtilsExt.formatStr(dto.getAuditDate(), "yyyy-MM-dd HH:mm"));
		}else{
			holder.time.setText("");
		}
		
		String str = "";
		if (!StringUtilsExt.isEmpty(dto.getJobContent())) {
			str = "审批意见：" + dto.getJobContent();
		}else{
			str = "审批意见：无";
		}
		SpannableStringBuilder style = new SpannableStringBuilder(str);
		style.setSpan(
				new ForegroundColorSpan(context.getResources().getColor(
						R.color.color_333333)), 0, 5,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		holder.suggestion.setText(style);
		return convertView;
	}

	class HoldView {
		TextView name;
		TextView time;
		TextView result;
		TextView suggestion;
	}
}
