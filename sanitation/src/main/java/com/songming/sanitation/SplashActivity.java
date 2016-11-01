package com.songming.sanitation;

import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.android.volley.VolleyError;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.user.LoginAcitivity;

/**
 * 启动页
 * 
 * @author
 * 
 */
public class SplashActivity extends BaseActivity {

	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	// 延迟2秒
	private static final long SPLASH_DELAY_MILLIS = 2000;
	private boolean fromNotification;
	private boolean signNotification;

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);

		// 百度云推送
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				Constants.PUSH_LOGIN_TYPE_API_KEY);

		// 判断点击图标的意图，如果是原来的应用不存在，点击应用ICON进入应用
		// 如果应用已经存在用户点击HOME键之后重新点击应用的ICON 重新进入app保证进入的是按home键时的Activity
		// 而不是重新进入SplashActivity---> MainActivity
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
			finish();
			return;
		}

		fromNotification = getIntent().getBooleanExtra("fromNotification",
				false);
		signNotification = getIntent().getBooleanExtra("signNotification",
				false);

		setContentView(R.layout.activity_splash);
		findViews();
		initViews();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void findViews() {

	}

	@Override
	protected void initViews() {

		boolean isFirstIn = SharedPreferencesUtils.getBooleanValue(this,
				"isFirstIn", true);

		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (!isFirstIn) {
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}

	}

	/**
	 * Handler:跳转到不同界面
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				String loginName = SharedPreferencesUtils.getStringValue(
						SplashActivity.this, SharedPreferencesUtils.LOGIN_NAME,
						"");
				if ("".equals(loginName)) {
					goLogin();
				} else {
					// 存在登陆凭证，直接进入主页面
					goHome();
				}

				break;
			case GO_GUIDE:
				goGuide();
				break;
			}
			super.handleMessage(msg);
		}
	};

	/** 跳转到主页面 */
	private void goHome() {

		Intent intent = new Intent(SplashActivity.this, Main.class);
		if (fromNotification) {
			intent.putExtra("fromNotification", true);
		}
		if (signNotification) {
			intent.putExtra("signNotification", true);
		}
		startActivity(intent);
		this.finish();
	}

	/** 跳转到登陆页面 */
	private void goLogin() {

		Intent intent = new Intent(SplashActivity.this, LoginAcitivity.class);
		startActivity(intent);
		this.finish();
	}

	/** 跳转到引导页面 */
	private void goGuide() {

		Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {

	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {

	}

}
