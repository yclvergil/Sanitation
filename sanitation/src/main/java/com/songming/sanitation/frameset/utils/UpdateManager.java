package com.songming.sanitation.frameset.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.songming.sanitation.R;

/**
 * app版本更新
 */

public class UpdateManager {
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 保存解析的XML信息 */
	HashMap<String, String> mHashMap;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;
	private TextView updateProgress;// 下载进度

	/* 下载路径 */
	private String mDownloadPath;
	private String apkFilename;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				updateProgress.setText("正在下载新版本" + progress + "%");
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context, String mDownloadPath) {
		this.mContext = context;
		this.mDownloadPath = mDownloadPath;
	}

	/**
	 * 检测软件更新
	 */
	public void checkUpdate() {
		// if (isUpdate()) {
		// 显示提示对话框
		// showNoticeDialog();
		// }
		/*
		 * else { Toast.makeText(mContext, R.string.soft_update_no,
		 * Toast.LENGTH_LONG).show(); }
		 */
	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	private boolean isUpdate() {
		// 获取当前软件版本
		int versionCode = getVersionCode(mContext);
		// 把version.xml放到网络上，然后获取文件信息
		InputStream inStream = ParseXmlService.class.getClassLoader()
				.getResourceAsStream("version.xml");
		// 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
		ParseXmlService service = new ParseXmlService();
		try {
			mHashMap = service.parseXml(inStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != mHashMap) {
			int serviceCode = Integer.valueOf(mHashMap.get("version"));
			// 版本判断
			if (serviceCode > versionCode) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 显示软件更新对话框
	 */
	public void showNoticeDialog(String content) {
		// 构造对话框
		final Dialog dialog = new Dialog(mContext, R.style.dialog);
		dialog.setContentView(R.layout.update_dialog);
		TextView content_text = (TextView) dialog
				.findViewById(R.id.update_content);
		TextView update_later = (TextView) dialog
				.findViewById(R.id.update_later);
		TextView update = (TextView) dialog.findViewById(R.id.update);
		content_text.setText(content);
		// 稍后更新
		update_later.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		// 立即更新
		update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				// 显示下载对话框
				showDownloadDialog();
			}
		});
		dialog.show();
	}

	/**
	 * 显示软件下载对话框
	 */
	public void showDownloadDialog() {
		// 构造软件下载对话框
		mDownloadDialog = new Dialog(mContext, R.style.dialog);
		mDownloadDialog.setContentView(R.layout.update_loading);
		TextView update_cancle = (TextView) mDownloadDialog
				.findViewById(R.id.update_cancle);
		mProgress = (ProgressBar) mDownloadDialog
				.findViewById(R.id.update_progress);
		updateProgress = (TextView) mDownloadDialog.findViewById(R.id.text1);
		// 取消
		update_cancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mDownloadDialog.dismiss();
				// 设置取消状态
				cancelUpdate = true;
			}
		});
		// 下载
		downloadApk();
		//设置点击外部区域不可取消
		mDownloadDialog.setCanceledOnTouchOutside(false);
		mDownloadDialog.show();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 * 
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";

					String downloadPath = "";
					if (mDownloadPath != null) {
						downloadPath = mDownloadPath;
					} else {
						downloadPath = mHashMap.get("url");
					}

					URL url = new URL(downloadPath);
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					apkFilename = "";
					if (mDownloadPath != null) {
						String[] l = mDownloadPath.split("/");
						if (l.length > 0) {
							apkFilename = l[l.length - 1];
						} else {
							apkFilename = "updata.apk";
						}

					} else {
						apkFilename = mHashMap.get("name");
					}
					File apkFile = new File(mSavePath, apkFilename);
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = null;
		if (apkFilename == null) {
			apkfile = new File(mSavePath, mHashMap.get("name"));
		} else {
			apkfile = new File(mSavePath, apkFilename);
		}

		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
