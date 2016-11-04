package com.songming.sanitation.frameset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.BuildConfig;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.ToastUtil;
import com.songming.sanitation.frameset.utils.WifiNetUtils;
import com.songming.sanitation.frameset.widget.CustomDialogView;

/**
 * 描述：基类封装了http请求方法
 * 
 * @author liudong
 * @since 1.0 2014.5.19
 * */
public abstract class BaseActivity extends FragmentActivity implements
		Listener<JSONObject>, ErrorListener {

	private static final String TAG = BaseActivity.class.getSimpleName();

	/** view */
	protected View view;
	/** app */
	protected ApplicationExt applications;

	protected RequestQueue requestQueue;

	protected List<Object> requestTags = new ArrayList<Object>();

	private CustomDialogView customDialogView;
	private RedrawCustomDialogViewThread redrawCdvRunnable;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		applications = (ApplicationExt) getApplication();
		requestQueue = VolleyQueue.getRequestQueue();
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
		requestTags.clear();

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 统计时长
		// MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息
		// MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		for (Object o : requestTags)
			VolleyQueue.cancelQueue(o);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) { // 截获菜单事件
		return false; // 返回为true 则显示系统menu
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");// 必须创建一项
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 发送请求的公用方法
	 * 
	 * @param jsonObject
	 *            封装请求参数的对象
	 * @param requestTag
	 *            请求标识
	 * @param methodName
	 *            请求方法名称
	 * @param url
	 *            请求的URL
	 * */
	protected void requestHttp(JSONObject jsonObject, Object requestTag,
			String methodName, String url, boolean isReadCache, boolean isCache)
			throws Exception// throws AppException
	{
		// 首先检测下网络是否连接
		if (!WifiNetUtils.isNetworkConnected(ApplicationExt.getApplication())) {
			ToastUtil.makeText(ApplicationExt.getApplication(),
					R.string.nowifi, Toast.LENGTH_SHORT);
			hideLoading();
			return;
		}

		// JsonObjectRequest request=new JsonObjectRequest();
		if (jsonObject == null || methodName == null || requestTag == null)
			return;
		String requestStr = jsonObject.toString();
		if (requestStr.contains("\"[") && requestStr.contains("]\"")) {
			int start = requestStr.indexOf("[");
			int end = requestStr.indexOf("]");
			String s = requestStr.substring(0, start - 1)
					+ requestStr.substring(start, end + 1)
					+ requestStr.substring(end + 2, requestStr.length());
			requestStr = StringEscapeUtils.unescapeJava(s);
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("param", requestStr);
		if (BuildConfig.DEBUG)
			Log.i(TAG, url + methodName + "?param=" + requestStr);
		Log.i("aa", url + methodName + "?param=" + requestStr);

		VolleyRequestVo vo = new VolleyRequestVo();
		vo.setRequestTag((String) requestTag);
		vo.setRequestUrl(methodName + requestStr);
		vo.setIsreadCache(isReadCache);
		vo.setCache(isCache);

		/*
		 * if(isReadCache){ //取缓存 String contentJsonStr=
		 * VolleyQueue.getStrLruCache().getString(vo.getRequestUrl());
		 * if(contentJsonStr!=null){ try {
		 * //contentJsonStr=DES3.decode(contentJsonStr);
		 * 
		 * if(BuildConfig.DEBUG) Log.i(TAG,
		 * "cache:"+vo.getRequestUrl()+"="+contentJsonStr); successCallback(new
		 * JSONObject(contentJsonStr),vo); return ; } catch (JSONException e) {
		 * Toast.makeText(this, "json解析出错："+e.getMessage(),
		 * Toast.LENGTH_SHORT).show(); } }else{ //若没有网络则默认读取缓存，缓存没有数据则不做处理
		 * if(!hasNet){
		 * Toast.makeText(ApplicationExt.getApplication(),R.string.nowifi
		 * ,Toast.LENGTH_SHORT).show(); hideLoading(); return ; } } }
		 */

		JsonObjectReuestExt request = new JsonObjectReuestExt(url + methodName,
				jsonObject, this, this, map);
		Log.d("TestBaseActivity", url + methodName+"--------"+jsonObject.toString());

		// 设置请求时间,重连次数
		request.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));

		request.setTag(vo);

		requestTags.add(vo);

		requestQueue.add(request);
	}

	/**
	 * 发送请求的公用方法 默认不存取缓存
	 * 
	 * @param jsonObject
	 *            封装请求参数的对象
	 * @param requestTag
	 *            请求标识
	 * @param methodName
	 *            请求方法名称
	 * @param url
	 *            请求的URL
	 * 
	 * */
	protected void requestHttp(JSONObject jsonObject, Object requestTag,
			String methodName, String url) throws Exception// throws
															// AppException
	{
		this.requestHttp(jsonObject, requestTag, methodName, url, false, false);
	}

	/**
	 * 发送请求的公用方法 默认不存取缓存
	 * 
	 * @param jsonObject
	 *            封装请求参数的对象
	 * @param requestTag
	 *            请求标识
	 * @param methodName
	 *            请求方法名称
	 * @param url
	 *            请求的URL
	 * 
	 * */
	protected void requestHttpMap(Map<String, String> jsonObject,
			Object requestTag, String methodName, String url) throws Exception// throws
																				// AppException
	{
		this.requestHttpMap(jsonObject, requestTag, methodName, url, false,
				false);
	}

	/**
	 * 发送请求的公用方法
	 * 
	 * @param jsonObject
	 *            封装请求参数的对象
	 * @param requestTag
	 *            请求标识
	 * @param methodName
	 *            请求方法名称
	 * @param url
	 *            请求的URL
	 * */
	protected void requestHttpMap(Map<String, String> jsonObject,
			Object requestTag, String methodName, String url,
			boolean isReadCache, boolean isCache) throws Exception// throws
																	// AppException
	{
		// 首先检测下网络是否连接
		if (!WifiNetUtils.isNetworkConnected(ApplicationExt.getApplication())) {
			ToastUtil.makeText(ApplicationExt.getApplication(),
					R.string.nowifi, Toast.LENGTH_SHORT);
			hideLoading();
			return;
		}

		// JsonObjectRequest request=new JsonObjectRequest();
		if (jsonObject == null || methodName == null || requestTag == null)
			return;
		if (BuildConfig.DEBUG)
			Log.i(TAG, url + methodName + "?" + jsonObject.toString());

		VolleyRequestVo vo = new VolleyRequestVo();
		vo.setRequestTag((String) requestTag);
		vo.setRequestUrl(methodName + jsonObject.toString());
		vo.setIsreadCache(isReadCache);
		vo.setCache(isCache);

		/*
		 * if(isReadCache){ //取缓存 String contentJsonStr=
		 * VolleyQueue.getStrLruCache().getString(vo.getRequestUrl());
		 * if(contentJsonStr!=null){ try {
		 * //contentJsonStr=DES3.decode(contentJsonStr);
		 * 
		 * if(BuildConfig.DEBUG) Log.i(TAG,
		 * "cache:"+vo.getRequestUrl()+"="+contentJsonStr); successCallback(new
		 * JSONObject(contentJsonStr),vo); return ; } catch (JSONException e) {
		 * Toast.makeText(this, "json解析出错："+e.getMessage(),
		 * Toast.LENGTH_SHORT).show(); } }else{ //若没有网络则默认读取缓存，缓存没有数据则不做处理
		 * if(!hasNet){
		 * Toast.makeText(ApplicationExt.getApplication(),R.string.nowifi
		 * ,Toast.LENGTH_SHORT).show(); hideLoading(); return ; } } }
		 */

		JsonObjectReuestExt request = new JsonObjectReuestExt(url + methodName,
				new JSONObject(jsonObject), this, this, jsonObject);

		// 设置请求时间,重连次数
		request.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));

		request.setTag(vo);

		requestTags.add(vo);

		requestQueue.add(request);
	}

	/**
	 * 发送请求的公用方法 默认存取缓存
	 * 
	 * @param jsonObject
	 *            封装请求参数的对象
	 * @param requestTag
	 *            请求标识
	 * @param methodName
	 *            请求方法名称
	 * @param url
	 *            请求的URL
	 * 
	 * */
	protected void requestHttp(JSONObject jsonObject, Object requestTag,
			String methodName, String url, boolean isReadCache)
			throws Exception {
		this.requestHttp(jsonObject, requestTag, methodName, url, isReadCache,
				true);
	}

	final class RedrawCustomDialogViewThread implements Runnable {

		private boolean isRun = true;

		@Override
		public void run() {

			while (isRun) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 通知重绘
				customDialogView.postInvalidate();
			}
		}

		public boolean isRun() {
			return isRun;
		}

		public void setRun(boolean isRun) {
			this.isRun = isRun;
		}

	}

	private Dialog loadDialog;

	protected void showLoading(String msgTip, final String requestTag) {
		if (loadDialog == null) {
			loadDialog = new Dialog(this, R.style.ProgressDialogTheme);
			loadDialog.setContentView(R.layout.dialog_loading_new);

			loadDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// for (Object o : requestTags)
					if (requestTag != null)
						VolleyQueue
								.cancelQueue(new VolleyRequestVo(requestTag));
				}
			});
			loadDialog.setCancelable(true);
			loadDialog.setCanceledOnTouchOutside(false);
		}

		if (msgTip != null) {
			TextView msg = (TextView) loadDialog.getWindow().findViewById(
					R.id.loadMsg);
			msg.setText(msgTip);
		}

		if (loadDialog.isShowing())
			loadDialog.dismiss();
		loadDialog.show();
	}

	protected void hideLoading() {
		try {
			if (loadDialog != null && loadDialog.isShowing()) {
				// redrawCdvRunnable.setRun(false);
				loadDialog.dismiss();
				loadDialog = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绑定控件
	 * 
	 */
	protected abstract void findViews();

	/**
	 * 控件初始化
	 * 
	 */
	protected abstract void initViews();

	@Override
	public void onErrorResponse(VolleyError arg0, Object requestTag) {
		try {
			Log.e("LOGIN-ERROR", arg0.getMessage(), arg0);
			byte[] htmlBodyBytes = arg0.networkResponse.data;
			Log.e("LOGIN-ERROR", new String(htmlBodyBytes), arg0);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			errorCallback(arg0, requestTag);
		}
	}

	/**
	 * 描述：发送HTTP请求后的回调方法
	 * 
	 * @param volleyError
	 *            框架请求错误对象
	 * @param requestTag
	 *            请求标识
	 * **/
	protected abstract void errorCallback(VolleyError volleyError,
			Object requestTag);

	@Override
	public void onResponse(JSONObject jsonObject, Object requestTag) {

		try {
			System.out.println("-----jsonObject-----" + jsonObject);
			if (BuildConfig.DEBUG)
				Log.i(TAG, jsonObject.toString());
			/*
			 * if (jsonObject.optInt("flag",1)>0) { Toast.makeText(this,
			 * jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
			 * hideLoading(); return ; }
			 */
			if (requestTag instanceof VolleyRequestVo) {
				VolleyRequestVo vo = (VolleyRequestVo) requestTag;
				if (vo.isCache()) {
					String requestUrl = vo.getRequestUrl();
					// 加缓存
					// VolleyQueue.getStrLruCache().putString(requestUrl,
					// DES3.encode(jsonObject.toString()));
					VolleyQueue.getStrLruCache().putString(requestUrl,
							jsonObject.toString());
				}
			}

			successCallback(jsonObject, requestTag);

		} catch (Exception e) {

			/*
			 * Toast.makeText(this, "json解析出错：" + e.getMessage(),
			 * Toast.LENGTH_SHORT).show();
			 */
		}
	}

	/**
	 * 描述：发送HTTP请求后的回调方法
	 * 
	 * @param jsonObject
	 *            解密后的数据部分
	 * @param requestTag
	 *            请求标识
	 * **/
	protected abstract void successCallback(JSONObject jsonObject,
			Object requestTag);

	/**
	 * 
	 * overridePendingTransition Description: override动画
	 * 
	 * @param activity
	 *            当前activity
	 * @param incoming_anim
	 *            进入动画
	 * @param outgoing_anim
	 *            出去动画
	 */
	protected void overridePendingTransition(Activity activity,
			int incoming_anim, int outgoing_anim) {
		activity.overridePendingTransition(incoming_anim, outgoing_anim);
	}

	protected void startTransition(Activity activity) {
		overridePendingTransition(activity, R.anim.push_left_in,
				R.anim.push_left_out);
	}

	/**
	 * 启动页面
	 * 
	 * @param activity
	 * 
	 */
	protected void startActivity(Context activity, Class<?> targetCls) {
		Intent intent = new Intent(activity, targetCls);
		activity.startActivity(intent);
		if (activity instanceof Activity) {
			startTransition((Activity) activity);
		}
	}

	/**
	 * 启动页面
	 * 
	 * @param activity
	 * 
	 */
	protected void startActivity(Context activity, Class<?> targetCls,
			Bundle bundle) {
		Intent intent = new Intent(activity, targetCls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		activity.startActivity(intent);
		if (activity instanceof Activity) {
			startTransition((Activity) activity);
		}
	}

	protected void startActivityForResult(Context activity, Class<?> targetCls,
			Bundle bundle, int requestCode) {
		Intent intent = new Intent(activity, targetCls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		if (activity instanceof Activity) {
			((Activity) activity).startActivityForResult(intent, requestCode);
			startTransition((Activity) activity);
		}
	}

	/**
	 * 输入框校验出错时播放放抖动动画
	 * 
	 * */
	protected void startShakeAnim(View v) {
		v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//home键应用转入后台运行
		if(keyCode == KeyEvent.KEYCODE_HOME){
			moveTaskToBack(true);
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
}
