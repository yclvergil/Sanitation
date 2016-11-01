package com.songming.sanitation.user;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.songming.sanitation.Main;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.ApplicationExt;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.MD5;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.user.model.UserDto;

/**
 * 登陆页面
 * 
 * @author Administrator
 * 
 */
@SuppressLint("NewApi")
public class LoginAcitivity extends BaseActivity implements OnClickListener,
		TextWatcher {

	private LinearLayout layoutback;
	// 登录按钮
	private TextView login_btn;
	private TextView retrieve_text;
	// 用户名输入框
	private EditText edit_uname;
	// 密码输入框
	private EditText edit_password;

	private ImageView clearuser;// 清楚用户名按钮

	private ImageView clearpwd;// 清楚密码按钮

	private boolean reLogin;
	private String version;
	private TextView versions;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setContentView(R.layout.activity_login);

		reLogin = getIntent().getBooleanExtra("reLogin", false);

		try {
			version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub

		login_btn = (TextView) findViewById(R.id.login_btn);

		edit_uname = (EditText) findViewById(R.id.edit_uname);

		edit_password = (EditText) findViewById(R.id.edit_password);

		retrieve_text = (TextView) findViewById(R.id.retrieve_text);

		clearuser = (ImageView) findViewById(R.id.clearuser);

		clearpwd = (ImageView) findViewById(R.id.clearpwd);

		versions = (TextView) findViewById(R.id.versions);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

		String loginName = SharedPreferencesUtils.getStringValue(this,
				SharedPreferencesUtils.LOGIN_NAME, "");
		edit_uname.setText(loginName);
		String psw = SharedPreferencesUtils.getStringValue(this,
				SharedPreferencesUtils.USERPASSWORD, "");
		edit_password.setText(psw);
		versions.setText("环卫365 v" + version + " for android");

		edit_uname.addTextChangedListener(this);
		edit_password.addTextChangedListener(this);

		clearuser.setOnClickListener(this);
		clearpwd.setOnClickListener(this);

		login_btn.setOnClickListener(this);
		retrieve_text.setOnClickListener(this);

		checkEmpty();

	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("login".equals(requestStr)) {
			Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("login", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("login".equals(requestStr)) {

			Gson gson = new Gson();

			String flag = jsonObject.optString("flag", "");
			String msg = jsonObject.optString("msg", "登陆成功");
			if (flag.equals("false")) {
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
			} else {

				UserDto user = gson.fromJson(jsonObject.optString("data", "{}"),
						UserDto.class);
				ApplicationExt.nowUser = user;
				Toast.makeText(this, "登陆成功！", Toast.LENGTH_SHORT).show();
				// 保存用户登陆信息
				SharedPreferencesUtils.setStringValue(this,
						SharedPreferencesUtils.LOGIN_NAME, edit_uname.getText()
								.toString().trim());
				SharedPreferencesUtils.setStringValue(this,
						SharedPreferencesUtils.USERPASSWORD, edit_password
								.getText().toString().trim());
				SharedPreferencesUtils.setLongValue(this,
						SharedPreferencesUtils.USER_ID, user.getId());
				SharedPreferencesUtils.setStringValue(this,
						SharedPreferencesUtils.USERHEAD, user.getPhotoUrl());
				SharedPreferencesUtils.setStringValue(this,
						SharedPreferencesUtils.USERNAME, user.getName());
				// 角色职权id，非常重要，根据职位权限决定可见页面
				SharedPreferencesUtils.setLongValue(this,
						SharedPreferencesUtils.STATIONID, user.getStationId());
				SharedPreferencesUtils.setIntValue(this,
						SharedPreferencesUtils.ORG_ID, user.getOrgId());
				SharedPreferencesUtils.setLongValue(this,
						SharedPreferencesUtils.STAFF_ID, user.getStaffId());
				SharedPreferencesUtils.setStringValue(this,
						SharedPreferencesUtils.PHONE, user.getPhone());
				if (!reLogin) {
					startActivity(new Intent(this, Main.class));
				}
				finish();
			}
		}
	}

	/**
	 * 描述：检查登录表单合法性
	 * 
	 * @return true if the form valid ,or false
	 * */
	private boolean checkForm() {

		if (StringUtilsExt.isEmpty(edit_uname.getText().toString())) {
			startShakeAnim(edit_uname);
			edit_uname.setError(this.getResources().getText(
					R.string.edit_uname_hint));
			return false;
		} else {
			edit_uname.setError(null);
		}
		if (StringUtilsExt.isEmpty(edit_password.getText().toString())) {
			startShakeAnim(edit_password);
			edit_password.setError(this.getResources().getText(
					R.string.login_psw_hint));
			return false;
		} else {
			edit_password.setError(null);
		}

		return true;
	}

	/**
	 * 描述：提交表单，进行远程登录校验
	 * 
	 * */
	private void submitLogin() {

		if (!checkForm())
			return;

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("loginName", edit_uname.getText().toString().trim());
			jsonObject.put("password",
					MD5.getMD5(edit_password.getText().toString().trim())
							.toUpperCase());
			if (applications.channelId != null) {
				jsonObject.put("channelId", applications.channelId);
			} else {
				jsonObject.put("channelId", "");
			}
			jsonObject.put("deviceType", "3");// 3-android
			this.showLoading("正在登录……", "login");
			requestHttp(jsonObject, "login", Constants.LOGIN,
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
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_btn:
			submitLogin();

			break;
		case R.id.retrieve_text:
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.clearuser:
			edit_uname.setText("");
			break;
		case R.id.clearpwd:
			edit_password.setText("");
			break;
		default:
			break;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		checkEmpty();

	}

	/**
	 * 判断输入框是否为空
	 * */
	private void checkEmpty() {
		if (!"".equals(edit_uname.getText().toString().trim())) {
			clearuser.setVisibility(View.VISIBLE);
		} else if ("".equals(edit_uname.getText().toString().trim())) {
			clearuser.setVisibility(View.INVISIBLE);
		}

		if (!"".equals(edit_password.getText().toString().trim())) {
			clearpwd.setVisibility(View.VISIBLE);
		} else if ("".equals(edit_password.getText().toString().trim())) {
			clearpwd.setVisibility(View.INVISIBLE);
		}
	}

}
