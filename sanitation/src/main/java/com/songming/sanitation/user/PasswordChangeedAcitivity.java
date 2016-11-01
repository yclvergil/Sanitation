package com.songming.sanitation.user;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.MD5;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.frameset.utils.StringUtilsExt;

/**
 * 修改密码页面
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class PasswordChangeedAcitivity extends BaseActivity implements
		OnClickListener, TextWatcher {

	private static final String TAG = PasswordChangeedAcitivity.class.getName();

	// 顶部标题
	private TextView topTitle;
	private ImageView layoutbackimg;
	private LinearLayout layoutback;

	private EditText oldpassword;// 老密码
	private EditText userPassword;// 密码输入框
	private EditText register_password2;// 二次密码输入框
	private ImageView clearuser;// 清楚密码
	private ImageView clearpwd;// 清楚二次密码按钮

	private Button ensure;// 确定

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.passwordchange);

		findViews();
		initViews();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void findViews() {
		topTitle = (TextView) findViewById(R.id.topTitle);
		topTitle.setVisibility(View.VISIBLE);
		topTitle.setText("修改密码");
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		ensure = (Button) findViewById(R.id.register_sure);
		userPassword = (EditText) findViewById(R.id.register_password);
		register_password2 = (EditText) findViewById(R.id.register_password2);
		oldpassword = (EditText) findViewById(R.id.oldpassword);

		clearuser = (ImageView) findViewById(R.id.clearuser);
		clearpwd = (ImageView) findViewById(R.id.clearpwd);
	}

	@Override
	protected void initViews() {

		ensure.setOnClickListener(this);
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		userPassword.addTextChangedListener(this);
		register_password2.addTextChangedListener(this);

		clearuser.setOnClickListener(this);
		clearpwd.setOnClickListener(this);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {

		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if (TAG.equals(requestStr)) {
			Toast.makeText(this, "修改密码失败！", Toast.LENGTH_SHORT).show();
			if (volleyError != null && volleyError.getMessage() != null)
				Log.i(TAG, volleyError.getMessage());
		}

	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {

		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if (TAG.equals(requestStr)) {

			boolean flag = jsonObject.optBoolean("flag");
			String msg = jsonObject.optString("msg", "修改密码失败！");

			if (flag) {
				Toast.makeText(this, "修改密码成功！", Toast.LENGTH_SHORT).show();
				this.finish();
			} else {

				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				return;
			}
		}

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * 描述：按钮等可点击控件的单击事件处理方法
	 * 
	 * */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.register_sure:
			// 进行表单提交注册
			submitRegister();
			break;

		case R.id.clearuser:
			userPassword.setText("");
			break;

		case R.id.clearpwd:
			register_password2.setText("");
			break;

		case R.id.layoutbackimg:
			this.finish();
			break;
		}
	}

	/**
	 * 描述：检查输入合法性
	 * 
	 * @return true if the form valid ,or false
	 * */
	private boolean checkForm() {
		if (StringUtilsExt.isEmpty(oldpassword.getText().toString())) {
			startShakeAnim(oldpassword);
			oldpassword.setError(this.getResources().getText(
					R.string.login_psw_hint));
			return false;
		} else {
			oldpassword.setError(null);
		}
		if (StringUtilsExt.isEmpty(userPassword.getText().toString())) {
			startShakeAnim(userPassword);
			userPassword.setError(this.getResources().getText(
					R.string.login_psw_hint));
			return false;
		} else {
			userPassword.setError(null);
		}
		if (!register_password2.getText().toString().trim()
				.equals(userPassword.getText().toString().trim())) {
			startShakeAnim(register_password2);
			register_password2.setError(this.getResources().getText(
					R.string.psw_no_same));
			return false;
		} else {
			register_password2.setError(null);
		}
		return true;
	}

	/**
	 * 描述：提交表单，注册操作
	 * 
	 * */
	private void submitRegister() {

		if (!checkForm())
			return;

		String oldPsw = SharedPreferencesUtils.getStringValue(this, SharedPreferencesUtils.USERPASSWORD, "");
		if(!oldPsw.equals(oldpassword.getText().toString().trim())){
			Toast.makeText(this, "您输入的原密码有误！", Toast.LENGTH_SHORT).show();
			return;
		}
		Long id = SharedPreferencesUtils.getLongValue(this, SharedPreferencesUtils.USER_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", id);
			jsonObject.put("oldPassword", MD5.encode(oldPsw).toUpperCase());
			jsonObject.put("newPassword",
					MD5.encode(userPassword.getText().toString().trim())
							.toUpperCase());// md5加密

			this.showLoading("正在保存修改...", "register");

			requestHttp(jsonObject, TAG, Constants.MODIFYPSW,
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
	 * 判断输入框是否为空
	 * */
	private void checkEmpty() {
		if (!"".equals(userPassword.getText().toString().trim())) {
			clearuser.setVisibility(View.VISIBLE);
		} else if ("".equals(userPassword.getText().toString().trim())) {
			clearuser.setVisibility(View.INVISIBLE);
		}

		if (!"".equals(register_password2.getText().toString().trim())) {
			clearpwd.setVisibility(View.VISIBLE);
		} else if ("".equals(register_password2.getText().toString().trim())) {
			clearpwd.setVisibility(View.INVISIBLE);
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

}
