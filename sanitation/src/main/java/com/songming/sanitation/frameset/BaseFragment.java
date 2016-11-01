package com.songming.sanitation.frameset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import com.songming.sanitation.frameset.widget.NavigateView;

/**
 * 描述：基类封装了http请求方法
 * 
 * @since 2014.5.28
 * */
public abstract class BaseFragment extends Fragment implements
		Listener<JSONObject>, ErrorListener {

	private static final String TAG = BaseFragment.class.getSimpleName();

	/** view */
	protected View rootView;

	/** app */
	protected ApplicationExt applications;

	protected NavigateView navigateView = null;

	protected RequestQueue requestQueue;

	protected List<Object> requestTags = new ArrayList<Object>();

	protected OnFragmentCallActivityListener fragmentListerner;

	private CustomDialogView customDialogView;
	private RedrawCustomDialogViewThread redrawCdvRunnable;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		applications = (ApplicationExt) getActivity().getApplication();
		requestQueue = VolleyQueue.getRequestQueue();
		requestTags.clear();

	}

	@Override
	public void onResume() {
		super.onResume();
		// MobclickAgent.onPageStart("TabMenuActivity");
	}

	@Override
	public void onPause() {
		super.onPause();
		// MobclickAgent.onPageEnd("TabMenuActivity");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onStop() {
		super.onStop();
		for (Object o : requestTags)
			VolleyQueue.cancelQueue(o);
	}

	/**
	 * 发送请求的公用方法 默认不存缓存，也不读取
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
			String methodName, String url) throws Exception {
		// JsonObjectRequest request=new JsonObjectRequest();
		this.requestHttp(jsonObject, requestTag, methodName, url, false, false);
	}

	/**
	 * 发送请求的公用方法 默认存缓存
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
			String methodName, String url, boolean isReadCache)
			throws Exception {
		this.requestHttp(jsonObject, requestTag, methodName, url, isReadCache,
				true);
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
			throws Exception {

		// boolean hasNet = true;
		// 首先检测下网络是否连接,若没有配置存缓存，无网络则直接不处理
		if (!WifiNetUtils.isNetworkConnected(ApplicationExt.getApplication())) {
			ToastUtil.makeText(ApplicationExt.getApplication(),
					R.string.nowifi, Toast.LENGTH_SHORT);
			hideLoading();
			return;
		}

		/*
		 * if(!WifiNetUtils.isNetworkConnected(ApplicationExt.getApplication())){
		 * if(!isCache){
		 * Toast.makeText(ApplicationExt.getApplication(),R.string.
		 * nowifi,Toast.LENGTH_SHORT).show(); hideLoading(); return ; }else{
		 * //若没有网络则默认读取缓存，缓存没有数据则不做处理 isReadCache=true; hasNet=false; } }
		 */

		// JsonObjectRequest request=new JsonObjectRequest();
		if (jsonObject == null || methodName == null || requestTag == null)
			return;
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", jsonObject.toString());

		// url = url == null ? HttpUrlUtils.httpUrl : url;
		if (BuildConfig.DEBUG)
			Log.i(TAG, url + methodName + "?param=" + jsonObject.toString());

		VolleyRequestVo vo = new VolleyRequestVo();
		vo.setRequestTag((String) requestTag);
		vo.setRequestUrl(methodName + jsonObject.toString());
		vo.setIsreadCache(isReadCache);
		vo.setCache(isCache);

		/*
		 * if (isReadCache) { // 取缓存 String contentJsonStr =
		 * VolleyQueue.getStrLruCache().getString( vo.getRequestUrl()); if
		 * (contentJsonStr != null) { //
		 * contentJsonStr=DES3.decode(contentJsonStr); try { if
		 * (BuildConfig.DEBUG) Log.i(TAG, "cache:" + vo.getRequestUrl() + "=" +
		 * contentJsonStr); successCallback(new JSONObject(contentJsonStr), vo);
		 * return; } catch (JSONException e) { Toast.makeText(getActivity(),
		 * "json解析出错：" + e.getMessage(), Toast.LENGTH_SHORT).show(); } } else {
		 * // 若没有网络则默认读取缓存，缓存没有数据则不做处理 if (!hasNet) {
		 * Toast.makeText(ApplicationExt.getApplication(), R.string.nowifi,
		 * Toast.LENGTH_SHORT).show(); hideLoading(); return; } } }
		 */
		JsonObjectReuestExt request = new JsonObjectReuestExt(url + methodName,
				jsonObject, this, this, map);

		request.setTag(vo);

		requestTags.add(vo);

		// 设置请求时间,重连次数
		request.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));

		requestQueue.add(request);

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
			loadDialog = new Dialog(getActivity(), R.style.dialog);
			loadDialog.setContentView(R.layout.dialog_loading_new);
			// loadDialog.setContentView(R.layout.dialog_load_new);
			// loadDialog.setContentView(R.layout.dialog_loading);
			// customDialogView = (CustomDialogView) loadDialog
			// .findViewById(R.id.customDialogView);
			// redrawCdvRunnable = new RedrawCustomDialogViewThread();
			// new Thread(redrawCdvRunnable).start();
			// loadDialog.setOnCancelListener(new OnCancelListener() {
			//
			// @Override
			// public void onCancel(DialogInterface dialog) {
			// if (requestTag != null)
			// VolleyQueue
			// .cancelQueue(new VolleyRequestVo(requestTag));
			// }
			// });
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
//				redrawCdvRunnable.setRun(false);
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
		errorCallback(arg0, requestTag);

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

			if (BuildConfig.DEBUG)
				Log.i(TAG, jsonObject.toString());

			/*
			 * if (jsonObject.optInt("flag", 1)>0) {
			 * Toast.makeText(getActivity(), jsonObject.optString("msg"),
			 * Toast.LENGTH_SHORT).show(); hideLoading(); return ; }
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
			// e.printStackTrace();
			/*
			 * Toast.makeText(getActivity(), "json解析出错：" + e.getMessage(),
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
	 * 
	 * 
	 * 
	 */
	protected void changeFragment(FragmentTransaction transaction,
			Fragment targetFragment, Bundle bundle, int containerResid) {

		targetFragment.setArguments(bundle);
		transaction.replace(containerResid, targetFragment);
		if (transaction.isAddToBackStackAllowed())
			transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commitAllowingStateLoss();
	}

	/**
	 * 
	 * 为了允许一个Fragment跟包含他的Activity通信
	 * */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			if (activity instanceof OnFragmentCallActivityListener) {
				fragmentListerner = (OnFragmentCallActivityListener) activity;
				// fragmentListerner.onProcess(getArguments());
			}

		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentCallActivityListener");
		}
	}

	/**
	 * 
	 * 为了允许一个Fragment跟包含他的Activity通信
	 * */
	public static interface OnFragmentCallActivityListener {

		/** 为了允许一个Fragment跟包含他的Activity通信 */
		public void onProcess(Bundle extras);
	}

	/**
	 * 输入框校验出错时播放放抖动动画
	 * 
	 * */
	protected void startShakeAnim(View v) {

		v.startAnimation(AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake));

	}

	// // 判断是连接还是断开
	// protected void connectedOrDis(String action) {
	// if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(action)) {
	// Log.d(App.TAG, "111111111 连接完成");
	// // navigateView.setEnable(true);
	//
	// } else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(action)) {
	// Log.d(App.TAG, "111111111 连接断开");
	// // navigateView.setEnable(false);
	// }
	// }

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

}
