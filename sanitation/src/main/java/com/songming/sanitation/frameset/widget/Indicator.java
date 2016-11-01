package com.songming.sanitation.frameset.widget;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.songming.sanitation.Main;
import com.songming.sanitation.R;

/**
 * 底部导航
 * 
 * @Version 1.0
 */
public class Indicator extends FrameLayout implements OnClickListener {

	/** �?��tab页面对应�?��Fragment */
	private List<Fragment> fragments;

	/** 用于切换tab */
	private ViewGroup indicatorContainer;

	/** Fragment�?��的Activity */
	private FragmentActivity fragmentActivity;

	/** Activity中所要被替换的区域的id */
	private int fragmentContentId;

	/** 当前Tab页面索引 */
	private int currentTab;

	private Main main;

	private ImageView home_image;
	private ImageView run_image;
	private ImageView image_safe_ic;
	private ImageView image_health_ic;

	private FrameLayout fLayout0;
	private FrameLayout fLayout1;
	// private FrameLayout fLayout3;
	/** 用于让调用�?在切换tab时�?增加新的功能 */
	private OnIndicatorCheckedChangedListener onIndicatorCheckedChangedListener; //

	public Indicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(R.layout.indicator,
				this, true);
		fLayout0 = (FrameLayout) view.findViewById(R.id.indicator0);
		fLayout1 = (FrameLayout) view.findViewById(R.id.indicator1);
		// fLayout3 = (FrameLayout) view.findViewById(R.id.indicator3);

		home_image = (ImageView) view.findViewById(R.id.image_safe);
		run_image = (ImageView) view.findViewById(R.id.image_health);

		image_safe_ic = (ImageView) view.findViewById(R.id.image_safe_ic);
		image_health_ic = (ImageView) view.findViewById(R.id.image_health_ic);

		init(context);
	}

	public Indicator(Context context) {
		this(context, null);
	}

	private void init(Context context) {
		indicatorContainer = (ViewGroup) findViewById(R.id.indicator_root);
	}

	public void initIndicator(Main fragmentActivity, List<Fragment> fragments,
			int fragmentContentId) {
		this.fragments = fragments;
		this.fragmentActivity = fragmentActivity;
		this.fragmentContentId = fragmentContentId;

		// 默认显示第一�?
		FragmentTransaction ft = fragmentActivity.getSupportFragmentManager()
				.beginTransaction();
		ft.add(fragmentContentId, fragments.get(0));
		ft.commit();

		home_image.setImageResource(R.drawable.safe1);
		image_safe_ic.setVisibility(View.VISIBLE);
		for (int i = 0; i < indicatorContainer.getChildCount(); i++) {
			indicatorContainer.getChildAt(i).setOnClickListener(this);
		}
	}

	/**
	 * 切换tab
	 * 
	 * @param idx
	 */
	private void showTab(int idx) {
		for (int i = 0; i < fragments.size(); i++) {
			Fragment fragment = fragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(idx);

			if (idx == i) {
				ft.show(fragment);
			} else {
				ft.hide(fragment);
			}
			// ft.commit();
			ft.commitAllowingStateLoss();
		}
		currentTab = idx; // 更新目标tab为当前tab
	}

	/**
	 * 获取�?��带动画的FragmentTransaction
	 * 
	 * @param index
	 * @return
	 */
	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = fragmentActivity.getSupportFragmentManager()
				.beginTransaction();
		// 设置切换动画
		if (index > currentTab) {
			ft.setCustomAnimations(R.anim.indicator_open_enter,
					R.anim.indicator_open_exit);
		} else {
			ft.setCustomAnimations(R.anim.indicator_close_enter,
					R.anim.indicator_close_exit);
		}
		return ft;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public Fragment getCurrentFragment() {
		return fragments.get(currentTab);
	}

	/**
	 * 将页面传回的值进行转�?
	 * 
	 * @param i
	 */
	public void onClick(int i) {
		// View view =
		// ((ViewGroup)indicatorContainer.getChildAt(i)).getChildAt(0);
		View view = indicatorContainer.getChildAt(i);

		onClick(view);
	}

	public void onClickLast() {
		onClick(currentTab);
	}

	/**
	 * 设置text
	 * 
	 * @param i
	 * @param text
	 */
	public void showTip(int i, String text) {
		ViewGroup group = (ViewGroup) indicatorContainer.getChildAt(i);
		TextView view = (TextView) group.getChildAt(1);
		view.setText(text);
		view.setVisibility(View.VISIBLE);
		ScaleAnimation anim = new ScaleAnimation(50f, 100f, 50f, 100f);
		anim.setDuration(500);
		view.startAnimation(anim);
	}

	/**
	 * 隐藏显示提示
	 * 
	 * @param i
	 */
	public void hideTip(int i) {
		ViewGroup group = (ViewGroup) indicatorContainer.getChildAt(i);
		final TextView view = (TextView) group.getChildAt(1);
		ScaleAnimation anim = new ScaleAnimation(100f, 50f, 100f, 50f);
		anim.setDuration(500);
		view.startAnimation(anim);
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				view.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}

		});
	}

	@Override
	public void onClick(View v) {
		int checkedId = v.getId();
		for (int i = 0; i < indicatorContainer.getChildCount(); i++) {
			ViewGroup group = (ViewGroup) indicatorContainer.getChildAt(i);
			if (group.getId() == checkedId) {
				group.getChildAt(0).setSelected(true);
				switch (i) {
				case 0:
					home_image.setImageResource(R.drawable.safe1);
					run_image.setImageResource(R.drawable.health);
					image_safe_ic.setVisibility(View.VISIBLE);
					image_health_ic.setVisibility(View.GONE);
					break;
				case 1:
					home_image.setImageResource(R.drawable.safe);
					run_image.setImageResource(R.drawable.health1);
					image_safe_ic.setVisibility(View.GONE);
					image_health_ic.setVisibility(View.VISIBLE);
					break;

				default:
					break;
				}

				if (currentTab == i) {
					continue;
				}
				Fragment fragment = fragments.get(i);
				FragmentTransaction ft = obtainFragmentTransaction(i);

				getCurrentFragment().onPause(); // 暂停当前tab
				// getCurrentFragment().onStop(); // 暂停当前tab

				if (fragment.isAdded()) {
					// fragment.onStart(); // 启动目标tab的onStart()
					fragment.onResume(); // 启动目标tab的onResume()
				} else {
					ft.add(fragmentContentId, fragment);
				}
				showTab(i); // 显示目标tab
				// ft.commit();
				ft.commitAllowingStateLoss();
				// 如果设置了切换tab额外功能功能接口
				if (null != onIndicatorCheckedChangedListener) {
					onIndicatorCheckedChangedListener.onIndicatorCheckedChange(
							checkedId, i);
				}
			} else {
				group.getChildAt(0).setSelected(false);
			}
		}
	}

	/**
	 * 切换tab额外功能功能接口
	 */
	static class OnIndicatorCheckedChangedListener {
		public void onIndicatorCheckedChange(int checkedId, int index) {
		}
	}

	public OnIndicatorCheckedChangedListener getOnIndicatorCheckedChangedListener() {
		return onIndicatorCheckedChangedListener;
	}

	public void setOnIndicatorCheckedChangedListener(
			OnIndicatorCheckedChangedListener onIndicatorCheckedChangedListener) {
		this.onIndicatorCheckedChangedListener = onIndicatorCheckedChangedListener;
	}

	/**
	 * @return the main
	 */
	public Main getMain() {
		return main;
	}

	/**
	 * @param main
	 *            the main to set
	 */
	public void setMain(Main main) {
		this.main = main;
	}

}
