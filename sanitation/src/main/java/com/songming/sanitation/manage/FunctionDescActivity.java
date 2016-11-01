package com.songming.sanitation.manage;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;

/**
 * 功能说明页面
 * 
 * @author Administrator
 * 
 */
public class FunctionDescActivity extends BaseActivity implements
		OnClickListener {

	private ImageView layoutbackimg;
	private TextView desc;
	private boolean fromWork;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.function_desc);

		fromWork = getIntent().getBooleanExtra("fromWork", false);
		findViews();
		initViews();
	}

	@Override
	protected void findViews() {
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		desc = (TextView) findViewById(R.id.desc);
	}

	@Override
	protected void initViews() {

		String content = "";
		if(fromWork){
		content = "审批：能帮企业员工轻松提交申请，还能让企业管理人员及时的进行批示，比如“请假”“费用上报”等，相关管理人员在收到申请后只需在手机环卫365上就可以完成审批操作。"
				+ "\n\n公告：帮助企业随时展示一些公告信息，比如“ 高温注意事项”“领导视察”，发布的公告将会更快速更便捷的以消息的形式来提醒企业全体员工。 "
				+ "\n\n日常工作：管理人员向下级安排部署工作任务，同时接收下级的工作情况汇报；下级向管理人员汇报工作进程等情况，同时接收管理人员对自己的工作安排。"
				+ "\n\n现场巡查：按制度要求进行巡查上报，一定程度上增强对发现问题和及时解决问题的意识。"
				+ "\n\n突发事件:上报工作中的非日常事件，管理人员及时接收事件上报事项，并做出相关安排。"
				+ "\n\n司机入口：关联车辆，并记录所有数据（车辆保养、车辆维修、车辆油耗）。";
		}
		desc.setText(content);
		layoutbackimg.setOnClickListener(this);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {

	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layoutbackimg:
			this.finish();
			break;

		default:
			break;
		}
	}

}
