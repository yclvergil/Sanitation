package com.songming.sanitation.workdeal;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.workdeal.bean.ImageDto;

/**
 * 巡查详情点击，图看查看
 */
public class CheckUpPictureActivity extends FragmentActivity {

	private ViewPager picture;
	/** 上一个页面传下来的数据 */
	private List<ImageDto> list = new ArrayList<ImageDto>();
	private int position;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.checkup_picture);
		list.clear();
		list = (List<ImageDto>) getIntent().getSerializableExtra("files");
		position = getIntent().getIntExtra("index", 0);

		findViews();
		initViews();

	}

	protected void findViews() {
		picture = (ViewPager) findViewById(R.id.viewpager_picture);
	}

	protected void initViews() {

		MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
		picture.setAdapter(adapter);
		picture.setCurrentItem(position);
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
