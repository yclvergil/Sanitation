package com.songming.sanitation.user;

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
import com.songming.sanitation.user.model.TUploadFileDto;

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
			return new CheckUpPictureFragment(arg0);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

		}
	}

	class CheckUpPictureFragment extends Fragment {

		private View rootView;// 缓存Fragment view
		private ImageView imageview;
		private int i;
		private DisplayImageOptions options;
		private ImageLoader imageLoader;
		private TextView pageNum;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			if (rootView == null) {
				rootView = inflater
						.inflate(R.layout.checkup_picture_item, null);
				imageview = (ImageView) rootView.findViewById(R.id.imageview);
				pageNum = (TextView) rootView.findViewById(R.id.pageNum);
			}

			pageNum.setText((i + 1) + "/" + list.size());
			
			String imgUrl = list.get(i).getFilePath();
			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.checkup_details_image)
					.showImageForEmptyUri(R.drawable.checkup_details_image)
					.showImageOnFail(R.drawable.checkup_details_image)
					.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
					.bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true)
					.cacheOnDisk(true).considerExifParams(true)
					.displayer(new FadeInBitmapDisplayer(100)).build();// 设置图片进入动画时间100ms
			imageLoader = ImageLoader.getInstance();
			imageLoader.init(ImageLoaderConfiguration
					.createDefault(getActivity()));

			imageLoader.displayImage(Constants.IMAGE_URL + imgUrl, imageview,
					options);

			// 缓存的rootView需要判断是否已经被加过parent，
			// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
			return rootView;

		}

		public CheckUpPictureFragment(int i) {
			super();
			this.i = i;
		}
	}

}
