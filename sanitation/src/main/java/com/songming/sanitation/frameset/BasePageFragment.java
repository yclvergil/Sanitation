package com.songming.sanitation.frameset;

import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.BuildConfig;

public abstract class BasePageFragment extends BaseFragment {

	private static final String TAG = BasePageFragment.class.getSimpleName();
	
	// 总记录数
		protected int rowCount;
		// 每页条数
		protected int pageSize = 12;
		// 当前页号
		protected int pageIndex = 1;
		// 总页数
		private int pageCount = 1;
		public int getPageCount() {
			if (pageCount == 1)
				statisticsPageCount();
			return pageCount;
		}

		
		
		/**
		 * 绑定控件
		 * 
		 */
		protected abstract void findViews();

		/** 计算页数 */
		private void statisticsPageCount() {
			int tempVar = rowCount % pageSize;
			if (tempVar == 0) {
				pageCount = rowCount / pageSize;
			} else {
				pageCount = rowCount / pageSize + 1;
			}
		}
		
		

		@Override
		public void onResponse(JSONObject jsonObject, Object requestTag) {
			
			try {
				
				    if(BuildConfig.DEBUG)
				       Log.i(TAG, jsonObject.toString());
				    if (jsonObject.optInt("flag", 1)>0) {
				    	Toast.makeText(getActivity(), jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
				    	hideLoading();
				    	return  ;
					}
				    
				 // 总记录数
					if (jsonObject.optInt("rowCount", -1) != -1) {
						rowCount = jsonObject.optInt("rowCount");
						statisticsPageCount();
					}
					if (jsonObject.optInt("totalPage", -1) != -1) {
						pageCount = jsonObject.optInt("totalPage");
					}
					
				    if(requestTag instanceof VolleyRequestVo){
				    	VolleyRequestVo vo=(VolleyRequestVo)requestTag;
				    	if(vo.isCache()){
						    String requestUrl=vo.getRequestUrl();
						    //加缓存
						    //VolleyQueue.getStrLruCache().putString(requestUrl, DES3.encode(jsonObject.toString()));
						    VolleyQueue.getStrLruCache().putString(requestUrl, jsonObject.toString());
				    	}
					}
				    
				    successCallback(jsonObject,requestTag);
				    
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Toast.makeText(getActivity(), "json解析出错："+e.getMessage(), Toast.LENGTH_SHORT).show();
				} 
		 }
		
}
