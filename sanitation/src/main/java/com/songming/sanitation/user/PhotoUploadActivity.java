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

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.ext.GridViewExt;
import com.songming.sanitation.frameset.utils.Bimp;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.HttpRequestTool;
import com.songming.sanitation.frameset.utils.ImageUtil;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.map.tool.BdMapLocationUtils;
import com.songming.sanitation.map.tool.BdMapLocationUtils.BdLocationSuccessListenner;
import com.songming.sanitation.user.adapter.PhotoUploadAdapter;

public class PhotoUploadActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private EditText photoctx;// 描述
	private TextView photoplace;// 地址
	private Button photocommit;// 上传

	private GridViewExt gridview;// 图片

	private List<Bitmap> picture = new ArrayList<Bitmap>();
	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			ImageUtil.getPhotoFileName());// 文件存储
	private Bitmap bmp;
	private List<Long> fileIds = new ArrayList<Long>();

	private static final int SHOWPICTURE = 1000;
	private static final int DELETEPICTURE = 99;
	private PhotoUploadAdapter adapter;// gridview适配器
	private static final String TAG = "PhotoUploadAcitivity";
	private static final int UPLOAD = 888;
	private static final int UPLOAD_FAIL = 444;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.photoupload);

		findViews();
		initViews();
		getLocation();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		photoctx = (EditText) findViewById(R.id.photoctx);
		photoplace = (TextView) findViewById(R.id.photoplace);
		photocommit = (Button) findViewById(R.id.photocommit);
		gridview = (GridViewExt) findViewById(R.id.gridview);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("照片上传");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		photocommit.setOnClickListener(this);

		bmp = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.photoupload02);
		picture.add(bmp);

		adapter = new PhotoUploadAdapter(PhotoUploadActivity.this, picture,
				handler);
		// 为GridView设置适配器
		gridview.setAdapter(adapter);
		// 注册监听事件
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				if (position == (picture.size() - 1)) {
					if (picture.size() <= 3) {
						Intent imageCaptureIntent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(tempFile));
						startActivityForResult(imageCaptureIntent, 102);
					} else {
						Toast.makeText(PhotoUploadActivity.this,
								"上传的图片不能超过三张！", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		this.hideLoading();
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("photoupload".equals(requestStr)) {

			String flag = jsonObject.optString("flag");
			if (flag.equals("true")) {
				Toast.makeText(PhotoUploadActivity.this, "提交成功！",
						Toast.LENGTH_SHORT).show();
				this.finish();
			} else {
				Toast.makeText(PhotoUploadActivity.this, "提交失败！",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 102:
				try {
					Bitmap bm = Bimp.revitionImageSize(tempFile.getPath());
					picture.add(bm);
					handler.sendEmptyMessage(SHOWPICTURE);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}

	}

	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
			case SHOWPICTURE:
				adapter.setPicture(picture);
				adapter.notifyDataSetChanged();
				break;
			case DELETEPICTURE:
				int position = (Integer) msg.obj;
				picture.remove(position);
				adapter.setPicture(picture);
				adapter.notifyDataSetChanged();
				break;
			case UPLOAD:

				requestData();
				break;
			case UPLOAD_FAIL:
				PhotoUploadActivity.this.hideLoading();
				Toast.makeText(PhotoUploadActivity.this, "上传图片失败！",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 获取当前位置
	 * */
	private void getLocation() {
		// 获取客户端当前的位置 坐标
		BdMapLocationUtils.getInstance().startLocation(
				new BdLocationSuccessListenner() {

					@Override
					public void locationResult(double _latitude,
							double _longitude, String _city,
							String _locationAddr, String _locationType) {
						photoplace.setText(_locationAddr);

					}
				});
	}

	/**
	 * 保存图片
	 * 
	 * @param bm
	 * @param fileName
	 * @param fileUrl
	 * @throws IOException
	 */
	private void saveFile() throws IOException {

		for (int i = 1; i < picture.size(); i++) {

			String fileName = ImageUtil.getPhotoFileName();
			File myCaptureFile = new File(getApplicationContext().getFilesDir()
					+ "/" + fileName);
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(myCaptureFile));
			Bitmap bm = picture.get(i);
			bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
			bos.flush();
			bos.close();

			Map<String, String> mapParams = new HashMap<String, String>();
			Map<String, File> mapFiles = new HashMap<String, File>();
			mapFiles.put(fileName, myCaptureFile);

			try {
				JSONObject json = new JSONObject();
				json.put("fileType", "2");
				String url = Constants.SERVER_URL + Constants.UPLOADFILE
						+ "?param=" + json.toString();
				String data = HttpRequestTool.post(url, mapParams, mapFiles);

				JSONObject object = new JSONObject(data);
				boolean flag = object.optBoolean("flag");
				if (flag) {

					String datastr = object.optString("data");
					JSONObject object2 = new JSONObject(datastr);
					long fileId = object2.optLong("id");
					fileIds.add(fileId);
					if (i == picture.size() - 1) {
						handler.sendEmptyMessage(UPLOAD);
					}

				} else {
					handler.sendEmptyMessage(UPLOAD_FAIL);
				}
			} catch (Exception e) {
				handler.sendEmptyMessage(UPLOAD_FAIL);
			}
		}
	}

	/**
	 * 描述：巡查情况上传
	 * */
	private void requestData() {

		long staffId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		int orgId = SharedPreferencesUtils.getIntValue(this,
				SharedPreferencesUtils.ORG_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("content", photoctx.getText().toString());
			jsonObject.put("createId", staffId);
			jsonObject.put("createOrgId", orgId);
			jsonObject.put("address", photoplace.getText().toString());

			List<JSONObject> jsons = new ArrayList<JSONObject>();
			for (int i = 0; i < fileIds.size(); i++) {
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("id", fileIds.get(i));
				jsons.add(jsonObject2);
			}
			if (fileIds.size() > 0) {
				jsonObject.put("fileKeys", jsons.toString());
			}

			requestHttp(jsonObject, "photoupload", Constants.PHOTOUPLOAD,
					Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.layoutbackimg:
			this.finish();

			break;

		case R.id.photocommit:
			// 上传
			if (StringUtilsExt.isEmpty(photoctx.getText().toString())) {
				Toast.makeText(this, "您还未填写照片描述！", Toast.LENGTH_SHORT).show();
				return;
			}
			if (picture.size() < 2) {
				Toast.makeText(this, "请拍摄照片后再试！", Toast.LENGTH_SHORT).show();
				return;
			}
			
			this.showLoading("上传中，请稍后...", TAG);
			// 开启一个新线程去处理上传图片等耗时操作
			new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						saveFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

			break;
		default:
			break;
		}

	}
}
