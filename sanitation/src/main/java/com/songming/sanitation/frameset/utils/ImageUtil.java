package com.songming.sanitation.frameset.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.View;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class ImageUtil {
	/*
	 * 得到file名
	 */
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/*
	 * 调用系统裁剪
	 */

	public static void startPhotoZoom(Activity context, Uri uri, int Result) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		context.startActivityForResult(intent, Result);
	}

	/*
	 * 调用系统相册 并裁剪
	 */
	public static Intent getImageClipIntent() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 100);// 输出图片大小
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		return intent;
	}

	/**
	 * 以最省内存的方式读取本地资源的图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {

		BitmapFactory.Options opt = new BitmapFactory.Options();
		// BitmapFactory.decodeStream(is, null, opt);
		opt.inJustDecodeBounds = true;
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		//
		// int width = CommonUtil.getScreenWidth(context);
		// int i = opt.outWidth / width;
		// if (i <= 0) {
		// i = 1;
		// }
		// opt.inSampleSize = i;
		opt.inInputShareable = true;

		// opt.inSampleSize = 2;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		opt.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(is, null, opt);
	}

	// 判断图片url是否带有images 没有加上
	public static String isAddImagesUrl(String url) {
		if (url == null) {
			return "null";
		}
		if (!url.contains("images/")) {
			url = "/images/" + url;
		}
		return url;
	}

	/*
	 * image loader 节约内存 DisplayImageOptions 设置
	 */
	public static DisplayImageOptions getDisplayImageOptions() {

		return new DisplayImageOptions.Builder()
		// 设置下载的图片是否缓存在内存中
				.cacheInMemory(false)
				// 外包缓存
				.cacheOnDisk(true)
				// 保留Exif信息
				.considerExifParams(true)
				// 设置图片以如何的编码方式显示
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				// 设置图片的解码类型
				.bitmapConfig(Bitmap.Config.RGB_565)
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				.considerExifParams(true)
				// 设置图片下载前的延迟
				.delayBeforeLoading(100)// int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.build();
	}

	/*
	 * image loader 节约内存 ImageLoaderConfiguration 设置
	 */
	public static ImageLoaderConfiguration getImageLoaderConfiguration(
			Context context) {
		return new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.memoryCache(new WeakMemoryCache()).writeDebugLogs().build();
	}

	/*
	 * 布局 设置背景图片
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void setBackground(View view, int id, Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			view.setBackground(context.getResources().getDrawable(id));
		} else {
			view.setBackgroundDrawable(context.getResources().getDrawable(id));
		}
	}
	
	/**
	 * @param urlpath
	 * @return Bitmap
	 * 根据图片url获取图片对象
	 */
	public static Bitmap getBitmap(String urlpath) {
		Bitmap map = null;
		try {
			URL url = new URL(urlpath);
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream in;
			in = conn.getInputStream();
			map = BitmapFactory.decodeStream(in);
			// TODO Auto-generated catch block
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

}
