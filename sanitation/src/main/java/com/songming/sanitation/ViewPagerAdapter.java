package com.songming.sanitation;

import java.util.List;

import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.user.LoginAcitivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 引导页面适配器
 * 
 * @author Administrator
 * 
 */
public class ViewPagerAdapter extends PagerAdapter {

	// 界面列表
	private List<View> views;
	private Activity activity;

	public ViewPagerAdapter(List<View> views, Activity activity) {
		this.views = views;
		this.activity = activity;
	}

	// 销毁arg1位置的界面
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	// 获得当前界面数
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	// 初始化arg1位置的界面
	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(views.get(arg1), 0);

		if (arg1 == views.size() - 1) {
			Button start = (Button) arg0.findViewById(R.id.btn_into);
			start.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					goLogin();
				}

			});
		}

		return views.get(arg1);
	}

	private void goLogin() {
		//设置已经引导过
		SharedPreferencesUtils.setBooleanValue(activity, "isFirstIn", false);
		// 跳转
		Intent intent = new Intent(activity, LoginAcitivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

}
