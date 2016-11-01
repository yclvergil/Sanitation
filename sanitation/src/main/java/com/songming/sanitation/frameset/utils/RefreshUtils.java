package com.songming.sanitation.frameset.utils;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

public class RefreshUtils {

	/*
	 * 设置pullRefresh 控件上下拉 提示语句
	 */
	public static void setRefreshPrompt(PullToRefreshBase mPullListView) {

		ILoadingLayout startLabels = mPullListView.getLoadingLayoutProxy(true,
				false);
		startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在载入...");// 刷新时
		startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

		ILoadingLayout endLabels = mPullListView.getLoadingLayoutProxy(false,
				true);
		endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在载入...");// 刷新时
		endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
		// 设置下拉刷新文本
		mPullListView.getLoadingLayoutProxy(false, true)
				.setPullLabel("上拉刷新...");
		mPullListView.getLoadingLayoutProxy(false, true).setReleaseLabel(
				"放开刷新...");
		mPullListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
				"正在加载...");
		// 设置上拉刷新文本
		mPullListView.getLoadingLayoutProxy(true, false)
				.setPullLabel("下拉刷新...");
		mPullListView.getLoadingLayoutProxy(true, false).setReleaseLabel(
				"放开刷新...");
		mPullListView.getLoadingLayoutProxy(true, false).setRefreshingLabel(
				"正在加载...");
	}

}
