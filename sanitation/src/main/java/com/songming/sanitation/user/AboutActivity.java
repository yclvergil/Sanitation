package com.songming.sanitation.user;

import org.json.JSONObject;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;

/**
 * 版本信息
 * @author Administrator
 *
 */
public class AboutActivity extends BaseActivity implements OnClickListener{

	//顶部标题栏控件
	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;
	//版本信息
	private String version;
	private TextView versions;//版本名称
	private TextView description;//版本描述
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.version_info);
		
		try {
			version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		findViews();
		initViews();
	}

	@Override
	protected void findViews() {

		//初始化标题栏
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		
		description = (TextView) findViewById(R.id.versionDescription);
		versions = (TextView) findViewById(R.id.versions);
		versions.setText(getResources().getText(R.string.app_name)+" v"+version+" for android");
		
		
	}

	@Override
	protected void initViews() {
		
		topTitle.setText("版本信息");
		
		String versionDescription = "\t\t\t"+ getResources().getText(R.string.app_name) +"是湖南中威斯顿为环卫事业专门研发的一套应用平台。中威斯顿以信息数字化为宗旨，迎合国家的“互联网+”政策，为降低环卫企业的运营成本，提高企业的管理质量，以及实时监控车辆和人员动态而设计的一款智能APP应用。";
		
		description.setText(versionDescription);
		
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.layoutbackimg:
			this.finish();
			break;

		default:
			break;
		}
	}

}
