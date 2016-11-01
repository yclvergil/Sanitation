package com.songming.sanitation.workdeal;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.ext.ListViewExt;
import com.songming.sanitation.frameset.utils.Bimp;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.HttpRequestTool;
import com.songming.sanitation.frameset.utils.ImageUtil;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.sign.SignActivity;
import com.songming.sanitation.workdeal.adapter.DetailImageAdapter;
import com.songming.sanitation.workdeal.adapter.WorkDealDoingDetailAdapter;
import com.songming.sanitation.workdeal.bean.ImageDto;
import com.songming.sanitation.workdeal.bean.TEventDetailDto;
import com.songming.sanitation.workdeal.bean.TEventDto;

/**
 * 正在处理界面
 * 
 * @author Administrator
 * 
 */
public class SisposeActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private Button sispose_accomplish;// 完成按钮 ，区域经理总经理需隐藏
	private Button sispose_dispose;// 再次指派

	private TextView sispose_title;// 标题
	private TextView sisoise_time;// 日期
	private TextView sispose_site;// 地址

	private TextView creatname;// 发起人
	private ImageView phone;// 发起人电话

	private long stationId;

	private TEventDto detail;
	private List<TEventDetailDto> detailList;
	private long id = -1;
	private long staffid;

	private ListViewExt listviewext;
	private WorkDealDoingDetailAdapter adapter;
	private int whatsup;
	private TextView statusname;
	private LinearLayout workdealtype;
	private long executeid;
	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			ImageUtil.getPhotoFileName());// 文件存储
	private List<Bitmap> picture = new ArrayList<Bitmap>();
	private static final int UPLOAD_SUCCESS = 555;
	private static final int UPLOAD_FAIL = 444;
	private List<Long> fileIds = new ArrayList<Long>();

	private List<ImageDto> files = new ArrayList<ImageDto>();// 账片放大
	private List<ImageDto> dipicture;// 照片
	private GridView gridview;
	private DetailImageAdapter diadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sispose);
		id = getIntent().getLongExtra("id", -1);
		stationId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STATIONID, -1);
		staffid = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);
		whatsup = getIntent().getIntExtra("whatsup", -1);
		executeid = getIntent().getLongExtra("executeid", -1);
		// 设置消息事件已阅读
		sendRead();

		findViews();
		initViews();
		requestData();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		// 完成按钮
		sispose_accomplish = (Button) findViewById(R.id.sispose_accomplish);
		// 再次指派
		sispose_dispose = (Button) findViewById(R.id.sispose_dispose);
		listviewext = (ListViewExt) findViewById(R.id.listviewext);
		workdealtype = (LinearLayout) findViewById(R.id.workdealtype);
		creatname = (TextView) findViewById(R.id.creatname);
		phone = (ImageView) findViewById(R.id.phone);

		statusname = (TextView) findViewById(R.id.statusname);// 进度条描述文字
		sispose_title = (TextView) findViewById(R.id.sispose_title);// 标题
		sisoise_time = (TextView) findViewById(R.id.sisoise_time);// 日期
		sispose_site = (TextView) findViewById(R.id.sispose_site);// 地址

		gridview = (GridView) findViewById(R.id.gridview);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		if (whatsup == 888) {
			topTitle.setText("查看事件状态");
			statusname.setText("事件状态");
			// workdealtype.setVisibility(View.GONE);
		} else {
			topTitle.setText("工单详情");
			statusname.setText("处理进度");
		}
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		sispose_accomplish.setOnClickListener(this);
		// sispose_dispose.setOnClickListener(this);
		adapter = new WorkDealDoingDetailAdapter(this, detailList, stationId,
				whatsup);

		// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
		// if (stationId == 2 || stationId == 3 || stationId == 5
		// || whatsup == 888) {
		// sispose_dispose.setVisibility(View.GONE);
		// sispose_accomplish.setVisibility(View.GONE);
		// }

		// if (executeid == staffid) {
		// sispose_accomplish.setVisibility(View.VISIBLE);
		// }

		listviewext.setAdapter(adapter);

		gridview.setVisibility(View.VISIBLE);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SisposeActivity.this,
						CheckUpPictureActivity.class);
				files.addAll(dipicture);

				intent.putExtra("files", (Serializable) files);
				intent.putExtra("index", position);
				startActivity(intent);

			}
		});

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		files.clear();
		super.onResume();
	}

	/**
	 * 描述： 设置消息事件已阅读
	 * 
	 * */
	private void sendRead() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("eventId", id);
			jsonObject.put("executeId", staffid);

			requestHttp(jsonObject, "sendRead", Constants.MESSAGE_READ,
					Constants.SERVER_URL);

		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 描述： 申请当页数据
	 * 
	 * */
	private void requestData() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", id);
			jsonObject.put("executeId", staffid);
			showLoading("数据加载中，请稍候...", "worklistdetail");
			requestHttp(jsonObject, "worklistdetail", Constants.WORKLISTDETAIL,
					Constants.SERVER_URL);

		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 描述： 完成任务
	 * 
	 * */
	private void submitData() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", id);
			jsonObject.put("executeId", staffid);

			List<JSONObject> jsons = new ArrayList<JSONObject>();
			for (int i = 0; i < fileIds.size(); i++) {
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("id", fileIds.get(i));
				jsons.add(jsonObject2);
			}
			if (fileIds.size() > 0) {
				jsonObject.put("fileKeys", jsons.toString());
			}
			showLoading("提交中，请稍候...", "finishwork");
			requestHttp(jsonObject, "finishwork", Constants.WORKCOMPLETE,
					Constants.SERVER_URL);

		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 签到照相对话框
	 */
	private void showDialog() {

		final Dialog dialog = new Dialog(SisposeActivity.this, R.style.dialog);
		dialog.setContentView(R.layout.cameradialog);
		ImageView cancle = (ImageView) dialog.findViewById(R.id.cancle);
		LinearLayout takephone = (LinearLayout) dialog
				.findViewById(R.id.takephoto);
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
				intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(tempFile));
				startActivityForResult(intentFromCapture, 102);
				dialog.dismiss();
			}
		});
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 102:
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Bitmap bm = Bimp.revitionImageSize(tempFile
									.getPath());
							picture.add(bm);
							saveFile(bm);
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

	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
			case UPLOAD_SUCCESS:
				// 完成
				submitData();
				break;
			case UPLOAD_FAIL:
				// 上传照片失败
				Toast.makeText(SisposeActivity.this, "上传图片失败！",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};

	private void saveFile(Bitmap bmp) throws IOException {
		for (int i = 0; i < picture.size(); i++) {
			String fileName = ImageUtil.getPhotoFileName();
			File myCaptureFile = new File(getApplicationContext().getFilesDir()
					+ "/" + fileName);
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(myCaptureFile));
			Bitmap bm = picture.get(i);
			bmp.compress(Bitmap.CompressFormat.PNG, 80, bos);
			bos.flush();
			bos.close();

			Map<String, String> mapParams = new HashMap<String, String>();
			Map<String, File> mapFiles = new HashMap<String, File>();
			mapFiles.put(fileName, myCaptureFile);

			try {
				JSONObject json = new JSONObject();
				json.put("fileType", "5");
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
						// 提交
						handler.sendEmptyMessage(UPLOAD_SUCCESS);
					}
				} else {
					handler.sendEmptyMessage(UPLOAD_FAIL);
				}
			} catch (Exception e) {
				handler.sendEmptyMessage(UPLOAD_FAIL);
			}
		}
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("finishwork".equals(requestStr)) {
			Toast.makeText(this, "数据提交失败", Toast.LENGTH_SHORT).show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("finishwork", volleyError.getMessage());

	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub

		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("worklistdetail".equals(requestStr)) {

			Gson gson = new Gson();
			detail = gson.fromJson(jsonObject.optString("data", "{}"),
					TEventDto.class);

			String flag = jsonObject.optString("flag", "");
			String msg = jsonObject.optString("msg", "获取数据成功");

			if (flag.equals("true")) {

				sispose_title.setText(detail.getEventTitle());// 内容标题
				sisoise_time.setText(detail.getEventDate());// 时间
				sispose_site.setText(detail.getAddress());// 事发地址

				creatname.setText(detail.getCreateName());
				phone.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it = new Intent(Intent.ACTION_CALL, Uri
								.parse("tel:" + detail.getPhone()));
						startActivity(it);

					}
				});
				detailList = detail.getDetails();

				adapter.setData(detailList);
				adapter.notifyDataSetChanged();

				if ("1".equals(detail.getIsExecute()) || whatsup == 888) {
					sispose_accomplish.setVisibility(View.GONE);
				} else if ("0".equals(detail.getIsExecute()) && whatsup != 888) {
					sispose_accomplish.setVisibility(View.VISIBLE);
				}

			} else {
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

			}

			dipicture = detail.getFiles();
			diadapter = new DetailImageAdapter(this, detail.getFiles());
			gridview.setAdapter(diadapter);
		}

		if ("finishwork".equals(requestStr)) {

			Gson gson = new Gson();
			detail = gson.fromJson(jsonObject.optString("data", "{}"),
					TEventDto.class);

			String flag = jsonObject.optString("flag", "");

			if (flag.equals("true")) {

				Toast.makeText(this, "已完成", Toast.LENGTH_SHORT).show();
				this.finish();

			} else {
				Toast.makeText(this, "提交失败", Toast.LENGTH_SHORT).show();

			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layoutbackimg:
			this.finish();

			break;
		case R.id.sispose_accomplish:
			// 完成按钮
			// submitData();
			showDialog();
			break;

		// case R.id.sispose_dispose:
		// // 指派按键点击事件;
		// Intent intent = new Intent(SisposeActivity.this,
		// DesignateActivity.class);
		// intent.putExtra("taskId", id);
		// startActivity(intent);
		// this.finish();
		// break;
		default:
			break;
		}
	}
}
