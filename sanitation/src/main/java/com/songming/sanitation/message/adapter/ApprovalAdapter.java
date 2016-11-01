package com.songming.sanitation.message.adapter;

import java.util.List;

import com.songming.sanitation.R;
import com.songming.sanitation.user.model.UserDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 添加审批人适配器
 * @author Administrator
 *
 */
public class ApprovalAdapter extends BaseAdapter{
	
	private Context context;
	private List<UserDto> list;

	public ApprovalAdapter(Context context, List<UserDto> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setList(List<UserDto> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	
	public void deleteUser(UserDto user){
		list.remove(user);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.addpersonal_item, null);
			holder = new HoldView();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		}else{
			holder = (HoldView) convertView.getTag();
		}
		holder.name.setText(list.get(position).getName());
		return convertView;
	}

	class HoldView{
		TextView name;
	}
}
