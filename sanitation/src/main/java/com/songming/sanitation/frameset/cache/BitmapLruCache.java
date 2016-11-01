package com.songming.sanitation.frameset.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.songming.sanitation.frameset.VolleyQueue;

/**
 * 描述：android中图片内存缓�?
 * 
 * */
public class BitmapLruCache extends LruCache<String, Bitmap> implements
		ImageCache {

	public BitmapLruCache() {
		this(getDefaultLruCacheSize());
	}

	public BitmapLruCache(int sizeInKiloBytes) {
		super(sizeInKiloBytes);
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight() / 1024;
	}

	@Override
	public Bitmap getBitmap(String url) {
		Bitmap data = get(url);
		if (data == null) {
			data = VolleyQueue.getDiskImageCache().getBitmap(String.valueOf(url.hashCode()));
			if (data != null) {
				put(url, data);
			}

		}

		return data;
		// return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url, bitmap);
		VolleyQueue.getDiskImageCache().putBitmap(String.valueOf(url.hashCode()), bitmap);
	}

	public static int getDefaultLruCacheSize() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;

		return cacheSize;
	}

}
