package com.songming.sanitation.frameset.cache;

import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.BuildConfig;
import com.songming.sanitation.frameset.VolleyQueue;

public class StringLruCache extends LruCache<String, String> implements StringCache{
     private static final String TAG=StringLruCache.class.getSimpleName();
	public StringLruCache() {
		this(getDefaultLruCacheSize());
	}

	public StringLruCache(int sizeInKiloBytes) {
		super(sizeInKiloBytes);
	}

	@Override
	protected int sizeOf(String key,String value) {
		return value.length()*2+38;
	}

	

	

	public static int getDefaultLruCacheSize() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;

		return cacheSize;
	}

	@Override
	public String getString(String url) {
		
		String value=get(url);
		if(value==null){
			value=VolleyQueue.getDiskStringCache().getString(String.valueOf(url.hashCode()));
			if(value!=null){
				put(url,value);
			}
		}
		 if(BuildConfig.DEBUG)
			    Log.i(TAG, "StringLruCache().getString\n"+value);
		return value;
	}

	@Override
	public void putString(String url, String value) {
		this.put(url, value);
		if(BuildConfig.DEBUG)
		    Log.i(TAG, "StringLruCache.putString\n"+url+value);
		VolleyQueue.getDiskStringCache().putString(String.valueOf(url.hashCode()), value);
	}
	
	

}
