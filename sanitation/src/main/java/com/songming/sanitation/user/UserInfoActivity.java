package com.songming.sanitation.user;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.ApplicationExt;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.HttpRequestTool;
import com.songming.sanitation.frameset.utils.ImageUtil;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.user.model.UserDto;

public class UserInfoActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;
	private LinearLayout head_layout;// 修改头像
	private ImageView user_image;
	private TextView name;
	private TextView age;
	private TextView worktime;
	private TextView phone;
	private TextView companyname;
	private TextView department;
	private String sextext;
	private Button save;
	private RadioGroup group;
	private RadioButton radioMale;
	private RadioButton radioFemale;
	private String headUrl;
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			ImageUtil.getPhotoFileName());
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private List<Long> fileIds = new ArrayList<Long>();
	private static final String TAG = "UserInfoActivity";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.userdataactivity);

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.user_topimage)
				.showImageForEmptyUri(R.drawable.user_topimage)
				.showImageOnFail(R.drawable.user_topimage).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(60)).build();// 设置圆角
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));

		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		head_layout = (LinearLayout) findViewById(R.id.head_layout);
		user_image = (ImageView) findViewById(R.id.user_image);
		name = (TextView) findViewById(R.id.name);
		age = (TextView) findViewById(R.id.age);
		worktime = (TextView) findViewById(R.id.worktime);
		phone = (TextView) findViewById(R.id.phone);
		companyname = (TextView) findViewById(R.id.companyname);
		department = (TextView) findViewById(R.id.department);
		save = (Button) findViewById(R.id.save);
		radioMale = (RadioButton) findViewById(R.id.radioMale);
		radioFemale = (RadioButton) findViewById(R.id.radioFemale);

		group = (RadioGroup) findViewById(R.id.radioGroup);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// 获取变更后的选中项的ID
				int radioButtonId = arg0.getCheckedRadioButtonId();
				// 根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton) findViewById(radioButtonId);
				sextext = rb.getText().toString();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void initViews() {

		topTitle.setText("个人资料");
		layoutback.setVisibility(View.VISIBLE);
		setUserInfo();
		layoutbackimg.setOnClickListener(this);
		head_layout.setOnClickListener(this);
		save.setOnClickListener(this);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if (TAG.equals(requestStr)) {
			Toast.makeText(UserInfoActivity.this, "操作失败", Toast.LENGTH_SHORT)
					.show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i(TAG, volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		// 修改用户信息
		if (TAG.equals(requestStr)) {
			String msg = jsonObject.optString("msg", "修改成功");
			boolean flag = jsonObject.optBoolean("flag");
			if (flag) {
				// 设置头像
				imageLoader.displayImage(Constants.IMAGE_URL + headUrl,
						user_image, options);
				SharedPreferencesUtils.setStringValue(this,
						SharedPreferencesUtils.USERHEAD, headUrl);
				ApplicationExt.nowUser.setPhotoUrl(headUrl);
				Toast.makeText(UserInfoActivity.this, "修改头像成功！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(UserInfoActivity.this, msg, Toast.LENGTH_SHORT)
						.show();

			}
		}
	}

	private void setUserInfo() {

		UserDto user = ApplicationExt.nowUser;
		headUrl = user.getPhotoUrl();
		imageLoader.displayImage(Constants.IMAGE_URL + headUrl, user_image, options);

		name.setText(user.getName());
		age.setText(user.getAge()+"");
		phone.setText(user.getPhone());
		companyname.setText(user.getOrgName());
		if(user.getGender() == 1){
			radioMale.setChecked(true);
		}else{
			radioFemale.setChecked(true);
		}
	}

	/**
	 * 修改用户头像 param={"staffId":1,"fileKeys":[{"id":1}]}
	 */
	private void updateUserInfo() {
		long id = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("staffId", id);
			List<JSONObject> jsons = new ArrayList<JSONObject>();
			for (int i = 0; i < fileIds.size(); i++) {
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("id", fileIds.get(i));
				jsons.add(jsonObject2);
			}
			jsonObject.put("fileKeys", jsons.toString());
			requestHttp(jsonObject, TAG, Constants.UPDATEHEAD,
					Constants.SERVER_URL);

		} catch (JSONException e) {
			Toast.makeText(UserInfoActivity.this, "json参数出错:",
					Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(UserInfoActivity.this, "请重新启动", Toast.LENGTH_SHORT)
					.show();
		}

	}

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候
		if (resultCode != this.RESULT_CANCELED) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (hasSdcard()) {
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(UserInfoActivity.this, "未找到存储卡，无法存储照片！",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					setImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				hideLoading();
				Toast.makeText(UserInfoActivity.this, "上传头像失败！",
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				// 更新用户资料
				updateUserInfo();
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 头像选择对话框
	 */
	private void showDialog() {

		final Dialog dialog = new Dialog(this, R.style.dialog);
		dialog.setContentView(R.layout.takeuserphoto);
		ImageView cancle = (ImageView) dialog.findViewById(R.id.cancle);
		LinearLayout takephone = (LinearLayout) dialog
				.findViewById(R.id.takephoto);
		LinearLayout photoshop = (LinearLayout) dialog
				.findViewById(R.id.photoshop);
		// 取消
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}

		});
		// 拍照
		takephone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentFromCapture = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				// 判断存储卡是否可以用，可用进行存储
				if (hasSdcard()) {
					intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(tempFile));
				}
				startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
				dialog.dismiss();
			}
		});
		// 相册选择
		photoshop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentFromGallery = new Intent();
				intentFromGallery.setType("image/*"); // 设置文件类型
				intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
				dialog.dismiss();
			}
		});
		dialog.show();

	}

	/**
	 * 检查设备是否存在SDCard的工具方法
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// 有存储的SDCard
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i(TAG, "The uri is not exist.");
		}
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setImageToView(Intent data) {
		this.showLoading("头像上传中...", TAG);
		final Bundle extras = data.getExtras();
		if (extras != null) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					Bitmap bitmap = extras.getParcelable("data");
					try {
						saveFile(bitmap);
						bitmap.recycle();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	/**
	 * 保存图片
	 * 
	 * @param bm
	 * @param fileName
	 * @param fileUrl
	 * @throws IOException
	 */
	private void saveFile(Bitmap bm) throws IOException {
		String filename = ImageUtil.getPhotoFileName();
		Map<String, String> mapParams = new HashMap<String, String>();
		Map<String, File> mapFiles = new HashMap<String, File>();
		File myCaptureFile = new File(this.getFilesDir() + "/" + filename);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		Log.i("test", "bm = " + bm + "\n" + "bos = " + bos);
		bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
		bos.flush();
		bos.close();
		mapFiles.put(filename, myCaptureFile);

		try {
			JSONObject json = new JSONObject();
			json.put("fileType", "6"); // 文件类型：7-上传头像
			String url = Constants.SERVER_URL + Constants.UPLOADFILE
					+ "?param=" + json.toString();
			String data = HttpRequestTool.post(url, mapParams, mapFiles);
			Log.d("aa", data);
			JSONObject object = new JSONObject(data);
			boolean flag = object.optBoolean("flag");
			if (flag) {
				String datastr = object.optString("data");
				JSONObject object2 = new JSONObject(datastr);
				long fileId = object2.optLong("id");
				fileIds.add(fileId);
				headUrl = object2.optString("filePath");
				handler.sendEmptyMessage(1);
			} else {
				handler.sendEmptyMessage(0);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(0);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.head_layout:
			// 点击弹出选择拍照或者上传照片
			showDialog();
			break;

		case R.id.save:
			// 保存
//			updateUserInfo();
			this.finish();
			break;
		case R.id.layoutbackimg:
			this.finish();

			break;
		default:
			break;
		}
	}

}
