package com.songming.sanitation.user;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.songming.sanitation.user.adapter.UpKeepAdapter;
import com.songming.sanitation.user.adapter.UpKeepCarAdapter;
import com.songming.sanitation.user.adapter.UpKeepItemsAdapter;
import com.songming.sanitation.user.adapter.UpKeepTypeAdapter;
import com.songming.sanitation.user.model.TCarDto;

/**
 * 车辆保养
 * 
 * @author Administrator
 * 
 */
public class UpKeepActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;
	
	private View PopView = null;
	private PopupWindow popupwindow;// 下拉框显示列表
	private ListView popup_listview;// popupwindow里面展示信息的listview

	private TextView upkeep_time;// 保养日期
	private EditText upkeep_cause;// 维修原因
	private EditText upkeep_kilometre;// 公里数
	private EditText upkeep_money;// 维修费用
	private EditText upkeep_person;// 经办人
	private TextView upkeep_next;// 下次保养时间
	private EditText upkeep_remarks;// 备注

	private Button upkeep_submit;// 提交按键

	private TextView upkeep_car;// 所属车辆下拉按键
	private TextView upkeep_type;// 保养类型下拉按键
	private TextView upkeep_items;// 维修项目下拉按键

	private GridViewExt gridview;// 图片
	private UpKeepAdapter adapter;// gridview适配器
	private UpKeepCarAdapter car_adapter;// 所属车辆适配器
	private UpKeepItemsAdapter items_adapter;// 维修项目适配器
	private UpKeepTypeAdapter type_adapter;// 保养类型适配器

	private static final int SHOWPICTURE = 1000;
	private static final int DELETEPICTURE = 99;
	private List<Bitmap> picture = new ArrayList<Bitmap>();
	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			ImageUtil.getPhotoFileName());// 文件存储
	private Bitmap bmp;

	private List<TCarDto> car_list = new ArrayList<TCarDto>();// 所属车辆数组
	private List<TCarDto> type_list = new ArrayList<TCarDto>();// 保养类型数组
	private List<TCarDto> items_list = new ArrayList<TCarDto>();// 维修项目数组

	private String str = "";

	private Integer carId;// 车辆ID
	private Integer maintainType;// 保养类型
	private Integer maintainProject;// 保养项目

	private List<Long> fileIds = new ArrayList<Long>();
	private static final String TAG = "UpKeepAcitivity";
	private static final int UPLOAD = 888;
	private static final int UPLOAD_FAIL = 444;

	

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.upkeep_activity);

		findViews();
		initViews();
		initializeViews();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		upkeep_time = (TextView) findViewById(R.id.upkeep_time);
		upkeep_cause = (EditText) findViewById(R.id.upkeep_cause);
		upkeep_kilometre = (EditText) findViewById(R.id.upkeep_kilometre);
		upkeep_money = (EditText) findViewById(R.id.upkeep_money);
		upkeep_person = (EditText) findViewById(R.id.upkeep_person);
		upkeep_next = (TextView) findViewById(R.id.upkeep_next);
		upkeep_remarks = (EditText) findViewById(R.id.upkeep_remarks);

		gridview = (GridViewExt) findViewById(R.id.upkeep_gridview);
		upkeep_submit = (Button) findViewById(R.id.upkeep_btn);
		// 所属车辆下拉按键
		upkeep_car = (TextView) findViewById(R.id.upkeep_car);
		// 维修项目下拉按键
		upkeep_items = (TextView) findViewById(R.id.upkeep_items);
		// 保养类型下拉按键
		upkeep_type = (TextView) findViewById(R.id.upkeep_type);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("车辆保养");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		upkeep_submit.setOnClickListener(this);
		upkeep_time.setOnClickListener(this);
		upkeep_next.setOnClickListener(this);

		upkeep_car.setOnClickListener(this);
		upkeep_items.setOnClickListener(this);
		upkeep_type.setOnClickListener(this);
		// 所属车辆适配器
		car_adapter = new UpKeepCarAdapter(this, car_list);
		// 维修项目适配器
		items_adapter = new UpKeepItemsAdapter(this, items_list);
		// 保养类型适配器
		type_adapter = new UpKeepTypeAdapter(this, type_list);

		bmp = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.photoupload02);
		picture.add(bmp);

		// 图片上传适配器
		adapter = new UpKeepAdapter(UpKeepActivity.this, picture, handler);
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
						Toast.makeText(UpKeepActivity.this, "上传的图片不能超过三张！",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
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
				UpKeepActivity.this.hideLoading();
				Toast.makeText(UpKeepActivity.this, "上传图片失败！",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("common_car_type".equals(requestStr)) {
			Toast.makeText(this, "未获取到车辆类型", Toast.LENGTH_SHORT).show();
		}
		if ("upkeepdata".equals(requestStr)) {
			Toast.makeText(UpKeepActivity.this, "提交失败！", Toast.LENGTH_SHORT)
					.show();
		}
		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("upkeepdata", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		// 所属车辆
		if ("common_car_type".equals(requestStr)) {
			String data = jsonObject.optString("data");
			if (StringUtilsExt.isEmpty(data) || data.equals("{}")
					|| data.equals("[]") || data.equals("null")) {
				Toast.makeText(this, "暂无绑定车辆，请先绑定车辆！", Toast.LENGTH_SHORT)
						.show();
				return;
			} else {
				Gson gson = new Gson();
				List<TCarDto> bean = gson.fromJson(data,
						new TypeToken<List<TCarDto>>() {
						}.getType());
				if (car_list.size() == 0 || bean.size() != car_list.size()) {
					car_list.clear();
					car_list.addAll(bean);
					car_adapter.setData(car_list);
				}
				car_adapter.notifyDataSetChanged();
			}
		}
		// 保养类型
		if ("common_car_maintain_type".equals(requestStr)) {
			String data = jsonObject.optString("data");
			Gson gson = new Gson();
			List<TCarDto> bean = gson.fromJson(data,
					new TypeToken<List<TCarDto>>() {
					}.getType());
			if (type_list.size() == 0 || bean.size() != type_list.size()) {
				type_list.clear();
				type_list.addAll(bean);
				type_adapter.setData(type_list);
			}
			type_adapter.notifyDataSetChanged();
		}
		// 维修项目
		if ("common_car_maintain_project".equals(requestStr)) {
			String data = jsonObject.optString("data");
			Gson gson = new Gson();
			List<TCarDto> bean = gson.fromJson(data,
					new TypeToken<List<TCarDto>>() {
					}.getType());
			if (items_list.size() == 0 || bean.size() != items_list.size()) {
				items_list.clear();
				items_list.addAll(bean);
				items_adapter.setData(items_list);
			}
			items_adapter.notifyDataSetChanged();
		}
		if ("upkeepdata".equals(requestStr)) {

			String flag = jsonObject.optString("flag");
			if (flag.equals("true")) {
				Toast.makeText(UpKeepActivity.this, "提交成功！", Toast.LENGTH_SHORT)
						.show();
				this.finish();
			} else {
				Toast.makeText(UpKeepActivity.this, "提交失败！", Toast.LENGTH_SHORT)
						.show();
			}

		}
		PopView = null;
	}

	/**
	 * 描述：车辆保养数据
	 * */
	private void requestData() {

		long createId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		int createOrgId = SharedPreferencesUtils.getIntValue(this,
				SharedPreferencesUtils.ORG_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("carId", carId);
			jsonObject.put("maintainTime", upkeep_time.getText().toString());
			jsonObject.put("maintainType", maintainType);
			jsonObject.put("maintainProject", maintainProject);
			jsonObject.put("reason", upkeep_cause.getText().toString());
			jsonObject.put("km", upkeep_kilometre.getText().toString());
			jsonObject.put("price", upkeep_money.getText().toString());
			jsonObject.put("managerBy", upkeep_person.getText().toString());
			jsonObject.put("remark", upkeep_remarks.getText().toString());
			jsonObject
					.put("maintainNextTime", upkeep_next.getText().toString());

			jsonObject.put("createOrgId", createOrgId);
			jsonObject.put("createId", createId);
			List<JSONObject> jsons = new ArrayList<JSONObject>();
			for (int i = 0; i < fileIds.size(); i++) {
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("id", fileIds.get(i));
				jsons.add(jsonObject2);
			}
			if (fileIds.size() > 0) {
				jsonObject.put("fileKeys", jsons.toString());
			}

			requestHttp(jsonObject, "upkeepdata", Constants.CARDATA,
					Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	/** 获取所属车辆，维修项目，保养类别 */
	private void common_car_type(String type) {
		Long staffId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			if (type.equals("common_car_type")) {
				jsonObject.put("staffId", staffId);
				requestHttp(jsonObject, type, Constants.CARID,
						Constants.SERVER_URL);
			} else {
				jsonObject.put("dictType", type);
				requestHttp(jsonObject, type, Constants.CARTYPE,
						Constants.SERVER_URL);
			}
		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
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
				json.put("fileType", "4");
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
	 * 下拉选项弹出列表
	 * 
	 * @param control
	 * @param adapter
	 */
	private void PopSite(final TextView control, BaseAdapter adapter,
			final List<TCarDto> list) {
		if (PopView == null) {
			PopView = getLayoutInflater().inflate(R.layout.upkeep_popup, null);
			// 设置宽高
			popupwindow = new PopupWindow(PopView, control.getWidth(),
					LayoutParams.WRAP_CONTENT, true);
			// 焦点设置，点击区域外消失参数必须为true
			popupwindow.setFocusable(true);
			// 点击区域外消失必须设置此方法
			popupwindow.setBackgroundDrawable(new BitmapDrawable());
			// 选择在某一个控件下面
			popupwindow.showAsDropDown(control);
			popup_listview = (ListView) PopView
					.findViewById(R.id.upkeep_pop_listview);
		}
		popup_listview.setAdapter(adapter);
		popup_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				PopView = null;
				if (control == upkeep_car) {
					// 所属车辆
					control.setText(list.get(position).getCarNo().toString());
					carId = new Long(list.get(position).getId()).intValue();
				}
				if (control == upkeep_items) {
					// 保养项目
					control.setText(list.get(position).getDictLabel()
							.toString());
					maintainProject = list.get(position).getSortNum();
				}
				if (control == upkeep_type) {
					// 保养类型
					control.setText(list.get(position).getDictLabel()
							.toString());

					maintainType = list.get(position).getSortNum();
				}
				// 点击区域外关闭popup
				if (null != popupwindow && popupwindow.isShowing()) {
					popupwindow.dismiss();
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.upkeep_car:
			// 所属车辆下拉
			str = "common_car_type";
			common_car_type(str);
			PopSite(upkeep_car, car_adapter, car_list);
			break;
		case R.id.upkeep_items:
			// 维修项目下拉
			str = "common_car_maintain_project";
			common_car_type(str);
			PopSite(upkeep_items, items_adapter, items_list);
			break;
		case R.id.upkeep_type:
			// 保养类型下拉
			str = "common_car_maintain_type";
			common_car_type(str);
			PopSite(upkeep_type, type_adapter, type_list);

			break;
		case R.id.upkeep_btn:
			if (!"".equals(upkeep_time.getText().toString())
					&& !"".equals(upkeep_cause.getText().toString())
					&& !"".equals(upkeep_kilometre.getText().toString())
					&& !"".equals(upkeep_money.getText().toString())
					&& !"".equals(upkeep_person.getText().toString())
					&& !"".equals(upkeep_car.getText().toString())
					&& !"".equals(upkeep_type.getText().toString())
					&& !"".equals(upkeep_items.getText().toString())) {

				this.showLoading("上传中，请稍后...", TAG);
				if (picture.size() <= 0) {
					requestData();
				} else {
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
				}

			} else {
				Toast.makeText(this, "还有未填写的，请仔细检查！", Toast.LENGTH_SHORT)
						.show();
			}
			// 提交按键
			break;

		case R.id.layoutbackimg:
			this.finish();
			break;

		case R.id.upkeep_time:
			dateFlag = 1;
			dateandtimeHandler.sendEmptyMessage(SHOW_DATAPICK);
			break;

		case R.id.upkeep_next:
			dateFlag = 2;
			dateandtimeHandler.sendEmptyMessage(SHOW_DATAPICK);
			break;

		default:
			break;
		}
	}

	private static final int SHOW_DATAPICK = 0;

	private int mYear;
	private int mMonth;
	private int mDay;
	private int dateFlag = 1;// 1-本次保养，2-下次保养

	/**
	 * 初始化控件和UI视图
	 */
	private void initializeViews() {

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		setDateTime();
	}

	/**
	 * 设置日期
	 */
	private void setDateTime() {
		final Calendar c = Calendar.getInstance();

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		updateDateDisplay();
	}

	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay() {

		String date = new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay).toString();
		if (dateFlag == 1) {
			upkeep_time.setText(date);
		} else {
			upkeep_next.setText(date);
		}
	}

	/**
	 * 日期控件的事件
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDateDisplay();
		}
	};

	/**
	 * 处理日期和时间控件的Handler
	 */
	Handler dateandtimeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_DATAPICK:
				DatePickerDialog dialog = new DatePickerDialog(
						UpKeepActivity.this, mDateSetListener, mYear, mMonth,
						mDay);
				dialog.show();
				break;
			}
		}

	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 回收图片，释放资源
		if (picture.size() > 0) {
			for (Bitmap bitmap : picture) {
				bitmap.recycle();
			}
		}
	}
}
