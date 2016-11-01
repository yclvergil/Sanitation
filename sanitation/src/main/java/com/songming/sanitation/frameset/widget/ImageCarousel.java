package com.songming.sanitation.frameset.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.ImageUtil;
import com.baidu.panosdk.plugin.indoor.util.ScreenUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 顶部轮播广告位
 * 
 * @author zyj
 * 
 */
public class ImageCarousel extends FrameLayout implements OnTouchListener {
	private int count;
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;

	private List<ImageView> imageViews;
	private Context context;
	private ViewPager vp;
	private boolean isAutoPlay;
	private int currentItem;
	private int delayTime;
	private LinearLayout ll_dot;
	private List<ImageView> iv_dots;
	private Handler handler = new Handler();

	private ItemClickInterface itemtClick = null;
	private float currentX;
	private float currentY;
	private long currentTime;

	public ImageCarousel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initImageLoader(context);
		initData();
	}

	public void setItemtClick(ItemClickInterface itemtClick) {
		this.itemtClick = itemtClick;
	}

	public ImageCarousel(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ImageCarousel(Context context) {
		this(context, null);
	}

	private void initData() {
		imageViews = new ArrayList<ImageView>();
		iv_dots = new ArrayList<ImageView>();
		delayTime = 2000;
	}

	public void setImagesUrl(String[] imagesUrl) {
		stopCarousel();
		initLayout();
		initImgFromNet(imagesUrl);
		showTime();
	}

	public void setImagesRes(int[] imagesRes) {
		stopCarousel();
		initLayout();
		initImgFromRes(imagesRes);
		showTime();
	}

	private void initLayout() {
		imageViews.clear();
		View view = LayoutInflater.from(context).inflate(
				R.layout.carousel_layout, this, true);
		vp = (ViewPager) view.findViewById(R.id.vp);
		ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
		ll_dot.removeAllViews();
		iv_dots.clear();
		vp.setOnTouchListener(this);
	}

	private void initImgFromRes(int[] imagesRes) {
		count = imagesRes.length;
		for (int i = 0; i < count; i++) {
			ImageView iv_dot = new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;
			iv_dot.setImageResource(R.drawable.dot_blur);
			ll_dot.addView(iv_dot, params);
			iv_dots.add(iv_dot);
		}
		iv_dots.get(0).setImageResource(R.drawable.dot_focus);

		for (int i = 0; i <= count + 1; i++) {
			ImageView iv = new ImageView(context);
			iv.setScaleType(ScaleType.CENTER_CROP);
			iv.setImageResource(R.drawable.loading);
			
			if (i == 0) {

				loadLocalImage(imagesRes[count - 1], iv);
			} else if (i == count + 1) {
				loadLocalImage(imagesRes[0], iv);
			} else {
				loadLocalImage(imagesRes[i - 1], iv);
			}
			imageViews.add(iv);
		}
	}

	private void initImgFromNet(String[] imagesUrl) {
		count = imagesUrl.length;
		for (int i = 0; i < count; i++) {
			ImageView iv_dot = new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;
			iv_dot.setImageResource(R.drawable.dot_blur);
			ll_dot.addView(iv_dot, params);
			iv_dots.add(iv_dot);
		}
		iv_dots.get(0).setImageResource(R.drawable.dot_focus);

		for (int i = 0; i <= count + 1; i++) {
			ImageView iv = new ImageView(context);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setImageResource(R.drawable.loading);
			if (i == 0) {
				mImageLoader.displayImage(imagesUrl[count - 1], iv, options);
			} else if (i == count + 1) {
				mImageLoader.displayImage(imagesUrl[0], iv, options);
			} else {
				mImageLoader.displayImage(imagesUrl[i - 1], iv, options);
			}
			imageViews.add(iv);
		}
	}

	private void showTime() {
		vp.setAdapter(new KannerPagerAdapter());
		vp.setFocusable(true);
		vp.setCurrentItem(1);
		currentItem = 1;
		vp.setOnPageChangeListener(new MyOnPageChangeListener());
		startPlay();
	}

	private void startPlay() {
		isAutoPlay = true;
		handler.postDelayed(task, 2000);
	}

	public void initImageLoader(Context context) {
		ImageLoaderConfiguration config = ImageUtil
				.getImageLoaderConfiguration(context);

		options = ImageUtil.getDisplayImageOptions();

		ImageLoader.getInstance().init(config);
		mImageLoader = ImageLoader.getInstance();
	}

	private void stopCarousel() {
		handler.removeCallbacks(task);
	}

	private final Runnable task = new Runnable() {
		@Override
		public void run() {
			if (isAutoPlay) {
				currentItem = currentItem % (count + 1) + 1;
				if (currentItem == 1) {
					vp.setCurrentItem(currentItem, false);
					handler.post(task);
				} else {
					vp.setCurrentItem(currentItem);
					handler.postDelayed(task, 3000);
				}
			} else {
				handler.postDelayed(task, 5000);
			}
		}
	};

	class KannerPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			container.addView(imageViews.get(position));
			return imageViews.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageViews.get(position));
		}

	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			case 1:
				isAutoPlay = false;
				break;
			case 2:
				isAutoPlay = true;
				break;
			case 0:
				if (vp.getCurrentItem() == 0) {
					vp.setCurrentItem(count, false);
				} else if (vp.getCurrentItem() == count + 1) {
					vp.setCurrentItem(1, false);
				}
				currentItem = vp.getCurrentItem();
				isAutoPlay = true;
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < iv_dots.size(); i++) {
				if (i == arg0 - 1) {
					iv_dots.get(i).setImageResource(R.drawable.dot_focus);
				} else {
					iv_dots.get(i).setImageResource(R.drawable.dot_blur);
				}
			}
		}
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		Log.i("ONTOUCH", "" + arg1.getAction());
		switch (arg1.getAction()) {
		case MotionEvent.ACTION_DOWN:
			stopCarousel();
			currentX = arg1.getX();
			currentY = arg1.getY();
			currentTime = System.currentTimeMillis();
			break;
		case MotionEvent.ACTION_UP:
			if (arg1.getX() == currentX && currentY == arg1.getY()
					&& (System.currentTimeMillis() - currentTime) < 1000
					&& itemtClick != null) {
				itemtClick.viewPageItemClickListener(vp.getCurrentItem());
				handler.postDelayed(task, 2000);
				return true;
			}
			handler.postDelayed(task, 2000);
			break;
		default:
			break;
		}
		return false;
	}

	public void loadLocalImage(int resId, ImageView view) {
		// if (mImageLoader != null) {
		// String url = "drawable://" + resId;
		// mImageLoader.displayImage(url, view, options);
		// }
		Bitmap bitmap = ImageUtil.readBitMap(context, resId);
		view.setImageBitmap(bitmap);
		
	}

}
