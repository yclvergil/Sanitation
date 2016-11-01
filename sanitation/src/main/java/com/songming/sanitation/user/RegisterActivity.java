package com.songming.sanitation.user;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.songming.sanitation.frameset.utils.StringUtilsExt;

/**
 * 找回密码
 * 
 * @author yunjiezhou
 * @since 1.0
 * */
public class RegisterActivity extends BaseActivity implements OnClickListener,
		TextWatcher {

	private static final String TAG = RegisterActivity.class.getName();

	// 顶部标题
	private TextView topTitle;
	private ImageView layoutbackimg;
	private LinearLayout layoutback;

	// 用户名输入框
	private EditText userName;

	// 密码输入框
	private EditText userPassword;
	private EditText register_password2;
	// 验证码输入框
	private EditText identityWord;

	// 获取验证码
	private TextView getIdentityWord;

	// 确定
	private Button ensure;
	private TimeCount time;
	private String vCope = "";

	private ImageView clearuser;// 清楚用户名按钮
	private ImageView clearpwd;// 清楚密码按钮

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.register);

		findViews();
		initViews();
		time = new TimeCount(60000, 1000);

		checkEmpty();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void findViews() {
		topTitle = (TextView) findViewById(R.id.topTitle);
		topTitle.setVisibility(View.VISIBLE);
		topTitle.setText("找回密码");
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		ensure = (Button) findViewById(R.id.register_sure);
		userName = (EditText) findViewById(R.id.register_username);
		userPassword = (EditText) findViewById(R.id.register_password);
		identityWord = (EditText) findViewById(R.id.register_identityword);
		register_password2 = (EditText) findViewById(R.id.register_password2);
		getIdentityWord = (TextView) findViewById(R.id.get_indentityword);

		clearuser = (ImageView) findViewById(R.id.clearuser);
		clearpwd = (ImageView) findViewById(R.id.clearpwd);
	}

	@Override
	protected void initViews() {

		ensure.setOnClickListener(this);
		getIdentityWord.setOnClickListener(this);
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
		if ("verification".equals(requestStr)) {
			Toast.makeText(this, "验证码请求失败！", Toast.LENGTH_SHORT).show();
			if (volleyError != null && volleyError.getMessage() != null)
				Log.i("verification", volleyError.getMessage());
		}
		if ("register".equals(requestStr)) {
			Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
			if (volleyError != null && volleyError.getMessage() != null)
				Log.i("register", volleyError.getMessage());
		}

	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {

		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("register".equals(requestStr)) {

			boolean flag = jsonObject.optBoolean("flag");
			String msg = "";
			try {
				msg = jsonObject.getString("msg");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			if (flag) {
				Toast.makeText(this, "用户密碼成功！", Toast.LENGTH_SHORT).show();
				this.finish();
				// startActivity(this, Login.class);
			} else {

				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				return;
			}
		}

		if ("verification".equals(requestStr)) {
			boolean flag = jsonObject.optBoolean("flag");
			String msg = "";
			try {
				msg = jsonObject.getString("msg");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			if (flag) {
				Toast.makeText(this, "获取验证码成功！", Toast.LENGTH_SHORT).show();
				try {
					vCope = jsonObject.getString("data");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// startActivity(this, Login.class);
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

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {

			getIdentityWord.setText("获取验证码");
			getIdentityWord.setClickable(true);

		}

		@Override
		public void onTick(long millisUntilFinished) {

			getIdentityWord.setClickable(false);
			getIdentityWord.setText(millisUntilFinished / 1000 + "秒");
		}
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

		case R.id.get_indentityword:
			verificationRegister();
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
	 * 描述：检查登录表单合法性
	 * 
	 * @return true if the form valid ,or false
	 * */
	private boolean checkForm() {
		if (!StringUtilsExt.isMobile(userName.getText().toString())) {
			startShakeAnim(userName);
			userName.setError(this.getResources().getText(
					R.string.login_username_hint));
			return false;
		} else {
			userName.setError(null);
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

		if (!vCope.equals(identityWord.getText().toString().trim())
				|| getIdentityWord.isClickable()) {
			Toast.makeText(this, "验证码错误或者超时！", 0).show();
			return false;
		}
		return true;

	}

	/**
	 * 描述：提交表单，请求验证码
	 * 
	 * */
	private void verificationRegister() {

		if (!StringUtilsExt.isMobile(userName.getText().toString())) {
			startShakeAnim(userName);
			userName.setError(this.getResources().getText(
					R.string.login_username_hint));
			return;
		} else {
			userName.setError(null);
		}

		time.start();

		JSONObject jsonObject = new JSONObject();
		// try {
		// jsonObject.put("mobile", userName.getText().toString().trim());
		// // this.showLoading("正在获取验证码...", "verification");
		//
		// requestHttp(jsonObject, "verification", Constants.VERIFICATION,
		// Constants.SERVER_URL);
		//
		// } catch (JSONException e) {
		// Toast.makeText(this.getApplicationContext(),
		// "json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();
		//
		// } catch (Exception e) {
		// Toast.makeText(this.getApplicationContext(), "请重新启动",
		// Toast.LENGTH_SHORT).show();
		// }

	}

	/**
	 * 描述：提交表单，注册操作
	 * 
	 * */
	private void submitRegister() {

		if (!checkForm())
			return;

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("username", userName.getText().toString().trim());
			jsonObject.put("userPassword",
					MD5.encode(userPassword.getText().toString().trim()));// md5加密

			jsonObject.put("userType", "1");
			jsonObject.put("clientType", "1");
			this.showLoading("正在注册...", "register");

			// requestHttp(jsonObject, "register", Constants.REGIST,
			// Constants.SERVER_URL);

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
