package com.songming.sanitation.frameset;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.songming.sanitation.frameset.cache.BitmapLruCache;
import com.songming.sanitation.frameset.cache.DiskLruImageCache;
import com.songming.sanitation.frameset.cache.DiskLruStringCache;
import com.songming.sanitation.frameset.cache.StringLruCache;
import com.songming.sanitation.frameset.utils.SDcardUtils;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;

/**
 * 描述：volley的静态工具方法类
 * @since 1.0
 * */
public class VolleyQueue {

	private static RequestQueue mRequestQueue;
	
	private static ImageLoader mImageLoader;
	
	private static DiskLruImageCache diskImageCache;
	
	private static StringLruCache strLruCache;
	
	private static DiskLruStringCache diskStringCache;
	
	private static final String DEFAULT_CACHE_DIR = "volley";
	
	private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 10;//10M
	
	private static int DISK_STRINGCACHE_SIZE = 1024 * 1024 * 10;//10M
	
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	
	private static int DISK_IMAGECACHE_QUALITY = 100; 
	
	// PNG is lossless so
	// quality is ignored
	// but must be provided
	private VolleyQueue() {
	}

	/**
	 * 初始化我们的请求队列。这个地方有一个BitmapLruCache，这个是在后面做图片加载的时候会提到的图片缓存策略
	 * 
	 * @param context
	 */
	public static void init(Context context) {
		
		mRequestQueue = Volley.newRequestQueue(context);

		int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		// Use 1/8th of the available memory for this memory cache.
		int cacheSize = 1024 * 1024 * memClass / 8;
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(cacheSize));
		
		int cfgSize=SharedPreferencesUtils.getIntValue(context,SharedPreferencesUtils.CACHE_SIZE, 30);
		
		strLruCache=new StringLruCache(cacheSize);
		
		long diskcacheSize=DISK_IMAGECACHE_SIZE;
		if(SDcardUtils.existSDCard()){
			//存在SD卡就获取可用空间大小
			long availableSize=SDcardUtils.getAvailableExternalMemorySize();
			
			if(availableSize!=-1 && availableSize/(1024 * 1024)< DISK_IMAGECACHE_SIZE*3)
				diskcacheSize=(long)availableSize/8;
		}else{
			//不存在，从内部存储卡
			long availableSize=SDcardUtils.getAvailableInternalMemorySize();
			
			if(availableSize!=-1 && availableSize/(1024 * 1024)< DISK_IMAGECACHE_SIZE*3)
				diskcacheSize=(long)availableSize/8;
		}
		
		if(diskcacheSize==0)
			diskcacheSize=1024*1024*3;
		else if(diskcacheSize>(cfgSize*1024*1024/3))
			diskcacheSize=cfgSize*1024*1024/3;
		else
		   SharedPreferencesUtils.setLongValue(context,SharedPreferencesUtils.CACHE_SIZE_VOLLEY, (diskcacheSize*2));
		
		diskStringCache= new DiskLruStringCache(context,context.getApplicationContext().getPackageCodePath()+"str",
				diskcacheSize);
		
		diskImageCache = new DiskLruImageCache(context,context.getApplicationContext().getPackageCodePath()+"img",
				diskcacheSize, DISK_IMAGECACHE_COMPRESS_FORMAT,DISK_IMAGECACHE_QUALITY);
	}

	public static DiskLruImageCache getDiskImageCache() {
		if (diskImageCache != null) {
			return diskImageCache;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}
	
	public static DiskLruStringCache getDiskStringCache() {
		if (diskStringCache != null) {
			return diskStringCache;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}
	
	public static StringLruCache getStrLruCache() {
		if (strLruCache != null) {
			return strLruCache;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	public static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}

	public static void cancelAllQueue() {

		getRequestQueue().cancelAll(new RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});
	}

	public static void cancelQueue(final Object tag) {

		getRequestQueue().cancelAll(new RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				String requestStr="";
				if(tag instanceof VolleyRequestVo && request.getTag() instanceof VolleyRequestVo){
					requestStr=((VolleyRequestVo)tag).getRequestTag();
					String str=((VolleyRequestVo)request.getTag()).getRequestTag();
					return requestStr.equalsIgnoreCase(str);
				}
				return request.getTag()==tag;
			}
		});
	}

}
