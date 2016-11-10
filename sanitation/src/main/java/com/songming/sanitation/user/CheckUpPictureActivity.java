package com.songming.sanitation.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.songming.sanitation.R;
import com.songming.sanitation.user.model.TUploadFileDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 巡查详情点击，图看查看
 */
public class CheckUpPictureActivity extends FragmentActivity {

	private ViewPager picture;
	/** 上一个页面传下来的数据 */
	private List<TUploadFileDto> list = new ArrayList<TUploadFileDto>();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.checkup_picture);
		list.clear();
		list = (List<TUploadFileDto>) getIntent().getSerializableExtra("files");

		findViews();
		initViews();

	}

	protected void findViews() {
		picture = (ViewPager) findViewById(R.id.viewpager_picture);
	}

	protected void initViews() {

		MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
		picture.setAdapter(adapter);
	}

	// 内部类 Fragment的点击事件
	class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			// 输出的需要改变成所创建的碎片即可
			return CheckUpPictureFragment.newInstance(arg0, list);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

		}
	}

}
